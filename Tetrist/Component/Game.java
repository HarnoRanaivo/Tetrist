package Component;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class Game extends UnicastRemoteObject implements MatrixInterface
{
    private static final int score_factor = 1000;
    protected static final int[] score_scale = { 0, 40, 100, 300, 1200 };

    private final int middle;
    private final int top;

    private final Grid grid;
    private Piece current;
    private Piece next;
    private int score;
    private int lines;
    private int level;
    private int threshold;

    private Point[] temp_points;
    private PieceGenerator generator;

    private boolean is_busy;
    private boolean matrice_IA[][];

    public Game() throws RemoteException
    {
        this(Piece.RANDOM);
    }

    public Game(PieceGenerator pg) throws RemoteException
    {
        generator = pg;
        grid = new Grid();
        middle = grid.width() / 2;
        top = grid.height() - 1;

        init();
    }

    private void init()
    {
        temp_points = new Point[4];
        for (int i = 0; i < temp_points.length; i++)
            temp_points[i] = new Point();
        matrice_IA = new boolean[grid.width()][grid.height()];
        score = 0;
        level = 1;
        threshold = score_factor;
        next = generator.new_piece(middle, top);
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

    public synchronized void refresh()
    {
        is_busy = true;
        for (int y = 0; y < grid.height(); y++)
            for (int x = 0; x < grid.width(); x++)
                matrice_IA[x][y] = ! grid.is_free(x, y);

        Point[] coordinates = current.coordinates();
        for (Point point : coordinates)
            if (grid.in_bonds(point))
                matrice_IA[point.abcissa()][point.ordinate()] = true;
        is_busy = false;
    }

    public Grid grid()
    {
        return grid;
    }

    public Piece current_piece()
    {
        return current;
    }

    public Piece next_piece()
    {
        return next;
    }

    private synchronized void new_piece()
    {
        current = next;
        next = generator.new_piece(middle, top);
    }

    public int level()
    {
        return level;
    }

    public int lines_destroyed()
    {
        return lines;
    }

    private void compute_level()
    {
        if (score >= threshold)
        {
            level++;
            threshold = score_factor * level * level;
        }
    }

    public synchronized void fall()
    {
        current.needed_space_fall(temp_points);
        if (grid.in_bonds(temp_points) && grid.is_free(temp_points))
            current.fall();
        else
        {
            grid.put(current.coordinates(), current.id());
            int destroyed = grid.check(current.coordinates());
            lines += destroyed;
            score += lines_to_score(destroyed);
            compute_level();
            new_piece();
        }
    }

    public synchronized void left()
    {
        current.needed_space_left(temp_points);
        if (grid.in_bonds(temp_points) && grid.is_free(temp_points))
            current.left();
    }

    public synchronized void right()
    {
        current.needed_space_right(temp_points);
        if (grid.in_bonds(temp_points) && grid.is_free(temp_points))
            current.right();
    }

    public synchronized void rotate()
    {
        current.needed_space_rotation(temp_points);
        if (grid.in_bonds(temp_points) && grid.is_free(temp_points))
            current.rotate();
    }

    public boolean game_is_over()
    {
        return grid.full();
    }

    private int lines_to_score(int destroyed)
    {
        int index = (destroyed < 0 || destroyed >= score_scale.length) ? 0 : destroyed;
        return score_scale[index];
    }

    public int score()
    {
        return score;
    }
}
