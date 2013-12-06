package IA;

import Component.Point;
import Component.Grid;

public class GridIA extends Grid
{
    private int blocks;
    private int holes;
    private int highest_block;
    private int[] blocks_array;
    private int[] highest_blocks_array;
    private int[] holes_array;

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
        highest_block = -1;
        highest_blocks_array = new int[width];
        holes_array = new int[width];
        blocks_array = new int[width];
        for (int i = 0; i < width; i++)
        {
            highest_blocks_array[i] = -1;
            holes_array[i] = 0;
            blocks_array[i] = 0;
        }

        super.init_grid();
    }

    public synchronized void put(int x, int y, int value)
    {
        int old_value = get(x, y);

        if (value != EMPTY_BLOCK)
        {
            if (old_value == EMPTY_BLOCK)
            {
                blocks++;
                blocks_array[x]++;
            }
            highest_blocks_array[x] = y;
            if (y > highest_block)
                highest_block = y;
        }
        else if (old_value != EMPTY_BLOCK)
        {
            blocks--;
            if (blocks < 0)
                blocks = 0;
            check_highest(x);
        }
        super.put(x, y, value);
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

    public int holes(int column)
    {
        return holes_array[column];
    }

    public int[] holes_array()
    {
        return holes_array;
    }

    public int[] blocks_array()
    {
        return blocks_array;
    }

    protected synchronized void check_highest(int column)
    {
        int highest = height() - 1;

        for (int i = height() - 1; i >= 0 && get(column, i) == EMPTY_BLOCK; i--)
            highest--;

        highest_blocks_array[column] = highest;
    }

    protected synchronized void check_highest()
    {
        highest_block = -1;
        for (int i = 0; i < width(); i++)
        {
            check_highest(i);
            if (highest_blocks_array[i] > highest_block)
                highest_block = highest_blocks_array[i];
        }
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

    public int[] highest_blocks_array()
    {
        return highest_blocks_array;
    }

    public int highest_block(int column)
    {
        return highest_blocks_array[column];
    }

    public int highest_block()
    {
        return highest_block;
    }

    protected synchronized void count_holes(int column)
    {
        int local_holes = 0;

        for (int i = highest_block(column); i >= 0; i--)
            if (is_free(column, i))
                local_holes++;

        if (local_holes != holes_array[column])
        {
            holes += local_holes - holes_array[column];
            holes_array[column] = local_holes;
        }
    }

    protected synchronized void count_holes()
    {
        for (int i = 0; i < width(); i++)
            count_holes(i);
    }

    public synchronized GridState eval()
    {
        return new GridState(this);
    }
}
