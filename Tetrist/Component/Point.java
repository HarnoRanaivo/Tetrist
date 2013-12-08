package Component;

import java.util.Comparator;

/**
 * Classe de représentation d'un "cube".</br>
 * Utilisée pour la représentation dans une grille.
 */
public class Point
{
    /**
     * Comparateur d'abscisse de deux points.
     */
    public static final Comparator<Point> ABCISSA_COMPARATOR = new PointAbcissaComparator();

    /**
     * Comparateur d'ordonnée de deux points.
     *
     * @see Grid#check(Point[])
     */
    public static final Comparator<Point> ORDINATE_COMPARATOR = new PointOrdinateComparator();

    /**
     * Ordonnée d'un point.</br>
     * Utilisée pour récupérer l'ordonnée d'un point.
     *
     * @see Piece#minimum_ordinate()
     * @see Piece#maximum_ordinate()
     */
    public static final PointValueGetter ORDINATE_GETTER = new PointOrdinateGetter();

    /**
     * Abscisse d'un point.</br>
     * Utilisée pour récupérer l'abscisse d'un point.
     *
     * @see Piece#minimum_abcissa()
     * @see Piece#maximum_abcissa()
     */
    public static final PointValueGetter ABCISSA_GETTER = new PointAbcissaGetter();

    /**
     * Abscisse <b>d'instance</b> d'un point.
     *
     * @see Point#Constructor(Point)
     * @see Point#set(Point)
     * @see Point#set_abcissa(int)
     * @see Point#shift_abcissa(int)
     * @see Point#abcissa()
     * @see Point#equals(Object)
     */
    private int abcissa;

    /**
     * Ordonnée <b>d'instance</b> d'un point.
     *
     * @see Point#Constructor(Point)
     * @see Point#set(Point)
     * @see Point#set_ordinate(int)
     * @see Point#shift_ordinate(int)
     * @see Point#ordinate()
     * @see Point#equals(Object)
     */
    private int ordinate;

    /**
     * Constructeur d'un point prenant deux <b>entiers</b>.
     *
     * @param x
     *          Abscisse.
     * @param y
     *          Ordonnée.
     */
    public Point(int x, int y)
    {
        set(x, y);
    }

    /**
     * Constructeur d'un point à l'origine.
     */
    public Point()
    {
        this(0, 0);
    }

    /**
     * Constructeur d'un point à partir d'un autre <b>point</b>.
     *
     * @param point
     *          Point.
     */
    public Point(Point point)
    {
        this(point.abcissa(), point.ordinate());
    }

    /**
     * Value l'abscisse et l'ordonnée d'un point.
     *
     * @param point
     *          Point.
     */
    public synchronized void set(Point point)
    {
        set(point.abcissa(), point.ordinate());
    }

    /**
     * Value l'abscisse et l'ordonnée d'un point à partir de l'abscisse <b>et</b> l'ordonnée.
     *
     * @param x
     *          Abscisse.
     * @param y
     *          Ordonnée.
     */
    public synchronized void set(int x, int y)
    {
        set_abcissa(x);
        set_ordinate(y);
    }

    /**
     * Value l'abscisse du point.</br>
     * <i>(setters).</i>
     *
     * @param x
     *          Abscisse.
     */
    public synchronized void set_abcissa(int x)
    {
        abcissa = x;
    }

    /**
     * Value l'ordonnée du point.</br>
     * <i>(setters).</i>
     *
     * @param y
     *          Ordonnée.
     */
    public synchronized void set_ordinate(int y)
    {
        ordinate = y;
    }

    /**
     * Décale le point d'une certaine unité horizontale <b>et</b> vertivale.
     *
     * @param horizontal_shift
     *          Décalage horizontal.
     * @param vertical_shift
     *          Décalage vertical.
     */
    public synchronized void shift(int horizontal_shift, int vertical_shift)
    {
        shift_abcissa(horizontal_shift);
        shift_ordinate(vertical_shift);
    }

    /**
     * Décale le point d'une certaine unité horizontale.
     *
     * @param shift
     *          Décalage horizontal.
     */
    public synchronized void shift_abcissa(int shift)
    {
        set_abcissa(abcissa() + shift);
    }

    /**
     * Décale le point d'une certaine unité verticale.
     *
     * @param shift
     *          Décalage vertical.
     */
    public synchronized void shift_ordinate(int shift)
    {
        set_ordinate(ordinate() + shift);
    }

    /**
     * Valeur de l'abscisse.
     *
     * @return abcissa
     */
    public final int abcissa()
    {
        return abcissa;
    }

    /**
     * Valeur de l'ordonnée.
     *
     * @return ordinate
     */
    public final int ordinate()
    {
        return ordinate;
    }

    /**
     * Teste l'égalité entre deux points.
     *
     * @param obj
     *          Le point avec lequel on veut comparer.
     * @return <b>true</b> si les points sont égaux, <b>false</b> sinon.
     */
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

/**
 * Classe de comparateur d'abscisse de deux points.
 */
class PointAbcissaComparator implements Comparator<Point>
{
    /**
     * Comparateur d'abscisse de deux points.
     *
     * @param a
     *          Point a.
     * @param b
     *          Point b.
     * @return la <b>différence</b> d'abscisse.
     */
    public int compare(Point a, Point b)
    {
        /* Pas de static int Integer.compare(int a, int b) en Java 6 ! */
        return Integer.valueOf(a.abcissa()).compareTo(Integer.valueOf(b.abcissa()));
    }
}

/**
 * Classe de comparateur d'ordonnée de deux points.
 */
class PointOrdinateComparator implements Comparator<Point>
{
    /**
     * Comparateur d'ordonnée de deux points.
     *
     * @param a
     *          Point a.
     * @param b
     *          Point b.
     * @return la <b>différence</b> d'ordonnée.
     */
    public int compare(Point a, Point b)
    {
        return Integer.valueOf(a.ordinate()).compareTo(Integer.valueOf(b.ordinate()));
    }
}

/**
 * Classe permettant d'accéder à la valeur de l'abscisse d'un point.
 */
class PointAbcissaGetter extends PointValueGetter
{
    /**
     * Retourne la valeur de l'abscisse d'un point.
     *
     * @param point
     *          Point.
     * @return l'abscisse.
     */
    public int get_value(Point point)
    {
        return point.abcissa();
    }
}

/**
 * Classe permettant d'accéder à la valeur de l'ordonnée d'un point.
 */
class PointOrdinateGetter extends PointValueGetter
{
    /**
     * Retourne la valeur de l'ordonnée d'un point.
     *
     * @param point
     *          Point.
     * @return l'ordonnée.
     */
    public int get_value(Point point)
    {
        return point.ordinate();
    }
}
