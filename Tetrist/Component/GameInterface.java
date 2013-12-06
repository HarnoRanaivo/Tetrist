package Component;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameInterface extends Remote
{
    public int[][] get_grid() throws RemoteException;
    public int[][] get_piece() throws RemoteException;
    public int get_piece_id() throws RemoteException;
}
