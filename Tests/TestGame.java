import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import Component.Game;
import Component.Piece;
import Component.PieceGenerator;

@RunWith(JUnit4.class)
public class TestGame
{
    @Test
    public void test_level()
    {
    }

    @Test
    public void test_score()
    {
    }

    @Test
    public void test_lines_destroyed()
    {
    }

    @Test
    public void test_fall()
    {
    }

    @Test
    public void test_right()
    {
    }

    @Test
    public void test_rotate()
    {
    }

    @Test
    public void test_game_is_over()
    {
    }
}

class FakePieceRandom implements PieceGenerator
{
    private static final int[] DEFAULT_ID = { 0, 1, 2, 3, 4, 5, 6 };
    private final int[] id;
    private int cursor;

    public FakePieceRandom()
    {
        this(DEFAULT_ID);
    }

    public FakePieceRandom(int[] fake_random_values)
    {
        boolean correct = true;
        for (int i : fake_random_values)
            if (i < 0 || i >= Piece.CARDINAL)
                correct = false;

        cursor = 0;
        id = correct ? fake_random_values : DEFAULT_ID;
    }

    public Piece new_piece(int x, int y)
    {
        Piece piece = new Piece(id[cursor], x, y);
        cursor = (cursor + 1) % id.length;
        return piece;
    }
}
