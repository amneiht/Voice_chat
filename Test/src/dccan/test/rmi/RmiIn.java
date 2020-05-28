package dccan.test.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiIn extends Remote{
	public  void testInput(byte[] d) throws RemoteException;
}
