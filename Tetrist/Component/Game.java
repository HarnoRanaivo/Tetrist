package Component;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

/**
 * Classe d'une partie de Tetrist.
 */
public class Game extends UnicastRemoteObject implements GameInterface
{
    /**
     * Entier utilisé dans le calcul de passage de niveau.
     *
     * @see Game#init()
     * @see Game#compute_level()
     */
    private static final int score_factor = 1000;

    /**
     * Gain de points en fonction du nombre de lignes supprimées.
     *
     * @see Game#lines_to_score(int)
     */
    protected static final int[] score_scale = { 0, 40, 100, 300, 1200 };


    /**
     * Milieu de la grille <b>en largeur</b>.
     *
     * @see Game#Constructor(PieceGenerator)
     * @see Game#init()
     * @see Game#new_piece()
     */
    private final int middle;
    /**
     * Hauteur de la grille.
     *
     * @see Game#Constructor(PieceGenerator)
     * @see Game#init()
     * @see Game#new_piece()
     */
    private final int top;

    /**
     * Grille du jeu.
     *
     * @see Game#Constructor(PieceGenerator)
     * @see Game#init()
     * @see Game#refresh()
     * @see Game#grid()
     * @see Game#fall()
     * @see Game#left()
     * @see Game#right()
     * @see Game#rotate()
     * @see Game#game_is_over()
     */
    private final Grid grid;


    /**
     * Pièce courante du jeu.
     *
     * @see Game#refresh()
     * @see Game#current_piece()
     * @see Game#new_piece()
     * @see Game#fall()
     * @see Game#left()
     * @see Game#right()
     * @see Game#rotate()
     */
    private Piece current;

    /**
     * Pièce suivante du jeu.</br>
     * <i>(non utilisée par l'IA)</i>
     *
     * @see Game#init()
     * @see Game#next_piece()
     * @see Game#new_piece()
     */
    private Piece next;

    /**
     * Score du jeu.
     *
     * @see Game#init()
     * @see Game#compute_level()
     * @see Game#fall()
     * @see Game#score()
     */
    private int score;

    /**
     * Lignes détruites.
     *
     * @see Game#lines_destroyed()
     * @see Game#fall()
     */
    private int lines;

    /**
     * Niveau de difficulté du jeu.
     *
     * @see Game#init()
     * @see Game#level()
     * @see Game#compute_level()
     */
    private int level;

    /**
     * Palier pour le prochain niveau.
     *
     * @see Game#init()
     * @see Game#compute_level()
     */
    private int threshold;


    /**
     * Ensemble temporaires de points.
     *
     * @see Game#init()
     * @see Game#fall()
     * @see Game#left()
     * @see Game#right()
     * @see Game#rotate()
     */
    private Point[] temp_points;

    /**
     * Générateur de pièces.
     *
     * @see Game#Constructor(PieceGenerator)
     * @see Game#init()
     * @see Game#new_piece()
     */
    private PieceGenerator generator;


    /**
     * Variable de vérification de l'occupation du jeu.
     *
     * @see Game#get_grid()
     * @see Game#get_piece()
     * @see Game#get_piece_id()
     * @see Game#refresh()
     */
    private boolean is_busy;

    /**
     * Grille utilisée pour l'appel distant.
     *
     * @see Game#get_grid()
     * @see Game#refresh()
     */
    private int[][] grid_IA;

    /**
     * Pièces utilisées pour l'appel distant.
     *
     * @see Game#get_piece()
     * @see Game#refresh()
     */
    private int[][] piece_IA;

    /**
     * Code de la pièce pour l'appel distant.
     *
     * @see Game#get_piece_id()
     * @see Game#refresh()
     */
    private int piece_id_IA;


    /**
     * Constructeur d'un jeu Tetrist.
     *
     * @throws RemoteException
     */
    public Game() throws RemoteException
    {
        this(Piece.RANDOM);
    }

    /**
     * Constructeur d'un jeu Tetrist à partir d'un générateur de pièces.
     *
     * @param pg
     *          Générateur de pièces.
     * @throws RemoteException
     */
    public Game(PieceGenerator pg) throws RemoteException
    {
        generator = pg;
        grid = new Grid();
        middle = grid.width() / 2;
        top = grid.height() - 1;

        init();
    }

    /**
     * Initialisation de toutes les variables utilisées dans un jeu de Tetrist.</br>
     * (Ex: niveau = 1, score = 0 etc ...)
     */
    private void init()
    {
        temp_points = new Point[4];
        for (int i = 0; i < temp_points.length; i++)
            temp_points[i] = new Point();
        grid_IA = new int[grid.width()][grid.height()];
        piece_IA = new int[4][2];
        score = 0;
        level = 1;
        threshold = score_factor;
        next = generator.new_piece(middle, top);
        new_piece();
    }

