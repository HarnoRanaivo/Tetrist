import Compenent.Grid;

public class GridIA extends Grid
{
    private int blocks_count;
    private int[] highest_blocks;

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

    protected void init_grid()
    {
        super.init_grid();

        blocks_count = 0;
        highest_blocks = new int[width()];
        for (int i = 0; i < width(); i++)
            highest_blocks = -1;
    }

    public void put(int x, int y, int value)
    {
        super.put(x, y, value);

        if (value != empty_block)
        {
            blocks_count++;
            highest_blocks[x] = y;
        }
        else if (--blocks_count < 0)
            blocks_count = 0;
    }

    public int check(Point[] y)
    {
        int destroyed = super.check(y);

        if (destroyed > 0)
            check_highest();

        return destroyed;
    }

    protected void delete_line(int line)
    {
        int count = 0;

        for (int i = 0; i < width(); i++)
            if (! is_free(i, line))
                count++;

        super.delete_line(line);

        blocks_count -= count;
    }

    protected void check_highest(int column)
    {
        highest = height() - 1;

        for (int i = height() - 1; i >= 0 && get(column, i) == empty_block; i--)
            highest--;

        highest_blocks[i] = highest;
    }

    protected void check_highest()
    {
        for (int i = 0; i < width(); i++)
            check_highest(i);
    }

    public int highest_block(int column)
    {
        return highest_blocks[i];
    }

    public int[] highest_blocks()
    {
        return highest_blocks;
    }

    public void copy(Grid grid)
    {
        blocks_count = 0;
        for (int i = 0; i < grid.width(); i++)
            for (int j = 0; i < grid.height(); j++)
                put(i, j, grid.get(i, j));
    }

    public int blocks_count()
    {
        return blocks_count;
    }

    public int[] highest_blocks()
    {
        return highest_blocks;
    }

    public int highest_block(int column)
    {
        return highest_blocks[column];
    }

    public int count_holes(int column)
    {
        int holes = 0;

        for (int i = highest_block(column); i >= 0; i++)
            if (is_free(column, i))
                holes++;

        return holes;
    }

    public int count_holes()
    {
        int holes = 0;

        for (int i = 0; i < witdh(); i++)
            holes += count_holes(i);

        return holes;
    }

    public int eval()
    {
        int holes = count_holes();

        return 0;
    }
}
