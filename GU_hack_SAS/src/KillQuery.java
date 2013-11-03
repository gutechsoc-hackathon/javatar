import java.rmi.Naming;

public class KillQuery {
	public static void main(String args[]) throws Exception {

		UserFunctions obj = (UserFunctions) Naming
				.lookup("//localhost/DataStructure");
		obj.killServer();
	}
}
