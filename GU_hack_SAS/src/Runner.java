import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Runner {
	public static final Graph graph = new Graph();
	public static int hasReleationshipWithHimself = 0;
	public static int duplicate = 0;
	public static void main(String[] args) throws IOException{
		//BufferedReader in = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream("relationships-small.txt");
			Scanner in = new Scanner(fis);
			//in = new BufferedReader(new FileReader("relationships-small.txt"));
			String line = null;
			String mainId = null;
			while(in.hasNext()) {
				line = in.nextLine();
				line = line.trim();
				if(!line.isEmpty()){
					String[] splittedLine = line.split(" ");
					if(splittedLine.length == 1){
						if (splittedLine[0].contains("{") || splittedLine[0].contains("}")){
							System.out.println("SKOBA: " + splittedLine[0]);
						}else{
							System.out.println("ID :" + splittedLine[0]);
							mainId = splittedLine[0];
							Runner.handleIdEntry(mainId);
						}  
					}else{
						
						if(splittedLine.length == 2 ){
							System.out.println(splittedLine[0] + " " + splittedLine[1]);
							Runner.isInRelationshipWithHimself(mainId, splittedLine[1]);
							//TODO: handle relationships
							try {
								Runner.addRelationship(mainId, splittedLine[0], splittedLine[1]);
							} catch (NumberFormatException e) {
								// TODO: handle incorrect relation ID 
								System.out.println("INCORRECT RELATION ID -> line : " + line);
							}
							
						}else{
							//TODO: handle incorrect input
							System.out.println("INCORRECT INPUT");
						}
						
					}
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("ne 4ete ot faila -> v Runner");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("ne6to se barka s 4eteneto na liniq");
			e.printStackTrace();
		}
		
		graph.averageRelationships();
		System.out.println(graph.countPeopleWithFriendOfRelationships());
	}
	public static void isInRelationshipWithHimself(String a, String b){
		if(a.equals(b)){
			hasReleationshipWithHimself++;
		}
	}
	public static void handleIdEntry(String mainId){
		if (graph.getFileMap().containsKey(Long.parseLong(mainId))){
			duplicate++;
			//TODO: handle update of old entry
		}else{
			graph.getFileMap().put(Long.parseLong(mainId), new Node(Long.parseLong(mainId)));
		}
	}
	public static void addRelationship(String mainId, String relationship, String relId)throws NumberFormatException{
		if(relationship.compareToIgnoreCase("dislikes")==0){
			graph.addDislike(Long.parseLong(mainId), Long.parseLong(relId));
		}else if (relationship.compareToIgnoreCase("friend_of")==0){
			graph.addFriend(Long.parseLong(mainId), Long.parseLong(relId));
		}else if (relationship.compareToIgnoreCase("knows")==0){
			graph.addKnows(Long.parseLong(mainId), Long.parseLong(relId));
		}else if (relationship.compareToIgnoreCase("married_to")==0){
			graph.addMarried(Long.parseLong(mainId), Long.parseLong(relId));
		}else if (relationship.compareToIgnoreCase("has_dated")==0){
			graph.addDated(Long.parseLong(mainId), Long.parseLong(relId));
		}
	}
}
