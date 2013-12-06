package Component;

import java.util.Random;

public class Piece
{
	/**
	 * Nombre de pièces <b>différentes</b>.
	 * 
	 * @see Piece#full_set_factory(int,int)
	 * @see PieceRandom#new_piece(int,int)
	 */
    public static final int CARDINAL = 7;
    
    /**
     * Générateur de pièces.
     * 
     * @see Game#Constructor()
     */
    public static final PieceGenerator RANDOM = new PieceRandom();
    
    /**
     * Les différents noms attribués aux pièces.</br>
     * <i>(la réprésentation d'une pièce se fait grâce à une lettre en comparant la ressemblance)</i>
     * 
     * @see Piece#name_to_id(char)
     */
    public static final char[] NAMES = { 'I', 'J', 'T', 'L', 'Z', 'O', 'S' };
    
    /**
     * Chargement de tous les paramètres en fonction de la pièce.
     * 
     * @see Piece#Constructor(int,int,int)
     */
    public static final Point[][][] FACES =
    {
        load_face(0), load_face(1), load_face(2), load_face(3),
        load_face(4), load_face(5), load_face(6)
    };

	
	/**
	 * Comparateur d'infériorité d'entiers.
	 * 
	 * @see Piece#maximum_abcissa()
	 * @see Piece#maximum_ordinate()
	 */
    private static final IntComp LOWER = new Lower();
    
    /**
	 * Comparateur de supériorité d'entiers.
	 * 
	 * @see Piece#minimum_abcissa()
	 * @see Piece#minimum_ordinate()
	 */
    private static final IntComp GREATER = new Greater();

	
	/**
	 * Valeur pour descendre une pièce.
	 * 
	 * @see Piece#needed_space_fall(Point[])
	 * @see Piece#needed_space(Point[],int)
	 */
    protected static final int MOVE_DOWN = 0;
    
    /**
	 * Valeur pour faire aller à gauche une pièce.
	 * 
	 * @see Piece#needed_space_left(Point[])
	 * @see Piece#needed_space(Point[],int)
	 */
    protected static final int MOVE_LEFT = 1;
    
    /**
	 * Valeur pour faire aller à droite une pièce.
	 * 
	 * @see Piece#needed_space_right(Point[])
	 */
    protected static final int MOVE_RIGHT = 2;

	/**
	 * Numéro de référence de la pièce.
	 * 
	 * @see Piece#Constructor(int,int,int)
	 * @see Piece#id()
	 * @see Piece#rotate()
	 */
    protected final int id;
    
    /**
     * Objet représentant la pièce.
     * 
     * @see Piece#Constructor(int,int,int)
     * @see Piece#init()
     * @see Piece#face()
     * @see Piece#rotate()
     */
    protected final Point[][] face;


	/**
	 * Abscisse à l'apparition.
	 * 
	 * @see Piece#Constructor(int,int,int)
     * @see Piece#init()
     * @see Piece#spawn_abcissa()
	 */
    private final int spawn_abcissa;
    
    /**
	 * Ordonnée à l'apparition.
	 * 
	 * @see Piece#Constructor(int,int,int)
     * @see Piece#init()
     * @see Piece#spawn_ordinate()
	 */
    private final int spawn_ordinate;
    
    /**
	 * Rotation à l'apparition.
	 * 
	 * @see Piece#Constructor(int,int,int)
     * @see Piece#init()
     * @see Piece#spawn_rotation()
	 */
    private final int spawn_rotation;


	/**
	 * Rotation de la pièce.
	 * 
	 * @see Piece#init()
	 * @see Piece#rotation()
	 * @see Piece#rotate()
	 */
    private int rotation;
    
    /**
     * Coordonnées de la pièce.
     * 
     * @see Piece#Constructor(int,int,int)
     * @see Piece#init()
     */
    private Point[] coordinates;


	/**
	 * Constructeur d'une pièce à partir de son nom.
	 * 
	 * @param c
	 * 			Nom de la pièce.
	 * @param x
	 * 			Abscisse d'apparition.
	 * @param y
	 * 			Ordonnée d'apparition.
	 */
    public Piece(char c, int x, int y)
    {
        this(name_to_id(c), x, y);
    }

