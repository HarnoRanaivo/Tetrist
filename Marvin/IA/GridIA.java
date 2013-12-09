package IA;

import Component.Point;
import Component.Grid;
import Component.Piece;

/**
 * Classe d'une grille d'IA.
 */
public class GridIA extends Grid
{
    /**
     * Nombre de blocs.
     *
     * @see GridIA#init_grid()
     * @see GridIA#put(int,int,int)
     * @see GridIA#blocks()
     */
    private int blocks;

    /**
     * Nombre de trous.
     *
     * @see GridIA#init_grid()
     * @see GridIA#count_holes(int)
     * @see GridIA#holes()
     */
    private int holes;

    /**
     * Bloc le plus haut.
     *
     * @see GridIA#init_grid()
     * @see GridIA#put(int,int,int)
     * @see GridIA#count_holes(int)
     * @see GridIA#highest_block()
     * @see GridIA#check_highest()
     * @see GridIA#check_highest(int)
     */
    private int highest_block;

    /**
     * Colonne la moins haute.
     *
     * @see GridIA#init_grid()
     * @see GridIA#check_smallest_size()
     * @see GridIA#smallest_size()
     */
    private int smallest_column_size;

    /**
     * Tableau de blocs.
     *
     * @see GridIA#init_grid()
     * @see GridIA#put(int,int,int)
     * @see GridIA#blocks_array()
     * @see GridIA#blocks()
     */
    private int[] blocks_array;

    /**
     * Tableau des plus hauts blocs.
     *
     * @see GridIA#init_grid()
     * @see GridIA#put(int,int,int)
     * @see GridIA#check_highest(int)
     * @see GridIA#highest_block(int)
     * @see GridIA#highest_blocks_array()
     * @see GridIA#smallest_size()
     */
    private int[] highest_blocks_array;

    /**
     * Tableau de trous.
     *
     * @see GridIA#init_grid()
     * @see GridIA#holes(int)
     * @see GridIA#holes_array()
     * @see GridIA#count_holes(int)
     */
    private int[] holes_array;

    /**
     * Constructeur d'une grille d'IA.</br>
     * <i>(pour le robot)</i>
     */
    public GridIA()
    {
        super();
    }

    /**
     * Constructeur d'une grille d'IA.</br>
     * <i>(pour le robot)</i>
     *
     * @param width
     *          Largeur de la grille.
     * @param height
     *          Hauteur de la grille.
     */
    public GridIA(int width, int height)
    {
        super(width, height);
    }

    /**
     * Constructeur d'une grille d'IA.</br>
     * <i>(pour le robot)</i>
     *
     * @param grid
     *          Une grille.
     */
    public GridIA(Grid grid)
    {
        this(grid.width(), grid.height());
        copy(grid);
    }

    /**
     * Initialisation de la grille de l'IA.
     */
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

    /**
     * Vérifie le nombre de ligne supprimée quand elles sont pleines.
     *
     * @param y
     *          Ligne de points.
     */
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

    /**
     * Retourne le nombre de trous.
     *
     * @return nombre de trous.
     */
    public int holes()
    {
        return holes;
    }

    /**
     * Retourne le nombre de trous dans une colonne.
     *
     * @param column
     *          Colonne étudiée.
     * @return nombre de trous dans column.
     */
    public int holes(int column)
    {
        return holes_array[column];
    }

    /**
     * Retourne un tableau de trous.
     *
     * @return Tableau de trous.
     */
    public int[] holes_array()
    {
        return holes_array;
    }

    /**
     * Retourne un tableau de blocs.
     *
     * @return Tableau de blocs.
     */
    public int[] blocks_array()
    {
        return blocks_array;
    }

    /**
     * Assigne le plus haut bloc actuel d'une colonne.
     *
     * @param column
     *          Colonne à vérifier.
     */
    protected synchronized void check_highest(int column)
    {
        int highest;

        for (highest = height() - 1; highest >= 0 && is_free(column, highest); highest--)
            ;

        highest_blocks_array[column] = highest;
        if (highest > highest_block)
            highest_block = highest;
    }

    /**
     * Assigne les plus hauts blocs de toutes les colonnes.
     */
    protected synchronized void check_highest()
    {
        highest_block = -1;
        for (int i = 0; i < width(); i++)
            check_highest(i);
    }

    /**
     * Copie une grille dans celle actuelle.
     *
     * @param grid
     *          Grille.
     */
    public synchronized void copy(Grid grid)
    {
        if (grid.width() == width() && grid.height() == height())
        {
            init_grid();
            super.copy(grid);
        }
    }

    /**
     * Retourne le nombre de blocs.
     *
     * @return nombre de blocs.
     */
    public int blocks()
    {
        return blocks;
    }

    /**
     * Retourne le nombre de blocs pour une colonne.
     *
     * @param column
     *          Colonne étudiée.
     * @return nombre de blocs de column.
     */
    public int blocks(int column)
    {
        return blocks_array[column];
    }

    /**
     * Retourne le tableau des plus hauts blocs.
     *
     * @return Tableau des plus hauts blocs.
     */
    public int[] highest_blocks_array()
    {
        return highest_blocks_array;
    }

    /**
     * Retourne le plus haut bloc d'une colonne.
     *
     * @param column
     *          Colonne étudiée.
     * @return Le plus haut bloc de column.
     */
    public int highest_block(int column)
    {
        return highest_blocks_array[column];
    }

    /**
     * Retourne le plus haut bloc.
     *
     * @return Le plus haut bloc.
     */
    public int highest_block()
    {
        return highest_block;
    }

    /**
     * Compte le nombre de trous dans une colonne.
     *
     * @param column
     *          Colonne étudiée.
     */
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

    /**
     * Compte le nombre de trous.
     */
    protected synchronized void count_holes()
    {
        for (int i = 0; i < width(); i++)
            count_holes(i);
    }

    /**
     * Evalue la grille.
     *
     * @return L'état de la grille.
     */
    public synchronized GridState eval()
    {
        return new GridState(this);
    }

    /**
     * Fait tomber la pièce.</br>
     * Maximise la vitesse de jeu de l'IA.
     *
     * @param piece
     *          Pièce à faire tomber.
     */
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

    /**
     * Assigne la plus petite colonne.
     */
    protected synchronized void check_smallest_size()
    {
        int smallest = height() - 1;
        for (int size : highest_blocks_array)
            if (size < smallest)
                smallest = size;

        smallest_column_size = smallest;
    }

    /**
     * Retourne la plus "petite" colonne.
     *
     * @return Le plus bas des indices de hauteurs.
     */
    public int smallest_column_size()
    {
        return smallest_column_size;
    }
}
