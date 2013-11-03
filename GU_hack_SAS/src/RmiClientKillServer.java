import java.rmi.Naming;

public class RmiClientKillServer {
	public static void main(String args[]) throws Exception {

		RmiServerIntf obj = (RmiServerIntf) Naming
				.lookup("//localhost/RmiServer");
		obj.killServer();
	}
}