    // Appel distant pour l'IA
    /**
     * Retourne la grille pour l'IA.</br>
     * <i>(si le jeu n'est pas occupé.)</i>
     *
     * @throws RemoteException
     * @return null <b>si occupé</b>, la grille de l'appel distant <b>sinon</b>.
     */
    public int[][] get_grid() throws RemoteException
    {
        if (is_busy)
            return null;

        return grid_IA;
    }

    /**
     * Retourne la pièce pour l'IA.</br>
     * <i>(si le jeu n'est pas occupé.)</i>
     *
     * @throws RemoteException
     * @return null <b>si occupé</b>, la pièce de l'appel distant <b>sinon</b>.
     */
    public int[][] get_piece() throws RemoteException
    {
        if (is_busy)
            return null;

        return piece_IA;
    }

    /**
     * Retourne le code de la pièce pour l'IA.</br>
     * <i>(si le jeu n'est pas occupé.)</i>
     *
     * @throws RemoteException
     * @return -1 <b>si occupé</b>, le code de la pièce de l'appel distant <b>sinon</b>.
     */
    public int get_piece_id() throws RemoteException
    {
        if (is_busy)
            return -1;

        return piece_id_IA;
    }
    // Fin de l'appel distant.

    /**
     * Rafraîchit les éléments de l'IA.
     */
    public synchronized void refresh()
    {
        is_busy = true;

        for (int i = 0; i < grid.width(); i++)
            for (int j = 0; j < grid.height(); j++)
                grid_IA[i][j] = grid.get(i, j);

        Point[] points = current.coordinates();
        for (int i = 0; i < 4; i++)
        {
            piece_IA[i][0] = points[i].abcissa();
            piece_IA[i][1] = points[i].ordinate();
        }
        piece_id_IA = current.id();

        is_busy = false;
    }

    /**
     * Retourne la grille courante.
     *
     * @return grille courante.
     */
    public Grid grid()
    {
        return grid;
    }

    /**
     * Retourne la pièce courante.
     *
     * @return pièce courante.
     */
    public Piece current_piece()
    {
        return current;
    }

    /**
     * Retourne la pièce suivante.</br>
     * <b>NON VU PAR L'IA !</b>
     *
     * @return pièce suivante.
     */
    public Piece next_piece()
    {
        return next;
    }

    /**
     * Charge une nouvelle pièce.
     */
    private synchronized void new_piece()
    {
        current = next;
        next = generator.new_piece(middle, top);
    }

    /**
     * Retourne le niveau actuel.
     *
     * @return niveau actuel.
     */
    public int level()
    {
        return level;
    }

    /**
     * Retourne les lignes détruites.
     *
     * @return lignes détruites.
     */
    public int lines_destroyed()
    {
        return lines;
    }

    /**
     * Utilisée pour le passage d'un niveau et la réajustation du palier de niveau.
     */
    private void compute_level()
    {
        if (score >= threshold)
        {
            level++;
            threshold = score_factor * level * level;
        }
    }

    /**
     * Fait tomber la pièce courante.
     */
    public synchronized void fall()
    {
        current.needed_space_fall(temp_points);
        if (grid.in_bonds(temp_points) && grid.is_free(temp_points))
            current.fall();
        else
        {
            grid.put(current.coordinates(), current.id());
            int destroyed = grid.check(current.coordinates());
            lines += destroyed;
            score += lines_to_score(destroyed);
            compute_level();
            new_piece();
        }
    }

    /**
     * Fait aller la pièce courante à gauche.
     */
    public synchronized void left()
    {
        current.needed_space_left(temp_points);
        if (grid.in_bonds(temp_points) && grid.is_free(temp_points))
            current.left();
    }

    /**
     * Fait aller la pièce courante à droite.
     */
    public synchronized void right()
    {
        current.needed_space_right(temp_points);
        if (grid.in_bonds(temp_points) && grid.is_free(temp_points))
            current.right();
    }

    /**
     * Fait tourner la pièce courante.
     */
    public synchronized void rotate()
    {
        current.needed_space_rotation(temp_points);
        if (grid.in_bonds(temp_points) && grid.is_free(temp_points))
            current.rotate();
    }

    /**
     * Vérifie si le jeu est terminé ou non.
     *
     * @return <b>true</b> si le jeu est fini, <b>false</b> sinon.
     */
    public boolean game_is_over()
    {
        return grid.full();
    }

    /**
     * Retourne le nombre de points reçus en détruisant une/des ligne(s).
     *
     * @param destroyed
     *          Nombre de lignes détruites.
     * @return les points engendrés par cette destruction.
     */
    private int lines_to_score(int destroyed)
    {
        int index = (destroyed < 0 || destroyed >= score_scale.length) ? 0 : destroyed;
        return score_scale[index];
    }

    /**
     * Retourne le score courant.
     *
     * @return score courant.
     */
    public int score()
    {
        return score;
    }
}
