package Component;

import java.util.Random;

public class Piece
{
    public static final int CARDINAL = 7;
    public static final PieceGenerator RANDOM = new PieceRandom();
    public static final char[] NAMES = { 'I', 'J', 'T', 'L', 'Z', 'O', 'S' };
    public static final Point[][][] FACES =
    {
        load_face(0), load_face(1), load_face(2), load_face(3),
        load_face(4), load_face(5), load_face(6)
    };

    private static final IntComp LOWER = new Lower();
    private static final IntComp GREATER = new Greater();

    protected static final int MOVE_DOWN = 0;
    protected static final int MOVE_LEFT = 1;
    protected static final int MOVE_RIGHT = 2;

    protected final int id;
    protected final Point[][] face;

    private final int spawn_abcissa;
    private final int spawn_ordinate;
    private final int spawn_rotation;

    private int rotation;
    private Point[] coordinates;

    public Piece(char c, int x, int y)
    {
        this(name_to_id(c), x, y);
    }

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

    public Piece(Piece p)
    {
        this(p.id(), p.spawn_abcissa(), p.spawn_ordinate());
        set_coordinates(p.coordinates());
    }

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

    public static int name_to_id(char c)
    {
        int id = -1;

        for (int i = 0; id == -1 && i < NAMES.length; i++)
            if (NAMES[i] == c)
                id = i;

        return (id == -1) ? 0 : id;
    }

    public synchronized void set_coordinates(Point[] points)
    {
        for (int i = 0; i < points.length; i++)
            coordinates[i].set(points[i]);
    }

    public int spawn_abcissa()
    {
        return spawn_abcissa;
    }

    public int spawn_ordinate()
    {
        return spawn_ordinate;
    }

    public int rotation()
    {
        return rotation;
    }

    public Point[][] face()
    {
        return face;
    }

    public Point[] coordinates()
    {
        return coordinates;
    }

    public int id()
    {
        return id;
    }

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

    public synchronized void fall()
    {
        fall(1);
    }

    public synchronized void left()
    {
        left(1);
    }

    public synchronized void right()
    {
        right(1);
    }

    public synchronized void fly()
    {
        fly(1);
    }

    public synchronized void left(int columns)
    {
        shift(-columns, 0);
    }

    public synchronized void right(int columns)
    {
        shift(columns, 0);
    }

    public synchronized void fall(int lines)
    {
        shift(0, -lines);
    }

    public synchronized void fly(int lines)
    {
        shift(0, lines);
    }

    private synchronized void shift(int horizontal_shift, int vertical_shift)
    {
        for (Point point : coordinates)
            point.shift(horizontal_shift, vertical_shift);
    }

    public void needed_space_fall(Point[] space)
    {
        needed_space(space, MOVE_DOWN);
    }

    public void needed_space_left(Point[] space)
    {
        needed_space(space, MOVE_LEFT);
    }

    public void needed_space_right(Point[] space)
    {
        needed_space(space, MOVE_RIGHT);
    }

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

    public void needed_space_rotation(Point[] space)
    {
        rotate();

        for (int i = 0; i < coordinates.length; i++)
            space[i].set(coordinates[i]);

        for (int i = 0; i < 3; i++)
            rotate();
    }

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

    public static Piece[] full_set_factory(int x, int y)
    {
        Piece[] set = new Piece[CARDINAL];
        for (int i = 0; i < CARDINAL; i++)
            set[i] = new Piece(i, x, y);

        return set;
    }
}

class PieceRandom implements PieceGenerator
{
    private final Random generator;

    public PieceRandom()
    {
        generator = new Random();
    }

    public Piece new_piece(int x, int y)
    {
        int id = generator.nextInt(Piece.CARDINAL);
        Piece piece = new Piece(id, x, y);

        return piece;
    }
}

abstract class IntComp
{
    abstract boolean compare(int a, int b);
}

class Lower extends IntComp
{
    boolean compare(int a, int b)
    {
        return a < b;
    }
}

class Greater extends IntComp
{
    boolean compare(int a, int b)
    {
        return a >= b;
    }
}
