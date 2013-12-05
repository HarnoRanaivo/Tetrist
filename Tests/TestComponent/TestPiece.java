package TestComponent;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;

import Component.Point;
import Component.Piece;

@RunWith(JUnit4.class)
public class TestPiece
{
    private static final int NEG_VALUE = -10;
    private static final int POS_VALUE = 10;
    private static final int THRESHOLD = 50;
    private static final int RANDOM_THRESHOLD = 100;
    private static final int STEP = 3;

    private static final Move LEFT = new Left();
    private static final Move RIGHT = new Left();
    private static final Move FALL = new Left();
    private static final Move FLY = new Left();

    @Test
    public void test_spawn_abcissa()
    {
        for (int i = 0; i < Piece.CARDINAL; i++)
        {
            Piece[] sample = get_sample(i);

            assertEquals(0, sample[0].spawn_abcissa());
            assertEquals(0, sample[1].spawn_abcissa());
            assertEquals(0, sample[2].spawn_abcissa());
            assertEquals(POS_VALUE, sample[3].spawn_abcissa());
            assertEquals(POS_VALUE, sample[4].spawn_abcissa());
            assertEquals(POS_VALUE, sample[5].spawn_abcissa());
            assertEquals(NEG_VALUE, sample[6].spawn_abcissa());
            assertEquals(NEG_VALUE, sample[7].spawn_abcissa());
            assertEquals(NEG_VALUE, sample[8].spawn_abcissa());
        }
    }

    @Test
    public void test_spawn_ordinate()
    {
        for (int i = 0; i < Piece.CARDINAL; i++)
        {
            Piece[] sample = get_sample(i);

            assertEquals(0, sample[0].spawn_ordinate());
            assertEquals(POS_VALUE, sample[1].spawn_ordinate());
            assertEquals(NEG_VALUE, sample[2].spawn_ordinate());
            assertEquals(0, sample[3].spawn_ordinate());
            assertEquals(POS_VALUE, sample[4].spawn_ordinate());
            assertEquals(NEG_VALUE, sample[5].spawn_ordinate());
            assertEquals(0, sample[6].spawn_ordinate());
            assertEquals(POS_VALUE, sample[7].spawn_ordinate());
            assertEquals(NEG_VALUE, sample[8].spawn_ordinate());
        }
    }

    @Test
    public void test_id()
    {
        for (int i = 0; i < Piece.CARDINAL; i++)
        {
            Piece[] sample = get_sample(i);
            for (Piece piece : sample)
                assertEquals(i, piece.id());
        }
    }

    @Test
    public void test_name_to_id()
    {
        for (char c : Piece.NAMES)
        {
            int c_id = Piece.name_to_id(c);
            assertTrue(c_id >= 0 && c_id < Piece.CARDINAL);
            for (char d : Piece.NAMES)
            {
                boolean comparison = c_id == Piece.name_to_id(d);
                if (c == d)
                    assertTrue(comparison);
                else
                    assertFalse(comparison);
            }
        }
    }

    @Test
    public void test_needed_space_left()
    {
        test_needed_space(LEFT);
    }

    @Test
    public void test_needed_space_right()
    {
        test_needed_space(RIGHT);
    }

    @Test
    public void test_needed_space_fall()
    {
        test_needed_space(FALL);
    }

    @Test
    public void test_left()
    {
        test_moves(LEFT);
    }

    @Test
    public void test_right()
    {
        test_moves(RIGHT);
    }

    @Test
    public void test_fall()
    {
        test_moves(FALL);
    }

    @Test
    public void test_fly()
    {
        test_moves(FLY);
    }

    @Test
    public void test_random()
    {
        for (int i = 0; i < RANDOM_THRESHOLD; i++)
        {
            Piece piece = Piece.RANDOM.new_piece(0, 0);
            assertTrue(piece.id() >= 0 && piece.id() < Piece.CARDINAL);
        }
    }

    @Test
    public void test_min_max_abcissa()
    {
        for (int i = 0; i < Piece.CARDINAL; i++)
        {
            Piece[] sample = get_sample(i);

            for (Piece piece : sample)
            {
                int id = piece.id();
                int rotation = piece.rotation();
                int spawn_abcissa = piece.spawn_abcissa();
                int spawn_ordinate = piece.spawn_ordinate();
                int min_abcissa = piece.minimum_abcissa();
                int min_ordinate = piece.minimum_ordinate();
                int max_abcissa = piece.maximum_abcissa();
                int max_ordinate = piece.maximum_ordinate();

                assertTrue(min_abcissa <= max_abcissa);
                assertTrue(min_ordinate <= max_ordinate);
                assertTrue(min_abcissa == spawn_abcissa + minimum_abcissa_shift(id, rotation));
                assertTrue(max_abcissa == spawn_abcissa + maximum_abcissa_shift(id, rotation));
                assertTrue(min_ordinate == spawn_ordinate + minimum_ordinate_shift(id, rotation));
                assertTrue(max_ordinate == spawn_ordinate + maximum_ordinate_shift(id, rotation));
            }
        }
    }

    private int minimum_abcissa_shift(int id, int rotation)
    {
        int center_abcissa = Piece.FACES[id][rotation][0].abcissa();
        int shift = 0;
        for (int i = 1; i < 4; i++)
        {
            int a = Piece.FACES[id][rotation][i].abcissa() - center_abcissa;
            shift = a < shift ? a : shift;
        }
        return shift;
    }