	/**
	 * Constructeur d'une pièce à partir de son id.
	 * 
	 * @param n
	 * 			id de la pièce.
	 * @param x
	 * 			Abscisse d'apparition.
	 * @param y
	 * 			Ordonnée d'apparition.
	 */
    public Piece(int n, int x, int y)
    {
        id = n;
        spawn_rotation = 3;
        spawn_abcissa = x;
        spawn_ordinate = y;
        face = FACES[id];
        coordinates = new Point[4];
        for (int i = 0; i < coordinates.length; i++)
            coordinates[i] = new Point();

        init();
    }

	/**
	 * Constructeur d'une pièce avec une autre pièce.
	 * 
	 * @param p
	 * 			Pièce.
	 */
    public Piece(Piece p)
    {
        this(p.id(), p.spawn_abcissa(), p.spawn_ordinate());
        set_coordinates(p.coordinates());
    }

	/**
	 * Initialisation de la pièce.
	 */
    private void init()
    {
        rotation = spawn_rotation;

        int center_x = face[rotation][0].abcissa();
        int center_y = face[rotation][0].ordinate();

        for (int i = 0; i < coordinates.length; i++)
        {
            Point current = coordinates[i];
            int i_x = face[rotation][i].abcissa();
            int i_y = face[rotation][i].ordinate();
            current.set_abcissa(spawn_abcissa + i_x - center_x);
            current.set_ordinate(spawn_ordinate + i_y - center_y);
        }
    }

	/**
	 * Retourne le code d'une pièce à l'aide de son nom.
	 * 
	 * @param c
	 * 			Nom de la pièce.
	 * @return Code de la pièce.
	 */
    public static int name_to_id(char c)
    {
        int id = -1;

        for (int i = 0; id == -1 && i < NAMES.length; i++)
            if (NAMES[i] == c)
                id = i;

        return (id == -1) ? 0 : id;
    }

	/**
	 * Value les coordonnées de la pièce.
	 * 
	 * @param points
	 * 			Points composant la pièce.
	 */
    public synchronized void set_coordinates(Point[] points)
    {
        for (int i = 0; i < points.length; i++)
            coordinates[i].set(points[i]);
    }

	/**
	 * Retourne l'abscisse d'apparition.
	 * 
	 * @return spawn_abcissa.
	 */
    public int spawn_abcissa()
    {
        return spawn_abcissa;
    }

	/**
	 * Retourne l'ordonnée d'apparition.
	 * 
	 * @return spawn_ordinate.
	 */
    public int spawn_ordinate()
    {
        return spawn_ordinate;
    }

	/**
	 * Retourne la rotation de la pièce.
	 * 
	 * @return rotation.
	 */
    public int rotation()
    {
        return rotation;
    }

	/**
	 * Retourne la pièce.
	 * 
	 * @return face.
	 */
    public Point[][] face()
    {
        return face;
    }

	/**
	 * Retourne les coordonnée de la pièce.
	 * 
	 * @return coordinates.
	 */
    public Point[] coordinates()
    {
        return coordinates;
    }

	/**
	 * Retourne le code de la pièce.
	 * 
	 * @return id.
	 */
    public int id()
    {
        return id;
    }

	/**
	 * Applique la rotation d'une pièce.</br>
	 * <i>(cette méthode utilise le code des différentes pièces)</i>
	 */
    public synchronized void rotate()
    {
        if (id != 5)
        {
            rotation += 3;
            if (id == 0 || id == 4 || id == 6)
                rotation = 2 + rotation % 2;
            else
                rotation = rotation % 4;

            for (int i = 1; i < 4; i++)
            {
                int center_x = face[rotation][0].abcissa();
                int center_y = face[rotation][0].ordinate();
                int i_x = face[rotation][i].abcissa();
                int i_y = face[rotation][i].ordinate();
                Point current = coordinates[i];

                current.set(coordinates[0]);
                current.shift_abcissa(i_x - center_x);
                current.shift_ordinate(i_y - center_y);
            }
        }
    }

