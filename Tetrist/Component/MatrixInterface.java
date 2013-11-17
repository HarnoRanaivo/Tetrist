package Basic;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MatrixInterface extends Remote
{
    public boolean[][] get_matrice() throws RemoteException;
}
