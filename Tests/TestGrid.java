import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import Component.Grid;

@RunWith(JUnit4.class)
public class TestGrid
{
    @Test
    public void test_size()
    {
        Grid grid_1 = new Grid();
        assertEquals(Grid.default_width, grid_1.width());
        assertEquals(Grid.default_height, grid_1.height());

        int w = 30;
        int h = 5;
        Grid grid_2 = new Grid(w, h);
        assertEquals(w, grid_2.width());
        assertEquals(h, grid_2.height());
    }

    @Test
    public void test_get()
    {
        Grid grid = new Grid();
        for (int i = 0; i < grid.width(); i++)
            for (int j = 0; j < grid.height(); j++)
                assertEquals(Grid.empty_block, grid.get(i, j));
    }

    @Test
    public void test_is_free()
    {
        Grid grid = new Grid();
        for (int i = 0; i < grid.width(); i++)
            for (int j = 0; j < grid.height(); j++)
                assertTrue(grid.is_free(i, j));

        for (int i = 0; i < grid.width(); i = i + 2)
            for (int j = 0; j < grid.height(); j = j + 3)
                grid.put(i, j, i*j);

    }

    @Test
    public void test_put()
    {
        Grid grid = new Grid();

        for (int i = 0; i < grid.width(); i++)
            for (int j = 0; j < grid.height(); j++)
            {
                int value = i * j;
                grid.put(i, j, value);
                assertEquals(value, grid.get(i, j));
            }
    }
}
