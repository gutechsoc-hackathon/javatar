
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import GephiGraph.*;

public class VisualiseGraph {	
	public static final Graph graph = new Graph();
	public static long hasReleationshipWithHimself = 0;
	public static long duplicate = 0;
	public static GUI ui;
	
	public static void main(String[] args) {
		graphCreation();
		System.out.println("Finished reading woooow" + graph.getSize());
		XMLGenerator xmlGen = new XMLGenerator();
		System.out.println("HashMap size is = " + graph.getFileMap().size() + "\n");
		String xmlString = xmlGen.generateXML(graph.getFileMap());
		xmlGen.writeFile(xmlString);
		PreviewJFrame.main(null);
    }
	
	public static void graphCreation()
	{
		FileInputStream fis = null;

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
		
		try {
			fis = new FileInputStream("relationships-small.txt");
			Scanner in = new Scanner(fis);
			// in = new BufferedReader(new
			// FileReader("relationships-small.txt"));
			String line = null;
			String mainId = null;
			while (in.hasNext()) {
				System.out.println("Next line");
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
							mainId = splittedLine[0];
							VisualiseGraph.handleIdEntry(mainId);
						}
					} else {

						if (splittedLine.length == 2) {
							// System.out.println(splittedLine[0] + " " +
							// splittedLine[1]);
							VisualiseGraph.isInRelationshipWithHimself(mainId,
									splittedLine[1]);
							// TODO: handle relationships
							try {
								VisualiseGraph.addRelationship(mainId, splittedLine[0],
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
		} catch (IOException e) {
			System.out.println("ne6to se barka s 4eteneto na liniq");
			e.printStackTrace();
		}
	}
	
	public static void isInRelationshipWithHimself(String a, String b) {
		if (a.equals(b)) {
			hasReleationshipWithHimself++;
		}
	}

	public static void handleIdEntry(String mainId) {
		if (graph.getFileMap().containsKey(Long.parseLong(mainId))) {
			duplicate++;
			System.out.println("found duplicate");
			// TODO: handle update of old entry
		} else {
			System.out.println("added to to HashMap");
			graph.getFileMap().put(Long.parseLong(mainId),
					new Node(Long.parseLong(mainId)));
		}
	}

	public static void addRelationship(String mainId, String relationship,
			String relId) throws NumberFormatException {
		if (relationship.compareToIgnoreCase("dislikes") == 0) {
			graph.addDislike(Long.parseLong(mainId), Long.parseLong(relId));
		} else if (relationship.compareToIgnoreCase("friend_of") == 0) {
			graph.addFriend(Long.parseLong(mainId), Long.parseLong(relId));
		} else if (relationship.compareToIgnoreCase("knows") == 0) {
			graph.addKnows(Long.parseLong(mainId), Long.parseLong(relId));
		} else if (relationship.compareToIgnoreCase("married_to") == 0) {
			graph.addMarried(Long.parseLong(mainId), Long.parseLong(relId));
		} else if (relationship.compareToIgnoreCase("has_dated") == 0) {
			graph.addDated(Long.parseLong(mainId), Long.parseLong(relId));
		}
	}
}
