import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class Runner {
	public static void main(String[] args){
		try {
			BufferedReader in = new BufferedReader(new FileReader("relationships-small.txt"));
			String line = null;
			while((line = in.readLine()) !=null){
				line = line.trim();
				if(!line.isEmpty()){
					String[] splittedLine = line.split(" ");
					if(splittedLine.length == 1){
						if (splittedLine[0].contains("{") || splittedLine[0].contains("}")){
							System.out.println("SKOBA: " + splittedLine[0]);
						}else{
							System.out.println("ID :" + splittedLine[0]);
						}  
					}else{
						System.out.println(splittedLine.length);
					}
				}
			}
			Graph graph = new Graph ();
		} catch (FileNotFoundException e) {
			System.out.println("ne 4ete ot faila -> v Runner");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("ne6to se barka s 4eteneto na liniq");
			e.printStackTrace();
		}
		
	}
}
