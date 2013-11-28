package Component;

import java.util.Comparator;

public class Point
{
    private int abcissa;
    private int ordinate;

    public Point(int x, int y)
    {
        set(x, y);
    }

    public Point()
    {
        set(0, 0);
    }

    public void set(Point point)
    {
        set(point.abcissa(), point.ordinate());
    }

    public void set(int x, int y)
    {
        set_abcissa(x);
        set_ordinate(y);
    }

    public void set_abcissa(int x)
    {
        abcissa = x;
    }

    public void set_ordinate(int y)
    {
        ordinate = y;
    }

    public void shift(int horizontal_shift, int vertical_shift)
    {
        shift_abcissa(horizontal_shift);
        shift_ordinate(vertical_shift);
    }

    public void shift_abcissa(int shift)
    {
        abcissa += shift;
    }

    public void shift_ordinate(int shift)
    {
        ordinate += shift;
    }

    public int abcissa()
    {
        return abcissa;
    }

    public int ordinate()
    {
        return ordinate;
    }
}
