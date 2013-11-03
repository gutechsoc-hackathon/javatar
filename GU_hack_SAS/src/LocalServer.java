import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

@SuppressWarnings("serial")
public class LocalServer extends UnicastRemoteObject implements UserFunctions {
	protected LocalServer() throws RemoteException {
		super();
	}

	public static final Graph graph = new Graph();
	public static long hasReleationshipWithHimself = 0;
	public static long duplicate = 0;
	public static GUI ui;

	public long getNum() {
		return graph.getSize();
	}

	public void killServer() {
		System.out.println("Server terminated");
		System.exit(0);
	}
	
	public Graph getStructure(){
		return graph;
	}

	public void getAve() {
		graph.averageRelationships();
	}

	public long getRelHimself() {
		return hasReleationshipWithHimself;
	}

	public long getFROfRel() {
		return graph.countPeopleWithFriendOfRelationships();
	}

	public long getDisliked() {
		return graph.theMostDislikedPerson();
	}
	
	public double getOddAve(){
		return 0;
		
	}
	
	public double getEvenAve(){
		return 0;
		
	}
	

	public static void main(String args[]) throws Exception {
		System.out.println("Server started!");

		boolean canInitialize = false;
		int port = 1099;
		while (!canInitialize) {
			try { // special exception handler for registry creation
				LocateRegistry.createRegistry(port);
				System.out.println("Registry created!");
				canInitialize = true;
			} catch (RemoteException e) {
				// port busy, try next
				port++;
			}
		}
		System.out.println(port);

		// Instantiate Server
		LocalServer obj = new LocalServer();
		obj.start();

		// Bind this object instance to the name "RmiServer"
		Naming.rebind("//localhost:"+port+"/DataStructure", obj);
		System.out.println("Server bound in registry");
	}

	private void start() {

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
		
		try {
			fis = new FileInputStream("relationships-small.txt");
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
							count++;
							if (count % 100000 == 0) {
								System.out.println("Read " + count);
							}
							// if (count > 1000000) break;
							mainId = splittedLine[0];
							graph.incrementCount(Long.parseLong(mainId));
							handleIdEntry(mainId);
						}
					} else {

						if (splittedLine.length == 2) {
							// System.out.println(splittedLine[0] + " " +
							// splittedLine[1]);
							isInRelationshipWithHimself(mainId,
									splittedLine[1]);
							graph.incrementSum(Long.parseLong(mainId));
							// TODO: handle relationships
							try {
								addRelationship(mainId, splittedLine[0],
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
			System.out.println("Cannot find file.");
			e.printStackTrace();
		}
		graph.partisionByFriends();

		System.out.println("Time for reading "
				+ String.valueOf(System.currentTimeMillis() - timer0));
		
	}

	public void isInRelationshipWithHimself(String a, String b) {
		if (a.equals(b)) {
			hasReleationshipWithHimself++;
		}
	}

	public void handleIdEntry(String mainId) {
		if (graph.getFileMap().containsKey(Long.parseLong(mainId))) {
			duplicate++;
			// TODO: handle update of old entry
		} else {
			graph.getFileMap().put(Long.parseLong(mainId),
					new Node(Long.parseLong(mainId)));
		}
	}

	public void addRelationship(String mainId, String relationship, String relId)
			throws NumberFormatException {
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
