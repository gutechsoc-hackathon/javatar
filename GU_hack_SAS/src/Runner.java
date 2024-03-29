import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Runner {
	public static final Graph graph = new Graph();
	public static long hasReleationshipWithHimself = 0;
	public static long duplicate = 0;
	public static GUI ui;
	

	public static void main(String[] args) throws IOException {

		long timer0 = System.currentTimeMillis();
		// BufferedReader in = null;
		long count = 0;
		FileInputStream fis = null;
		//long count = 0;
		// TODO working just needs to be out of the comment
		/*
		 * File file = null; JFileChooser openFileDialog = new JFileChooser();
		 * int filePath = openFileDialog.showDialog(null, "Open");
		if (filePath==JFileChooser.APPROVE_OPTION){
			file = openFileDialog.getSelectedFile();
		}
		*/
		
		//ui = new GUI();
		//ui.show();
		long start = System.currentTimeMillis();
		System.out.println("start");
		try {
			fis = new FileInputStream("relationships-100m.txt");
			Scanner in = new Scanner(fis);
			// in = new BufferedReader(new
			// FileReader("relationships-small.txt"));
			String line = null;
			String mainId = null;
			while (in.hasNext()) {
				line = in.nextLine();
				line = line.trim();
				if (!line.isEmpty()) {
					String[] splittedLine = line.split(" ");
					if (splittedLine.length == 1) {
						if (splittedLine[0].contains("{")
								|| splittedLine[0].contains("}")) {
							// System.out.println("SKOBA: " + splittedLine[0]);
						} else {
							// System.out.println("ID :" + splittedLine[0]);
							/*count++;
							if (count % 100000 == 0) {
								System.out.println("Read " + count);
							}*/
							// if (count > 1000000) break;
							mainId = splittedLine[0];
							graph.incrementCount(Long.parseLong(mainId));
							Runner.handleIdEntry(mainId);
						}
					} else {

						if (splittedLine.length == 2) {
							// System.out.println(splittedLine[0] + " " +
							// splittedLine[1]);
							Runner.isInRelationshipWithHimself(mainId,
									splittedLine[1]);
							graph.incrementSum(Long.parseLong(mainId));
							// TODO: handle relationships
							try {
								Runner.addRelationship(mainId, splittedLine[0],
										splittedLine[1]);
							} catch (NumberFormatException e) {
								// TODO: handle incorrect relation ID
								System.out
										.println("INCORRECT RELATION ID -> line : "
												+ line);
							}

						} else {
							// TODO: handle incorrect input
							System.out.println("INCORRECT INPUT");
						}

					}
				}

			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("ne 4ete ot faila -> v Runner");
			e.printStackTrace();
		}
		//graph.partisionByFriends();


		//System.out.println("The longest cycle is " + graph.awayFromStart + " people long");


		//System.out.println("Check clustering: " + graph.clusterByFriends.size() );
		//ui.getNumOfPeople().setText(String.valueOf(graph.getSize()));
		//System.out.println("Time for reading "		+ String.valueOf(System.currentTimeMillis() - timer0));
		//long start = System.currentTimeMillis();
		// ui.getNumOfPeople().setText(String.valueOf(graph.getSize()));
		//System.out.println("Number of dups: " + duplicate);
		System.out.println("Number of people: " + graph.getSize());
		graph.averageRelationships();
		//ui.getRelThemselves().setText(String.valueOf(hasReleationshipWithHimself));
		System.out.println("Relationship with themselves: " + hasReleationshipWithHimself);
		//ui.getFriendOfRel().setText(String.valueOf(graph.countPeopleWithFriendOfRelationships()));
		System.out.println("# of people having double relationships : " + graph.countPeopleWithFriendOfRelationships());
		//ui.getMostDisliked().setText(String.valueOf(graph.theMostDislikedPerson()));
		System.out.println("Most disliked person: " + graph.theMostDislikedPerson());
		//graph.longestCycle(807618169778923806L);
		//graph.partisionByFriends();
		//System.out.println("connected components = " + graph.countConnectedComponents + " longest cycle = " + graph.awayFromStart);

		long end = System.currentTimeMillis();
		System.out
				.println("Runtime after reading: " + ((end - start) / 1000.0));
	}

	public static void isInRelationshipWithHimself(String a, String b) {
		if (a.equals(b)) {
			hasReleationshipWithHimself++;
		}
	}

	public static void handleIdEntry(String mainId) {
		if (graph.getFileMap().containsKey(Long.parseLong(mainId))) {
			duplicate++;
			// TODO: handle update of old entry
		} else {
			graph.getFileMap().put(Long.parseLong(mainId),
					new Node(Long.parseLong(mainId)));
		}
	}

	public static void addRelationship(String mainId, String relationship,
			String relId) throws NumberFormatException {
		if (relationship.compareToIgnoreCase("dislikes") == 0) {
			graph.addDisliked(Long.parseLong(relId));
			//graph.addDislike(Long.parseLong(mainId), Long.parseLong(relId));
		} else if (relationship.compareToIgnoreCase("friend_of") == 0) {
			graph.addFriend(Long.parseLong(mainId), Long.parseLong(relId));
		} /*else if (relationship.compareToIgnoreCase("knows") == 0) {
			graph.addKnows(Long.parseLong(mainId), Long.parseLong(relId));
		} else if (relationship.compareToIgnoreCase("married_to") == 0) {
			graph.addMarried(Long.parseLong(mainId), Long.parseLong(relId));
		} else if (relationship.compareToIgnoreCase("has_dated") == 0) {
			graph.addDated(Long.parseLong(mainId), Long.parseLong(relId));
		}*/
	}
}