	/**
	 * Fait tomber une pièce d'une case.
	 */
    public synchronized void fall()
    {
        fall(1);
    }

	/**
	 * Fait aller une pièce d'une case vers la gauche.
	 */
    public synchronized void left()
    {
        left(1);
    }

	/**
	 * Fait aller une pièce d'une case vers la droite.
	 */
    public synchronized void right()
    {
        right(1);
    }

	/**
	 * Remonte une pièce d'une case.
	 */
    public synchronized void fly()
    {
        fly(1);
    }

	/**
	 * Fait aller une pièce de plusieurs cases vers la gauche.
	 * 
	 * @param columns
	 * 			Nombre de cases de décalage.
	 */
    public synchronized void left(int columns)
    {
        shift(-columns, 0);
    }

	/**
	 * Fait aller une pièce de plusieurs cases vers la droite.
	 * 
	 * @param columns
	 * 			Nombre de cases de décalage.
	 */
    public synchronized void right(int columns)
    {
        shift(columns, 0);
    }

	/**
	 * Fait tomber une pièce de plusieurs cases.
	 * 
	 * @param lines
	 * 			Nombre de cases de décalage.
	 */
    public synchronized void fall(int lines)
    {
        shift(0, -lines);
    }

	/**
	 * Remonte de plusieurs cases.
	 * 
	 * @param lines
	 * 			Nombre de cases de décalage.
	 */
    public synchronized void fly(int lines)
    {
        shift(0, lines);
    }

	/**
	 * Décalage d'une pièce.
	 * 
	 * @param horizontal_shift
	 * 			Décalage horizontal.
	 * @param vertical_shift
	 * 			Décalage vertical.
	 */
    private synchronized void shift(int horizontal_shift, int vertical_shift)
    {
        for (Point point : coordinates)
            point.shift(horizontal_shift, vertical_shift);
    }

	/**
	 * Espace nécessaire pour tomber.
	 * 
	 * @param space
	 * 			Espace.
	 */
    public void needed_space_fall(Point[] space)
    {
        needed_space(space, MOVE_DOWN);
    }

	/**
	 * Espace nécessaire pour aller à gauche.
	 * 
	 * @param space
	 * 			Espace.
	 */
    public void needed_space_left(Point[] space)
    {
        needed_space(space, MOVE_LEFT);
    }
    
	/**
	 * Espace nécessaire pour aller à droite.
	 * 
	 * @param space
	 * 			Espace.
	 */
    public void needed_space_right(Point[] space)
    {
        needed_space(space, MOVE_RIGHT);
    }

	/**
	 * Espace nécessaire pour un déplacement quelconque.
	 * 
	 * @param space
	 * 			Espace.
	 * @param move
	 * 			Déplacement effectué.
	 */
    private void needed_space(Point[] space, int move)
    {
        int h_shift = (move == MOVE_DOWN) ? 0 : ((move == MOVE_LEFT) ? -1 : 1);
        int v_shift = (move == MOVE_DOWN) ? -1 : 0;

        for (int i = 0; i < coordinates.length; i++)
        {
            space[i].set(coordinates[i]);
            space[i].shift(h_shift, v_shift);
        }
    }

	/**
	 * Espace nécessaire pour une rotation.
	 * 
	 * @param space
	 * 			Espace.
	 */
    public void needed_space_rotation(Point[] space)
    {
        rotate();

        for (int i = 0; i < coordinates.length; i++)
            space[i].set(coordinates[i]);

        for (int i = 0; i < 3; i++)
            rotate();
    }

	/**
	 * Charge les paramètres d'un pièce en fonction de son code.
	 * 
	 * @param p
	 * 			Code de pièce.
	 * @return un bloc.
	 */
    private static Point[][] load_face(int p)
    {
        Point[][] blocks = new Point[4][4];
        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks[i].length; j++)
                blocks[i][j] = new Point();

        /* Centre de rotation. */
        blocks[0][0].set(0, 1);

        blocks[0][1].set_abcissa(p != 0 ? 1 : 0);
        blocks[0][1].set_ordinate(p >= 4 ? 1 : (3 - p));

