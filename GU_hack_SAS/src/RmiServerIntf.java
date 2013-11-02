import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiServerIntf extends Remote {
	public long getNum() throws RemoteException;

	public void getAve() throws RemoteException;

	public long getRelHimself() throws RemoteException;

	public long getFROfRel() throws RemoteException;

	public long getDisliked() throws RemoteException;
}