    private int maximum_abcissa_shift(int id, int rotation)
    {
        int center_abcissa = Piece.FACES[id][rotation][0].abcissa();
        int shift = 0;
        for (int i = 1; i < 4; i++)
        {
            int a = Piece.FACES[id][rotation][i].abcissa() - center_abcissa;
            shift = a > shift ? a : shift;
        }
        return shift;
    }

    private int minimum_ordinate_shift(int id, int rotation)
    {
        int center_ordinate = Piece.FACES[id][rotation][0].ordinate();
        int shift = 0;
        for (int i = 1; i < 4; i++)
        {
            int a = Piece.FACES[id][rotation][i].ordinate() - center_ordinate;
            shift = a < shift ? a : shift;
        }
        return shift;
    }

    private int maximum_ordinate_shift(int id, int rotation)
    {
        int center_ordinate = Piece.FACES[id][rotation][0].ordinate();
        int shift = 0;
        for (int i = 1; i < 4; i++)
        {
            int a = Piece.FACES[id][rotation][i].ordinate() - center_ordinate;
            shift = a > shift ? a : shift;
        }
        return shift;
    }


    private void test_needed_space(Move move)
    {
        Point[] buffer = new Point[4];
        for (int i = 0; i < 4; i++)
            buffer[i] = new Point();

        for (int i = 0; i < Piece.CARDINAL; i++)
        {
            Piece[] sample = get_sample(i);

            for (Piece piece : sample)
            {
                Point[] original = copy_point_array(piece.coordinates());

                move.needed_space(piece, buffer);
                test_shifts(original, buffer, move.SIMPLE_SHIFT_X, move.SIMPLE_SHIFT_Y);
            }
        }
    }

    private void test_moves(Move move)
    {
        for (int i = 0; i < Piece.CARDINAL; i++)
        {
            Piece[] sample_1 = get_sample(i);

            for (Piece piece : sample_1)
            {
                Point[] original = copy_point_array(piece.coordinates());

                move.move(piece);
                test_shifts(original, piece.coordinates(), move.SIMPLE_SHIFT_X, move.SIMPLE_SHIFT_Y);
            }

            for (int k = -THRESHOLD; k < THRESHOLD; k += 3)
            {
                Piece[] sample_k = get_sample(i);

                for (Piece piece : sample_k)
                {
                    int length = piece.coordinates().length;
                    Point[] original = new Point[length];
                    for (int j = 0; j < length; j++)
                        original[j] = new Point(piece.coordinates()[j]);

                    move.move(piece, k);
                    test_shifts(original, piece.coordinates(), move.shift_value_x(k), move.shift_value_y(k));
                }
            }
        }
    }

    private Piece[] get_sample(int i)
    {
        Piece[] sample =
        {
            new Piece(i, 0, 0),
            new Piece(i, 0, POS_VALUE),
            new Piece(i, 0, NEG_VALUE),
            new Piece(i, POS_VALUE, 0),
            new Piece(i, POS_VALUE, POS_VALUE),
            new Piece(i, POS_VALUE, NEG_VALUE),
            new Piece(i, NEG_VALUE, 0),
            new Piece(i, NEG_VALUE, POS_VALUE),
            new Piece(i, NEG_VALUE, NEG_VALUE),
        };

        return sample;
    }

    private void test_shifts(Point[] original, Point[] shifted, int horizontal_shift, int vertical_shift)
    {
        for (Point point : original)
            point.shift(horizontal_shift, vertical_shift);

        assertArrayEquals(original, shifted);
    }

    private Point[] copy_point_array(Point[] array)
    {
        Point[] new_array = Arrays.copyOf(array, array.length);
        for (int i = 0; i < new_array.length; i++)
            new_array[i] = new Point(new_array[i]);

        return new_array;
    }
}

abstract class Move
{
    public final int SIMPLE_SHIFT_X;
    public final int SIMPLE_SHIFT_Y;
    public Move(int x, int y) { SIMPLE_SHIFT_X = x; SIMPLE_SHIFT_Y = y; }
    public abstract void move(Piece piece);
    public abstract void move(Piece piece, int value);
    public abstract int shift_value_x(int value);
    public abstract int shift_value_y(int value);
    public abstract void needed_space(Piece piece, Point[] buffer);
}

class Left extends Move
{
    public Left() { super(-1, 0); }
    public void move(Piece piece) { piece.left(); }
    public void move(Piece piece, int value) { piece.left(value); }
    public int shift_value_x(int value) { return -value; }
    public int shift_value_y(int value) { return 0; }
    public void needed_space(Piece piece, Point[] buffer) { piece.needed_space_left(buffer); }
}

class Right extends Move
{
    public Right() { super(1, 0); }
    public void move(Piece piece) { piece.right(); }
    public void move(Piece piece, int value) { piece.right(value); }
    public int shift_value_x(int value) { return value; }
    public int shift_value_y(int value) { return 0; }
    public void needed_space(Piece piece, Point[] buffer) { piece.needed_space_right(buffer); }
}

class Fall extends Move
{
    public Fall() { super(0, -1); }
    public void move(Piece piece) { piece.fall(); }
    public void move(Piece piece, int value) { piece.fall(value); }
    public int shift_value_x(int value) { return 0; }
    public int shift_value_y(int value) { return -value; }
    public void needed_space(Piece piece, Point[] buffer) { piece.needed_space_fall(buffer); }
}

class Fly extends Move
{
    public Fly() { super(0, 1); }
    public void move(Piece piece) { piece.fly(); }
    public void move(Piece piece, int value) { piece.fly(value); }
    public int shift_value_x(int value) { return 0; }
    public int shift_value_y(int value) { return value; }
    public void needed_space(Piece piece, Point[] buffer) { }
}
