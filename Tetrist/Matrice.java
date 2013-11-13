import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.Arrays;

class Matrice extends UnicastRemoteObject implements MatriceInterface
{
    static final int height = 20;
    static final int width = 10;
    boolean matrice_IA[][];
    int matrice[][];
    private boolean is_busy;

    public Matrice() throws RemoteException
    {
        super();
        matrice = new int[width][height];
        matrice_IA = new boolean[width][height];
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                matrice[i][j] = -1;
    }

    // Appel distant pour la IA
    public boolean[][] get_matrice() throws RemoteException
    {
        if (is_busy)
            return null;

        return matrice_IA;
    }
    // Fin de l'appel distant.

    public void refresh()
    {
        int x, y;

        is_busy = true;
        for (y = 0; y < height; y++)
            for (x = 0; x < width; x++)
                matrice_IA[x][y] = matrice[x][y] != -1;
        is_busy = false;
    }

    public void put(int x, int y, int v)
    {
        matrice[x][y] = v;
    }

    public void put(int[] x, int[] y, int v)
    {
        for (int i = 0; i < x.length; i++)
            matrice[x[i]][y[i]] = v;
    }

    boolean check_line(int y)
    {
        for (int i = 0; i < width; i++)
            if (matrice[i][y] == -1)
                return false;

        return true;
    }

    public boolean in_bonds(int[] x, int[] y)
    {
        for (int i = 0; i < x.length; i++)
            if (x[i] < 0 || x[i] >= width || y[i] < 0 || y[i] >= height)
                return false;

        return true;
    }

    void delete_line(int y)
    {
        for (int i = y; i < height-1; i++)
            for (int j = 0; j < width; j++)
            {
                matrice[j][i] = matrice[j][i+1];
            }

        for (int i = 0; i < width; i++)
            matrice[i][height-1] = -1;
    }

    public int check_and_delete(int[] y)
    {
        int destroyed = 0;
        Arrays.sort(y);
        for (int i = y.length - 1; i >= 0; i--)
            if (check_line(y[i]))
            {
                destroyed++;
                delete_line(y[i]);
            }

        return destroyed;
    }

    public int get(int x, int y)
    {
        return matrice[x][y];
    }

    boolean is_free(int[] abcissae, int[] ordinates)
    {
        for (int i = 0; i < abcissae.length; i++)
            if (get(abcissae[i], ordinates[i]) != -1)
                return false;

        return true;
    }

    boolean column_full()
    {
        for (int i = 0; i < width; i++)
            if (matrice[i][height-1] != -1)
                return true;

        return false;
    }
}
