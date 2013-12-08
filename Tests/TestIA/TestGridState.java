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
        test_compareTo_empty_grid();
    }


    private void test_compareTo_empty_grid()
    {
        test_empty('I', new int[] { 0, 1 });
        test_empty('J', new int[] { 2, 1, 0, 3 });
        test_empty('T', new int[] { 2, 1, 3, 0 });
        test_empty('L', new int[] { 2, 3, 0, 1 });
        test_empty('Z', new int[] { 0, 1 });
        /* Pas de test pour 'O' */
        test_empty('S', new int[] { 0, 1 });
    }

    private void test_empty(char name, int[] order)
    {
        int n = Piece.max_rotations(name) + 1;
        GridIA[] grids = create_grids(n);
        Piece[] pieces = create_pieces(n, name);

        correct_fall(grids, pieces);
        assert_order(grids, order, "Empty, " + name);
    }

    private GridIA[] create_grids(int n)
    {
        GridIA[] grids = new GridIA[n];
        for (int i = 0; i < n; i++)
            grids[i] = new GridIA();

        return grids;
    }

    private void correct_fall(GridIA[] grids, Piece[] pieces)
    {
        for (int i = 0; i < grids.length; i++)
            correct_fall(grids[i], pieces[i]);
    }

    private Piece[] create_pieces(int n, char name)
    {
        Piece[] pieces = new Piece[n];
        for (int i = 0; i < n; i++)
        {
            pieces[i] = new_piece(name);
            for (int j = 0; j < i; j++)
                pieces[i].rotate();
        }

        return pieces;
    }

    private void assert_order(GridIA[] grids, int[] order, String message)
    {
        for (int i = 0; i < grids.length - 1; i++)
            assert_better_than(grids[order[i]], grids[order[i+1]], message + ", " + i);
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

    private void assert_better_than(GridIA g, GridIA h, String message)
    {
        String failure_string = "Failure: " + message;
        GridState state_1 = new GridState(g);
        GridState state_2 = new GridState(h);

        assertTrue(failure_string, state_1.compareTo(state_2) >= 0);
    }

    private void assert_equals(GridIA g, GridIA h, String message)
    {
        String failure_string = "Failure: " + message;
        GridState state_1 = new GridState(g);
        GridState state_2 = new GridState(h);

        assertTrue(failure_string, state_1.equals(state_2));
        assertTrue(failure_string, state_1.compareTo(state_2) == 0);
    }

    private void double_rotate(Piece piece)
    {
        rotate_n(piece, 2);
    }

    private void triple_rotate(Piece piece)
    {
        rotate_n(piece, 3);
    }

    private void rotate_n(Piece piece, int n)
    {
        for (int i = 0; i < n; i++)
            piece.rotate();
    }

    private void correct_fall(GridIA grid, Piece piece)
    {
        Point[] needed_space = new Point[4];
        for (int i = 0; i < 4; i++)
            needed_space[i] = new Point();

        piece.needed_space_fall(needed_space);
        while (grid.in_bonds(needed_space) && grid.is_free(needed_space))
        {
            piece.fall();
            piece.needed_space_fall(needed_space);
        }

        grid.put(piece);
    }
}
