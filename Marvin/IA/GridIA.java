package IA;

import Component.Point;
import Component.Grid;

public class GridIA extends Grid
{
    private int blocks;
    private int holes;
    private int[] highest_blocks;
    private int[] holes_columns;

    public GridIA()
    {
        super();
    }

    public GridIA(int width, int height)
    {
        super(width, height);
    }

    public GridIA(Grid grid)
    {
        this(grid.width(), grid.height());
        copy(grid);
    }

    protected synchronized void init_grid()
    {
        blocks = 0;
        holes = 0;
        int width = width();
        highest_blocks = new int[width];
        holes_columns = new int[width];
        for (int i = 0; i < width; i++)
        {
            highest_blocks[i] = -1;
            holes_columns[i] = 0;
        }

        super.init_grid();
    }

    public synchronized void put(int x, int y, int value)
    {
        super.put(x, y, value);

        if (value != EMPTY_BLOCK)
        {
            blocks++;
            highest_blocks[x] = y;
        }
        else if (--blocks < 0)
            blocks = 0;

        count_holes(x);
    }

    public synchronized int check(Point[] y)
    {
        int destroyed = super.check(y);

        if (destroyed > 0)
        {
            check_highest();
            count_holes();
        }

        return destroyed;
    }

    public int holes()
    {
        return holes;
    }

    protected void check_highest(int column)
    {
        int highest = height() - 1;

        for (int i = height() - 1; i >= 0 && get(column, i) == EMPTY_BLOCK; i--)
            highest--;

        highest_blocks[column] = highest;
    }

    protected void check_highest()
    {
        for (int i = 0; i < width(); i++)
            check_highest(i);
    }

    public synchronized void copy(Grid grid)
    {
        if (grid.width() == width() && grid.height() == height())
        {
            init_grid();
            super.copy(grid);
        }
    }

    public int blocks()
    {
        return blocks;
    }

    public int[] highest_blocks()
    {
        return highest_blocks;
    }

    public int highest_block(int column)
    {
        return highest_blocks[column];
    }

    protected void count_holes(int column)
    {
        int local_holes = 0;

        for (int i = highest_block(column); i >= 0; i--)
            if (is_free(column, i))
                local_holes++;

        if (local_holes != holes_columns[column])
        {
            holes += local_holes - holes_columns[column];
            holes_columns[column] = local_holes;
        }
    }

    protected void count_holes()
    {
        for (int i = 0; i < width(); i++)
            count_holes(i);
    }

    public int eval()
    {
        return 0;
    }
}