        blocks[0][2].set_abcissa(p == 4 ? 1 : 0);
        blocks[0][2].set_ordinate(((p % 2) == 0) ? 2 : 0);

        blocks[0][3].set_abcissa(p > 4 ? 1 : 0);
        blocks[0][3].set_ordinate((p == 1 || p == 3) ? 2 : 0);

        /* Rotations. */
        for (int i = 1; i < blocks.length; i++)
            for (int j = 0; j < blocks[i].length; j++)
            {
                int h = i - 1;
                int x = -blocks[h][j].ordinate();
                int y = blocks[h][j].abcissa();
                blocks[i][j].set(x, y);
            }

        return blocks;
    }

	/**
	 * Comparateur de minimum <b>ou</b> de maximum avec un point.
	 * 
	 * @param comparator
	 * 			Applique le minimum <b>ou</b> le maximum.
	 * @param getter
	 * 			Point à comparer.
	 * @return Différence entre les deux points.
	 */
    private int min_max(IntComp comparator, PointValueGetter getter)
    {
        int value = getter.get_value(coordinates[0]);

        for (Point point : coordinates)
        {
            int candidate = getter.get_value(point);
            if (comparator.compare(value, candidate))
                    value = candidate;
        }

        return value;
    }

    public int minimum_abcissa()
    {
        return min_max(GREATER, Point.ABCISSA_GETTER);
    }

    public int maximum_abcissa()
    {
        return min_max(LOWER, Point.ABCISSA_GETTER);
    }

    public int minimum_ordinate()
    {
        return min_max(GREATER, Point.ORDINATE_GETTER);
    }

    public int maximum_ordinate()
    {
        return min_max(LOWER, Point.ORDINATE_GETTER);
    }

	/**
	 * Crée toutes les pièces différentes.
	 * 
	 * @param x
	 * 			Abscisse.
	 * @param y
	 * 			Ordonnée.
	 * @return les 7 pièces du jeu Tetrist.
	 */
    public static Piece[] full_set_factory(int x, int y)
    {
        Piece[] set = new Piece[CARDINAL];
        for (int i = 0; i < CARDINAL; i++)
            set[i] = new Piece(i, x, y);

        return set;
    }
}

/**
 * Classe de générateur de pièces <b>aléatoires</b>.
 */
class PieceRandom implements PieceGenerator
{
	/**
	 * Générateur de nombres aléatoires.
	 * 
	 * @see PieceRandom#Constructor()
	 */
    private final Random generator;

	/**
	 * Constructeur de pièces aléatoires.
	 */
    public PieceRandom()
    {
        generator = new Random();
    }

	/**
	 * Génère une nouvelle pièce aléatoire.
	 * 
	 * @param x
	 * 			Abscisse.
	 * @param y
	 * 			Ordonnée.
	 * @return une pièce aléatoire.
	 */
    public Piece new_piece(int x, int y)
    {
        int id = generator.nextInt(Piece.CARDINAL);
        Piece piece = new Piece(id, x, y);

        return piece;
    }
}

/**
 * Classe abstraite pour la comparaison d'entiers.
 */
abstract class IntComp
{
    abstract boolean compare(int a, int b);
}

/**
 * Classe de comparaison d'un entier inférieur à un autre.
 */
class Lower extends IntComp
{
	/**
	 * Compare deux entiers.
	 * 
	 * @param a
	 * 			Entier a.
	 * @param b
	 * 			Entier b.
	 * @return <b>true</b> si a<b, <b>false</b> sinon.
	 */
    boolean compare(int a, int b)
    {
        return a < b;
    }
}

/**
 * Classe de comparaison d'un entier supérieur à un autre.
 */
class Greater extends IntComp
{
	/**
	 * Compare deux entiers.
	 * 
	 * @param a
	 * 			Entier a.
	 * @param b
	 * 			Entier b.
	 * @return <b>true</b> si a>=b, <b>false</b> sinon.
	 */
    boolean compare(int a, int b)
    {
        return a >= b;
    }
}
