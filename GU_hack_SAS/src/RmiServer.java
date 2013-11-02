import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RmiServer extends UnicastRemoteObject implements RmiServerIntf {
	protected RmiServer() throws RemoteException {
		super();
	}

	public static final Graph graph = new Graph();
	public static long hasReleationshipWithHimself = 0;
	public static long duplicate = 0;
	public static GUI ui;

	public static final String MESSAGE = "Hello world";

	public String getMessage() {
		return MESSAGE;
	}
	public long getNum(){
		return graph.getSize();
	}
	
	public void getAve(){
		graph.averageRelationships();
	}
	
	public long getRelHimself(){
		return hasReleationshipWithHimself;
	}
	
	public long getFROfRel(){
		return graph.countPeopleWithFriendOfRelationships();
	}
	
	public long getDisliked(){
		return graph.theMostDislikedPerson();
	}
	

	public static void main(String args[]) throws Exception {
		System.out.println("RMI server started");

		try { // special exception handler for registry creation
			LocateRegistry.createRegistry(1099);
			System.out.println("java RMI registry created.");
		} catch (RemoteException e) {
			// do nothing, error means registry already exists
			System.out.println("java RMI registry already exists.");
		}

		// Instantiate RmiServer
		RmiServer obj = new RmiServer();
		obj.start();

		// Bind this object instance to the name "RmiServer"
		Naming.rebind("//localhost/RmiServer", obj);
		System.out.println("PeerServer bound in registry");
	}

	private void start() {

		// BufferedReader in = null;
		FileInputStream fis = null;

		// TODO working just needs to be out of the comment
		/*
		 * File file = null; JFileChooser openFileDialog = new JFileChooser();
		 * int filePath = openFileDialog.showDialog(null, "Open"); if
		 * (filePath==JFileChooser.APPROVE_OPTION){ file =
		 * openFileDialog.getSelectedFile(); }
		 */

		// ui = new GUI();
		// ui.show();

		try {
			fis = new FileInputStream("relationships-1g.txt");
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
							mainId = splittedLine[0];
							handleIdEntry(mainId);
						}
					} else {

						if (splittedLine.length == 2) {
							// System.out.println(splittedLine[0] + " " +
							// splittedLine[1]);
							isInRelationshipWithHimself(mainId,
									splittedLine[1]);
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
			System.out.println("ne 4ete ot faila -> v Runner");
			e.printStackTrace();
		}
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

	public void addRelationship(String mainId, String relationship,
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
