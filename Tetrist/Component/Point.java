package Component;

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
        this(0, 0);
    }

    public Point(Point point)
    {
        this(point.abcissa(), point.ordinate());
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
        set_abcissa(abcissa() + shift);
    }

    public void shift_ordinate(int shift)
    {
        set_ordinate(ordinate() + shift);
    }

    public int abcissa()
    {
        return abcissa;
    }

    public int ordinate()
    {
        return ordinate;
    }

    public boolean equals(Object obj)
    {
        boolean result;
        if (obj instanceof Point)
        {
            Point point = (Point) obj;
            result = abcissa() == point.abcissa() && ordinate() == point.ordinate();
        }
        else
            result = false;

        return result;
    }
}
