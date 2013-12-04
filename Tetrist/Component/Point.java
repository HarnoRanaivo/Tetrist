package Component;

import java.util.Comparator;

public class Point
{
    public static final Comparator<Point> ABCISSA_COMPARATOR = new PointAbcissaComparator();
    public static final Comparator<Point> ORDINATE_COMPARATOR = new PointOrdinateComparator();
    public static final PointValueGetter ORDINATE_GETTER = new PointOrdinateGetter();
    public static final PointValueGetter ABCISSA_GETTER = new PointAbcissaGetter();

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

    public synchronized void set(Point point)
    {
        set(point.abcissa(), point.ordinate());
    }

    public synchronized void set(int x, int y)
    {
        set_abcissa(x);
        set_ordinate(y);
    }

    public synchronized void set_abcissa(int x)
    {
        abcissa = x;
    }

    public synchronized void set_ordinate(int y)
    {
        ordinate = y;
    }

    public synchronized void shift(int horizontal_shift, int vertical_shift)
    {
        shift_abcissa(horizontal_shift);
        shift_ordinate(vertical_shift);
    }

    public synchronized void shift_abcissa(int shift)
    {
        set_abcissa(abcissa() + shift);
    }

    public synchronized void shift_ordinate(int shift)
    {
        set_ordinate(ordinate() + shift);
    }

    public final int abcissa()
    {
        return abcissa;
    }

    public final int ordinate()
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

class PointAbcissaComparator implements Comparator<Point>
{
    public int compare(Point a, Point b)
    {
        /* Pas de static int Integer.compare(int a, int b) en Java 6 ! */
        return Integer.valueOf(a.abcissa()).compareTo(Integer.valueOf(b.abcissa()));
    }
}

class PointOrdinateComparator implements Comparator<Point>
{
    public int compare(Point a, Point b)
    {
        return Integer.valueOf(a.ordinate()).compareTo(Integer.valueOf(b.ordinate()));
    }
}

class PointAbcissaGetter extends PointValueGetter
{
    public int get_value(Point point)
    {
        return point.abcissa();
    }
}

class PointOrdinateGetter extends PointValueGetter
{
    public int get_value(Point point)
    {
        return point.ordinate();
    }
}
