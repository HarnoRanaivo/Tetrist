package Component;

import java.util.Arrays;
import java.util.Comparator;

public class Grid
{
    public static final int default_height = 20;
    public static final int default_width = 10;

    protected static final int empty_block = -1;

    private final int height;
    private final int width;

    private int grid[][];

    public Grid()
    {
        height = default_height;
        width = default_width;

        init_grid();
    }

    public Grid(int width, int height)
    {
        this.height = height;
        this.width = width;

        init_grid();
    }

    private void init_grid()
    {
        grid = new int[width][height];
        for (int i =  0; i < height; i++)
            empty_line(i);
    }

    public int height()
    {
        return height;
    }

    public int width()
    {
        return width;
    }

    public void put(Point[] coordinates, int value)
    {
        for (Point point : coordinates)
            put(point, value);
    }

    public void put(Point point, int value)
    {
        put(point.abcissa(), point.ordinate(), value);
    }

    public void put(int x, int y, int value)
    {
        grid[x][y] = value;
    }

    public int check_and_delete(Point[] y)
    {
        int destroyed = 0;

        Arrays.sort(y, new Comparator<Point>()
            {
                public int compare(Point a, Point b)
                {
                    return Integer.compare(a.ordinate(), b.ordinate());
                }
                public boolean equals(Point a, Point b)
                {
                    return a.ordinate() == b.ordinate() && a.abcissa() == b.abcissa();
                }
            });
        for (int i = y.length - 1; i >= 0; i--)
            if (check_line(y[i].ordinate()))
            {
                destroyed++;
                delete_line(y[i].ordinate());
            }

        return destroyed;
    }

    public boolean check_line(int line)
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
        int x = point.abcissa();
        int y = point.ordinate();

        return (x >= 0 && x < width && y >= 0 && y < height);
    }

    public void delete_line(int line)
    {
        for (int i = line; i < height-1; i++)
            shift_down_line(i+1);

        empty_line(height-1);
    }

    protected void shift_down_line(int line)
    {
        for (int i = 0; i < width; i++)
            put(i, line-1, get(i, line));
    }

    protected void empty_line(int line)
    {
        for (int i = 0; i < width; i++)
            put(i, line, empty_block);
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
        return get(x, y) == empty_block;
    }

    public boolean full()
    {
        for (int i = 0; i < width; i++)
            if (column_full(i))
                return true;

        return false;
    }

    public boolean column_full(Point[] coordinates)
    {
        for (Point point : coordinates)
            if (column_full(point.ordinate()))
                return true;

        return false;
    }

    public boolean column_full(int column)
    {
        return (! is_free(column, height - 1));
    }
}
