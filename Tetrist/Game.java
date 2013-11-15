import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.Random;

class Game extends UnicastRemoteObject implements MatriceInterface
{
    protected static int[] points_scale = { 0, 40, 100, 300, 1200 };

    Grid grid;
    Piece current;
    Piece next;
    int points;

    protected int[] temp_x;
    protected int[] temp_y;
    protected Random generator;

    protected boolean is_busy;
    protected boolean matrice_IA[][];

    protected Piece pieces[];

    Game() throws RemoteException
    {
        super();

        temp_x = new int[4];
        temp_y = new int[4];
        matrice_IA = new boolean[Grid.width][Grid.height];
        grid = new Grid();
        generator = new Random();
        points = 0;
        pieces = new Piece[7];
        for (int i = 0; i < 7; i++)
            pieces[i] = new Piece(i, Grid.width/2, Grid.height-2);
        int id = generator.nextInt(7);
        next = pieces[id];
        next.reset();
        new_piece();
    }

    // Appel distant pour la IA
    public boolean[][] get_matrice() throws RemoteException
    {
        if (is_busy)
            return null;

        return matrice_IA;
    }
    // Fin de l'appel distant.

    protected void refresh()
    {
        is_busy = true;
        for (int y = 0; y < Grid.height; y++)
            for (int x = 0; x < Grid.width; x++)
                matrice_IA[x][y] = grid.grid[x][y] != -1;

        for (int i = 0; i < 4; i++)
            matrice_IA[current.abcissae[i]][current.ordinates[i]] = true;
        is_busy = false;
    }

    public Grid grid()
    {
        return grid;
    }

    public void new_piece()
    {
        current = next;
        int id = generator.nextInt(7);
        next = pieces[id];
        next.reset();
    }

    public void fall()
    {
        current.needed_space_fall(temp_x, temp_y);
        if (grid.in_bonds(temp_x, temp_y) && grid.is_free(temp_x, temp_y))
            current.fall();
        else
        {
            grid.put(current.abcissae, current.ordinates, current.id);
            int destroyed = grid.check_and_delete(current.ordinates);
            points += lines_to_points(destroyed);
            if (! game_is_over())
                new_piece();
        }
    }

    public void left()
    {
        current.needed_space_left(temp_x, temp_y);
        if (grid.in_bonds(temp_x, temp_y) && grid.is_free(temp_x, temp_y))
            current.left();
    }

    public void right()
    {
        current.needed_space_right(temp_x, temp_y);
        if (grid.in_bonds(temp_x, temp_y) && grid.is_free(temp_x, temp_y))
            current.right();
    }

    public void rotate()
    {
        current.needed_space_rotation(temp_x, temp_y);
        if (grid.in_bonds(temp_x, temp_y) && grid.is_free(temp_x, temp_y))
            current.rotate();
    }

    public boolean game_is_over()
    {
        return grid.column_full();
    }

    int lines_to_points(int destroyed)
    {
        int index = (destroyed < 0 || destroyed >= points_scale.length) ? 0 : destroyed;
        return points_scale[index];
    }
}
