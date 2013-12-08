package Component;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Classe de représentation d'une grille.
 */
public class Grid
{
    /**
     * Hauteur par défaut.
     *
     * @see Grid#Constructor()
     */
    public static final int DEFAULT_HEIGHT = 20;

    /**
     * Largeur par défaut.
     *
     * @see Grid#Constructor()
     */
    public static final int DEFAULT_WIDTH = 10;

    /**
     * Valeur d'une case vide.
     *
     * @see Grid#empty_line(int)
     * @see Grid#is_free(int,int)
     */
    public static final int EMPTY_BLOCK = -1;

    /**
     * Hauteur du jeu. <i>(en case)</i>
     *
     * @see Grid#Constructor()
     * @see Grid#Constructor(int,int)
     * @see Grid#init_grid()
     * @see Grid#height()
     * @see Grid#put(int,int,int)
     * @see Grid#copy(Grid)
     * @see Grid#in_bonds(int,int)
     * @see Grid#delete_line(int)
     * @see Grid#full_column(int)
     * @see Grid#equals(Object)
     */
    private final int height;

    /**
     * Largeur du jeu. <i>(en case)</i>
     *
     * @see Grid#Constructor()
     * @see Grid#Constructor(int,int)
     * @see Grid#init_grid()
     * @see Grid#width()
     * @see Grid#put(int,int,int)
     * @see Grid#full_line(int)
     * @see Grid#copy(Grid)
     * @see Grid#in_bonds(int,int)
     * @see Grid#shift_down_line(int)
     * @see Grid#empty_line(int)
     * @see Grid#full()
     * @see Grid#equals(Object)
     */
    private final int width;

    /**
     * Grille d'entier.
     *
     * @see Grid#init_grid()
     * @see Grid#put(int,int,int)
     * @see Grid#copy(Grid)
     * @see Grid#get(int,int)
     * @see Grid#equals(Object)
     */
    private int grid[][];


    /**
     * Constructeur de grilles.
     */
    public Grid()
    {
        height = DEFAULT_HEIGHT;
        width = DEFAULT_WIDTH;

        init_grid();
    }

    /**
     * Constructeur de grilles avec une hauteur <b>et</b> une largeur.
     *
     * @param w
     *          Largeur.
     * @param h
     *          Hauteur.
     */
    public Grid(int w, int h)
    {
        height = h;
        width = w;

        init_grid();
    }

    /**
     * Constructeur de grilles à l'aide d'une grille.
     *
     * @param grid
     *          Grille de Tetrist.
     */
    public Grid(Grid grid)
    {
        this(grid.width(), grid.height());
        copy(grid);
    }

    /**
     * Initialisation de la grille.
     */
    protected synchronized void init_grid()
    {
        grid = new int[width][height];
        for (int i =  0; i < height; i++)
            empty_line(i);
    }

    /**
     * Retourne la hauteur de la grille.
     *
     * @return hauteur.
     */
    public final int height()
    {
        return height;
    }

    /**
     * Retourne la largeur de la grille.
     *
     * @return largeur.
     */
    public final int width()
    {
        return width;
    }

    /**
     * Place une pièece dans la grill
     *
     * @param piece
     *          Pièece.
     */
    public synchronized void put(Piece piece)
    {
        put(piece.coordinates(), piece.id());
    }

    /**
     * Place une valeur dans un ensemble de cases.
     *
     * @param coordinates
     *          Ensemble de Points.
     * @param value
     *          Valeur affectée.
     */
    public synchronized void put(Point[] coordinates, int value)
    {
        for (Point point : coordinates)
            put(point, value);
    }

    /**
     * Place une valeur dans une case.
     *
     * @param point
     *          Point.
     * @param value
     *          Valeur affectée.
     */
    public synchronized void put(Point point, int value)
    {
        put(point.abcissa(), point.ordinate(), value);
    }

    /**
     * Place une valeur dans la case {x;y}.
     *
     * @param x
     *          Abscisse.
     * @param y
     *          Ordonnée.
     * @param value
     *          Valeur affectée.
     */
    public synchronized void put(int x, int y, int value)
    {
        if (x >= 0 && x < width && y >= 0 && y < height)
            grid[x][y] = value;
    }

    /**
     * Copie une grille dans celle actuelle.
     *
     * @param grid
     *          Grille.
     */
    public synchronized void copy(Grid grid)
    {
        if (grid.width() == width && grid.height() == height)
            for (int i = 0; i < width; i++)
                for (int j = 0; j < height; j++)
                    put(i, j, grid.get(i, j));
    }

    /**
     * Vérifie le nombre de ligne supprimée quand elles sont pleines.
     *
     * @param y
     *          Ligne de points.
     */
    public synchronized int check(Point[] y)
    {
        int destroyed = 0;

        Arrays.sort(y, Point.ORDINATE_COMPARATOR);
        for (int i = y.length - 1; i >= 0; i--)
            if (full_line(y[i].ordinate()))
            {
                destroyed++;
                delete_line(y[i].ordinate());
            }

        return destroyed;
    }

