package dccan.test.rmi;

import java.rmi.RemoteException;

public class TestInput implements RmiIn {

	@Override
	public void testInput(byte[] d) throws RemoteException {
		d[0]=1 ;
	}

}
