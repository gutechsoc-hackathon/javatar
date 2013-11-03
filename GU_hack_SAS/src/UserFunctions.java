import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserFunctions extends Remote {
	public long getNum() throws RemoteException;

	public void getAve() throws RemoteException;

	public long getRelHimself() throws RemoteException;

	public long getFROfRel() throws RemoteException;

	public long getDisliked() throws RemoteException;
	
	public void killServer() throws RemoteException;
	
	public Graph getStructure() throws RemoteException;
	
	public double getEvenAve() throws RemoteException;
	
	public double getOddAve() throws RemoteException;
	
}