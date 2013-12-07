package Component;

import java.util.Arrays;
import java.util.Comparator;

public class Grid
{
    public static final int DEFAULT_HEIGHT = 20;
    public static final int DEFAULT_WIDTH = 10;
    public static final int EMPTY_BLOCK = -1;

    private final int height;
    private final int width;

    private int grid[][];

    public Grid()
    {
        height = DEFAULT_HEIGHT;
        width = DEFAULT_WIDTH;

        init_grid();
    }

    public Grid(int w, int h)
    {
        height = h;
        width = w;

        init_grid();
    }

    public Grid(Grid grid)
    {
        this(grid.width(), grid.height());
        copy(grid);
    }

    protected synchronized void init_grid()
    {
        grid = new int[width][height];
        for (int i =  0; i < height; i++)
            empty_line(i);
    }

    public final int height()
    {
        return height;
    }

    public final int width()
    {
        return width;
    }

    public synchronized void put(Piece piece)
    {
        put(piece.coordinates(), piece.id());
    }

    public synchronized void put(Point[] coordinates, int value)
    {
        for (Point point : coordinates)
            put(point, value);
    }

    public synchronized void put(Point point, int value)
    {
        put(point.abcissa(), point.ordinate(), value);
    }

    public synchronized void put(int x, int y, int value)
    {
        if (x >= 0 && x < width && y >= 0 && y < height)
            grid[x][y] = value;
    }

    public synchronized void copy(Grid grid)
    {
        if (grid.width() == width && grid.height() == height)
            for (int i = 0; i < width; i++)
                for (int j = 0; j < height; j++)
                    put(i, j, grid.get(i, j));
    }

    public synchronized int check(Point[] y)
    {
        int destroyed = 0;

        Arrays.sort(y, Point.ORDINATE_COMPARATOR);
        for (int i = y.length - 1; i >= 0; i--)
            if (full_line(y[i].ordinate()))
            {
                destroyed++;
                delete_line(y[i].ordinate());
            }

        return destroyed;
    }

    public boolean full_line(Point[] coordinates)
    {
        for (Point point : coordinates)
            if (full_line(point.ordinate()))
                return true;

        return false;
    }

    public boolean full_line(int line)
    {
        for (int i = 0; i < width; i++)
            if (is_free(i, line))
                return false;

        return true;
    }

    public boolean in_bonds(Point[] coordinates)
    {
        for (Point point : coordinates)
            if (! in_bonds(point))
                return false;

        return true;
    }

    public boolean in_bonds(Point point)
    {
        return in_bonds(point.abcissa(), point.ordinate());
    }

    public boolean in_bonds(int x, int y)
    {
        return (x >= 0 && x < width && y >= 0 && y < height);
    }

    protected synchronized void delete_line(int line)
    {
        for (int i = line; i < height-1; i++)
            shift_down_line(i+1);

        empty_line(height-1);
    }

    protected synchronized void shift_down_line(int line)
    {
        for (int i = 0; i < width; i++)
            put(i, line-1, get(i, line));
    }

    protected synchronized void empty_line(int line)
    {
        for (int i = 0; i < width; i++)
            put(i, line, EMPTY_BLOCK);
    }

    public int get(Point point)
    {
        return get(point.abcissa(), point.ordinate());
    }

    public int get(int x, int y)
    {
        return grid[x][y];
    }

    public boolean is_free(Point[] coordinates)
    {
        for (Point point : coordinates)
            if (! is_free(point))
                return false;

        return true;
    }

    public boolean is_free(Point point)
    {
        return is_free(point.abcissa(), point.ordinate());
    }

    public boolean is_free(int x, int y)
    {
        return get(x, y) == EMPTY_BLOCK;
    }

    public boolean full()
    {
        for (int i = 0; i < width; i++)
            if (full_column(i))
                return true;

        return false;
    }

    public boolean full_column(Point[] coordinates)
    {
        for (Point point : coordinates)
            if (full_column(point.abcissa()))
                return true;

        return false;
    }

    public boolean full_column(int column)
    {
        return (! is_free(column, height - 1));
    }

    public boolean equals(Object obj)
    {
        boolean result = false;

        if (obj instanceof Grid)
        {
            Grid grid = (Grid) obj;

            result = grid.width() == width && grid.height() == height;

            for (int i = 0; result && i < width; i++)
                for (int j = 0; result && j < height; j++)
                    if (get(i, j) != grid.get(i, j))
                        result = false;
        }

        return result;
    }

}
