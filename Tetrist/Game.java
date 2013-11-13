import java.rmi.RemoteException;
import java.util.Random;

class Game
{
    Matrice grid;
    Piece current;
    int[] abcissae;
    int[] ordinates;
    Random generator;
    public int points;
    int[] scale = { 0, 40, 100, 300, 1200 };

    Game() throws RemoteException
    {
        abcissae = new int[4];
        ordinates = new int[4];
        try
        {
            grid = new Matrice();
        }
        catch (RemoteException re)
        {
            throw(re);
        }
        Piece.fill_pieces_set();
        generator = new Random();
        points = 0;
        new_piece();
    }

    public Matrice grid()
    {
        return grid;
    }

    public void new_piece()
    {
        int id = generator.nextInt(7);
        current = new Piece(id, grid.width/2, grid.height-2);
    }

    public void fall()
    {
        current.needed_space_fall(abcissae, ordinates);
        if (grid.in_bonds(abcissae, ordinates) && grid.is_free(abcissae, ordinates))
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
        current.needed_space_left(abcissae, ordinates);
        if (grid.in_bonds(abcissae, ordinates) && grid.is_free(abcissae, ordinates))
            current.left();
    }

    public void right()
    {
        current.needed_space_right(abcissae, ordinates);
        if (grid.in_bonds(abcissae, ordinates) && grid.is_free(abcissae, ordinates))
            current.right();
    }

    public void rotate()
    {
        current.needed_space_rotation(abcissae, ordinates);
        if (grid.in_bonds(abcissae, ordinates) && grid.is_free(abcissae, ordinates))
            current.rotate();
    }

    public boolean game_is_over()
    {
        return grid.column_full();
    }

    int lines_to_points(int destroyed)
    {
        return scale[destroyed];
    }
}
