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
import Component.Point;
import IA.GridIA;
import IA.GridState;

@RunWith(JUnit4.class)
public class TestGridState
{
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

        assertFalse(state_1.equals(state_2));
        assertTrue(state_3.equals(state_4));
    }
}
