package IA;

import Component.Point;
import Component.Grid;
import Component.Piece;

public class GridIA extends Grid
{
    private int blocks;
    private int holes;
    private int highest_block;
    private int smallest_column_size;
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
        int width = width();
        blocks = 0;
        holes = 0;
        highest_block = -1;
        smallest_column_size = -1;
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
        check_highest();
    }

    public synchronized void put(int x, int y, int value)
    {
        int old_value = get(x, y);

        /* /!\ Modifier la grille avant de faire les vérifications ! */
        super.put(x, y, value);
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
            blocks_array[x]--;
            if (blocks < 0)
                blocks = 0;
            if (blocks_array[x] < 0)
                blocks_array[x] = 0;
            check_highest();
        }
        count_holes(x);
        check_smallest_size();
    }

    public synchronized int check(Point[] y)
    {
        int destroyed = super.check(y);

        if (destroyed > 0)
        {
            check_highest();
            count_holes();
            check_smallest_size();
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
        int highest;

        for (highest = height() - 1; highest >= 0 && is_free(column, highest); highest--)
            ;

        highest_blocks_array[column] = highest;
        if (highest > highest_block)
            highest_block = highest;
    }

    protected synchronized void check_highest()
    {
        highest_block = -1;
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

    public int blocks(int column)
    {
        return blocks_array[column];
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

    public synchronized void brute_fall(Piece piece)
    {
        Point[] needed_space = new Point[4];
        for (int i = 0; i < 4; i++)
            needed_space[i] = new Point();

        piece.needed_space_fall(needed_space);
        while (in_bonds(needed_space) && is_free(needed_space))
        {
            piece.fall();
            piece.needed_space_fall(needed_space);
        }

        put(piece);
    }

    protected synchronized void check_smallest_size()
    {
        int smallest = height() - 1;
        for (int size : highest_blocks_array)
            if (size < smallest)
                smallest = size;

        smallest_column_size = smallest;
    }

    public int smallest_column_size()
    {
        return smallest_column_size;
    }
}
