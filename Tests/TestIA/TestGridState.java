package TestIA;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertArrayEquals;

import java.util.Vector;
import Component.Grid;
import Component.Piece;
import Component.Point;
import IA.GridIA;
import IA.GridState;

@RunWith(JUnit4.class)
public class TestGridState
{
    private static int MIDDLE = Grid.DEFAULT_WIDTH / 2;
    private static int TOP = Grid.DEFAULT_HEIGHT - 1;

    @Test
    public void test_compareTo()
    {
        test_empty_i();
        test_misc();
    }

    private void test_misc()
    {
        GridIA grid_1 = new GridIA();
        GridIA grid_2 = new GridIA();

        grid_1.put(0, 0, 1);
        grid_1.put(1, 0, 1);
        grid_1.put(2, 0, 1);
        grid_1.put(1, 1, 1);

        grid_2.put(0, 1, 1);
        grid_2.put(1, 1, 1);
        grid_2.put(2, 1, 1);
        grid_2.put(1, 0, 1);

        GridState state_1 = new GridState(grid_1);
        GridState state_2 = new GridState(grid_2);

        assertTrue(state_1.compareTo(state_2) > 0);
    }

    private void test_empty_i()
    {
        GridIA grid_1 = new GridIA();
        GridIA grid_2 = new GridIA();

        Piece i_1 = new_i();
        Piece i_2 = new_i();
        i_1.fall(TOP);
        i_2.rotate();
        i_2.fall(TOP-2);
        grid_1.put(i_1.coordinates(), i_1.id());
        grid_2.put(i_2.coordinates(), i_2.id());

        GridState state_1 = new GridState(grid_1);
        GridState state_2 = new GridState(grid_2);

        assertTrue(state_1.compareTo(state_2) > 0);
    }

    @Test
    public void test_equals()
    {
        GridIA grid_1 = new GridIA();
        GridIA grid_2 = new GridIA(Grid.DEFAULT_WIDTH+10, Grid.DEFAULT_HEIGHT+5);
        GridIA grid_3 = new GridIA();
        GridIA grid_4 = new GridIA();

        for (int i = 0; i < 4; i++)
        {
            grid_3.put(i, 0, 0);
            grid_4.put(i, 0, 0);
            grid_3.put(Grid.DEFAULT_WIDTH-1, i, 0);
            grid_4.put(Grid.DEFAULT_WIDTH-2, i, 0);
            grid_3.put(Grid.DEFAULT_WIDTH-2, i, 0);
            grid_4.put(Grid.DEFAULT_WIDTH-3, i, 0);
        }

        GridState state_1 = new GridState(grid_1);
        GridState state_2 = new GridState(grid_2);
        GridState state_3 = new GridState(grid_3);
        GridState state_4 = new GridState(grid_4);

        assertFalse("Failure: state_1 == state_2", state_1.equals(state_2));
        assertTrue("Failure: state_3 != state_4", state_3.equals(state_4));
    }

    private Piece new_i() { return new_piece('I'); }
    private Piece new_j() { return new_piece('J'); }
    private Piece new_t() { return new_piece('T'); }
    private Piece new_l() { return new_piece('L'); }
    private Piece new_z() { return new_piece('Z'); }
    private Piece new_o() { return new_piece('O'); }
    private Piece new_s() { return new_piece('S'); }

    private Piece new_piece(char name)
    {
        return new Piece(name, MIDDLE, TOP);
    }
}
