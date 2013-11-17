package Basic;

import java.util.Arrays;

class Grid
{
    static final int height = 20;
    static final int width = 10;

    int grid[][];

    public Grid()
    {
        grid = new int[width][height];
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                grid[i][j] = -1;
    }

    public void put(int x, int y, int v)
    {
        grid[x][y] = v;
    }

    public void put(int[] x, int[] y, int v)
    {
        for (int i = 0; i < x.length; i++)
            grid[x[i]][y[i]] = v;
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


    boolean check_line(int y)
    {
        for (int i = 0; i < width; i++)
            if (grid[i][y] == -1)
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
                grid[j][i] = grid[j][i+1];

        for (int i = 0; i < width; i++)
            grid[i][height-1] = -1;
    }

    public int get(int x, int y)
    {
        return grid[x][y];
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
            if (grid[i][height-1] != -1)
                return true;

        return false;
    }
}