    /**
     * Vérifie si une ligne est pleine.
     *
     * @param coordinates
     *          Ligne de points.
     * @return <b>true</b> si la ligne est pleine, <b>false</b> sinon.
     */
    public boolean full_line(Point[] coordinates)
    {
        for (Point point : coordinates)
            if (full_line(point.ordinate()))
                return true;

        return false;
    }

    /**
     * Vérifie si une ligne est pleine.
     *
     * @param coordinates
     *          Numéro d'une ligne de points.
     * @return <b>true</b> si la ligne est pleine, <b>false</b> sinon.
     */
    public boolean full_line(int line)
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

    /**
     * Vérifie si les coordonnées d'un point sont valides.
     *
     * @param point
     *          Point.
     * @return <b>true</b> si les coordonnées du point sont valides, <b>false</b> sinon.
     */
    public boolean in_bonds(Point point)
    {
        return in_bonds(point.abcissa(), point.ordinate());
    }

    /**
     * Vérifie si les coordonnées d'un point sont valides.
     *
     * @param x
     *          Abscisse.
     * @param y
     *          Ordonnée.
     * @return <b>true</b> si les coordonnées sont valides, <b>false</b> sinon.
     */
    public boolean in_bonds(int x, int y)
    {
        return (x >= 0 && x < width && y >= 0 && y < height);
    }

    /**
     * Supprime une ligne.
     *
     * @param line
     *          Ligne à supprimer.
     */
    protected synchronized void delete_line(int line)
    {
        for (int i = line; i < height-1; i++)
            shift_down_line(i+1);

        empty_line(height-1);
    }

    /**
     * Décale une ligne vers le bas.
     *
     * @param line
     *          Ligne à décaler.
     */
    protected synchronized void shift_down_line(int line)
    {
        for (int i = 0; i < width; i++)
            put(i, line-1, get(i, line));
    }

    /**
     * Assigne les valeurs d'une ligne vide.<i>(-1)</i>
     *
     * @param line
     *          Ligne à vider.
     */
    protected synchronized void empty_line(int line)
    {
        for (int i = 0; i < width; i++)
            put(i, line, EMPTY_BLOCK);
    }

    /**
     * Retourne la valeur en un point.
     *
     * @param point
     *          Point.
     * @return valeur.
     */
    public int get(Point point)
    {
        return get(point.abcissa(), point.ordinate());
    }

    /**
     * Retourne la valeur en un abscisse et une ordonnée.
     *
     * @param x
     *          Abscisse.
     * @param y
     *          Ordonnée.
     * @return valeur.
     */
    public int get(int x, int y)
    {
        return grid[x][y];
    }

    /**
     * Vérifie si un ensemble de cases est un ensemble de cases vides.
     *
     * @param coordinates
     *          Ensemble de points.
     * @return <b>true</b> si la case est vide, <b>false</b> sinon.
     */
    public boolean is_free(Point[] coordinates)
    {
        for (Point point : coordinates)
            if (! is_free(point))
                return false;

        return true;
    }

    /**
     * Vérifie si une case est vide.
     *
     * @param point
     *          Point.
     * @return <b>true</b> si la case est vide, <b>false</b> sinon.
     */
    public boolean is_free(Point point)
    {
        return is_free(point.abcissa(), point.ordinate());
    }

    /**
     * Vérifie si une case est vide.
     *
     * @param x
     *          Abscisse.
     * @param y
     *          Ordonnée.
     * @return <b>true</b> si la case est vide, <b>false</b> sinon.
     */
    public boolean is_free(int x, int y)
    {
        return get(x, y) == EMPTY_BLOCK;
    }

    /**
     * Vérifie si une colonne du jeu est pleine.
     *
     * @return <b>true</b> si une colonne est pleine, <b>false</b> sinon.
     */
    public boolean full()
    {
        for (int i = 0; i < width; i++)
            if (full_column(i))
                return true;

        return false;
    }

    /**
     * Vérifie si une des colonnes d'un ensemble de points est pleine.
     *
     * @param coordinates
     *          Ensemble de points.
     * @return <b>true</b> si une colonne est pleine, <b>false</b> sinon.
     */
    public boolean full_column(Point[] coordinates)
    {
        for (Point point : coordinates)
            if (full_column(point.abcissa()))
                return true;

        return false;
    }

    /**
     * Vérifie si une colonne est pleine.
     *
     * @param column
     *          Numéro de la colonne.
     * @return <b>true</b> si la colonne est pleine, <b>false</b> sinon.
     */
    public boolean full_column(int column)
    {
        return (! is_free(column, height - 1));
    }

    /**
     * Teste l'égalité entre deux griles.
     *
     * @param obj
     *          Grille.
     * @return <b>true</b> si les grilles sont égales, <b>false</b> sinon.
     */
    public boolean equals(Object obj)
    {
        boolean result = false;

        if (obj instanceof Grid)
        {
            Grid grid = (Grid) obj;

            result = grid.width() == width && grid.height() == height;

            for (int i = 0; result && i < width; i++)
                for (int j = 0; result && j < height; j++)
                    if (get(i, j) != grid.get(i, j))
                        result = false;
        }

        return result;
    }
}
