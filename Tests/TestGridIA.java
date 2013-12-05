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

@RunWith(JUnit4.class)
public class TestGridIA
{
    /* TestGrid */
    @Test
    public void test_size()
    {
        GridIA grid_1 = new GridIA();
        assertEquals("Failure - new GridIA(): ", GridIA.DEFAULT_WIDTH, grid_1.width());
        assertEquals("Failure - new GridIA(): ", GridIA.DEFAULT_HEIGHT, grid_1.height());

        int w = 30;
        int h = 5;
        GridIA grid_2 = new GridIA(w, h);
        assertEquals("Failure - new GridIA(int, int): ", w, grid_2.width());
        assertEquals("Failure - new GridIA(int, int): ", h, grid_2.height());
    }

    @Test
    public void test_equals()
    {
        GridIA grid_1 = new GridIA();
        GridIA grid_2 = new GridIA(30, 5);

        assertTrue("Failure - equals(Object): ", grid_1.equals(grid_1));
        assertTrue("Failure - equals(Object): ", grid_2.equals(grid_2));
        assertFalse("Failure - equals(Object): ", grid_1.equals(grid_2));

        GridIA grid_3 = new GridIA();
        GridIA grid_4 = new GridIA();

        for (int i = 0; i < grid_1.width(); i++)
            for (int j = 0; j < grid_1.height(); j++)
            {
                grid_1.put(i, j, i*j);
                grid_3.put(i, j, i*j);
                grid_4.put(i, j, i*j);
            }
        grid_4.put(grid_1.width()/2, grid_1.height()/2, GridIA.EMPTY_BLOCK);

        assertTrue("Failure - equals(Object): ", grid_1.equals(grid_1));
        assertTrue("Failure - equals(Object): ", grid_3.equals(grid_3));
        assertTrue("Failure - equals(Object): ", grid_4.equals(grid_4));
        assertTrue("Failure - equals(Object): ", grid_1.equals(grid_3));
        assertFalse("Failure - equals(Object): ", grid_4.equals(grid_1));
        assertFalse("Failure - equals(Object): ", grid_4.equals(grid_3));
    }

    @Test
    public void test_copy()
    {
        GridIA grid_1 = new GridIA();
        GridIA grid_2 = new GridIA(30, 5);

        grid_1.copy(grid_2);
        assertFalse("Failure - copy(GridIA): ", grid_1.equals(grid_2));

        for (int i = 0; i < grid_1.width(); i += 2)
            for (int j = 0; j < grid_1.height(); j += 3)
                grid_1.put(i, j, i*j);

        GridIA grid_3 = new GridIA();

        assertFalse("Failure - copy(GridIA): ", grid_1.equals(grid_3));
        grid_3.copy(grid_1);
        assertTrue("Failure - copy(GridIA): ", grid_1.equals(grid_3));
    }

    @Test
    public void test_get()
    {
        GridIA grid = new GridIA();
        for (int i = 0; i < grid.width(); i++)
            for (int j = 0; j < grid.height(); j++)
            {
                Point point = new Point(i, j);
                assertEquals("Failure - get(int, int): ", GridIA.EMPTY_BLOCK, grid.get(i, j));
                assertEquals("Failure - get(Point): ", GridIA.EMPTY_BLOCK, grid.get(point));

                int value = i * j;
                grid.put(i, j, value);
                assertEquals("Failure - get(int, int): ", value, grid.get(i, j));
                assertEquals("Failure - get(Point): ", value, grid.get(point));
            }
    }

    @Test
    public void test_is_free()
    {
        GridIA grid = new GridIA();
        Vector<Point> free_blocks = new Vector<Point>();
        for (int i = 0; i < grid.width(); i++)
            for (int j = 0; j < grid.height(); j++)
            {
                Point point = new Point(i, j);
                free_blocks.add(point);

                assertTrue("Failure - is_free(int, int): ", grid.is_free(i, j));
                assertTrue("Failure - is_free(Point): ", grid.is_free(point));
            }
        assertTrue("Failure - is_free(Point[]): ", grid.is_free(free_blocks.toArray(new Point[0])));

        Vector<Point> busy_blocks = new Vector<Point>();
        for (int i = 0; i < grid.width(); i = i + 2)
            for (int j = 0; j < grid.height(); j = j + 3)
            {
                Point point = new Point(i, j);
                busy_blocks.add(point);

                grid.put(i, j, i*j);
                assertFalse("Failure - is_free(int, int): ", grid.is_free(i, j));
                assertFalse("Failure - is_free(Point): ", grid.is_free(point));
            }
        assertFalse("Failure - is_free(Point[]): ", grid.is_free(busy_blocks.toArray(new Point[0])));
    }

    @Test
    public void test_put()
    {
        int i_plus = 2; int j_plus = 3;

        GridIA grid_1 = new GridIA();
        GridIA grid_2 = new GridIA();
        GridIA grid_3 = new GridIA();

        Vector<Point> points = new Vector<Point>();
        for (int i = 0; i < GridIA.DEFAULT_WIDTH; i += i_plus)
            for (int j = 0; j < GridIA.DEFAULT_HEIGHT; j += j_plus)
            {
                int value_1 = i * j;
                int value_2 = i * j + 10;
                Point point = new Point(i, j);
                points.add(point);

                grid_1.put(i, j, value_1);
                grid_2.put(point, value_2);

                assertEquals("Failure - put(int, int, int): ", value_1, grid_1.get(i, j));
                assertEquals("Failure - put(Point, int): ", value_2, grid_2.get(point));
            }


        int value_3 = 4;
        grid_3.put(points.toArray(new Point[0]), value_3);
        for (int i = 0; i < GridIA.DEFAULT_WIDTH; i += i_plus)
            for (int j = 0; j< GridIA.DEFAULT_HEIGHT; j += j_plus)
                assertEquals("Failure - put(Point[], int): ", value_3, grid_3.get(i, j));
    }

    @Test
    public void test_in_bonds()
    {
        GridIA grid_1 = new GridIA();
        GridIA grid_2 = new GridIA(30, 40);

        test_in_bonds(grid_1);
        test_in_bonds(grid_2);
    }

    private void test_in_bonds(GridIA grid)
    {
        Vector<Point> in_bonds_points = new Vector<Point>();
        for (int i = 0; i < grid.width(); i++)
            for (int j = 0; j < grid.height(); j++)
            {
                Point point = new Point(i, j);
                in_bonds_points.add(point);

                assertTrue("Failure - in_bonds(int, int): ", grid.in_bonds(i, j));
                assertTrue("Failure - in_bonds(Point): ", grid.in_bonds(point));
            }
        assertTrue("Failure - in_bonds(Point[]): ", grid.in_bonds(in_bonds_points.toArray(new Point[0])));

        Vector<Point> not_in_bonds_points = new Vector<Point>();
        int out_left_x = -1; int out_left_y = 0;
        int out_right_x = grid.width(); int out_right_y = 0;
        int out_top_x = 0; int out_top_y = grid.height();
        int out_bottom_x = 0; int out_bottom_y = -1;
        Point out_left = new Point(out_left_x, out_left_y);
        Point out_right = new Point(out_right_x, out_right_y);
        Point out_top = new Point(out_top_x, grid.height());
        Point out_bottom = new Point(out_bottom_x, out_bottom_y);

        not_in_bonds_points.add(out_left);
        not_in_bonds_points.add(out_right);
        not_in_bonds_points.add(out_top);
        not_in_bonds_points.add(out_bottom);

        assertFalse("Failure - in_bonds(int, int): ", grid.in_bonds(out_left_x, out_left_y));
        assertFalse("Failure - in_bonds(int, int): ", grid.in_bonds(out_left_x, out_right_y));
        assertFalse("Failure - in_bonds(int, int): ", grid.in_bonds(out_top_x, out_top_y));
        assertFalse("Failure - in_bonds(int, int): ", grid.in_bonds(out_bottom_x, out_bottom_y));
        assertFalse("Failure - in_bonds(Point): ", grid.in_bonds(out_left));
        assertFalse("Failure - in_bonds(Point): ", grid.in_bonds(out_right));
        assertFalse("Failure - in_bonds(Point): ", grid.in_bonds(out_top));
        assertFalse("Failure - in_bonds(Point): ", grid.in_bonds(out_bottom));
        assertFalse("Failure - in_bonds(Point[]): ", grid.in_bonds(not_in_bonds_points.toArray(new Point[0])));
    }

    @Test
    public void test_full_column()
    {
        GridIA grid = new GridIA();
        int value = 2;
        int[] full_columns = { 1, 2, 6 };
        Vector<Point> full_columns_points = new Vector<Point>();

        for (int column : full_columns)
        {
            for (int i = 0; i < grid.height(); i++)
            {
                grid.put(column, i, value);
                full_columns_points.add(new Point(column, i));
            }
            assertTrue("Failure - full_column(int): ", grid.full_column(column));
        }
        assertTrue("Failure - full_column(Point[]): ", grid.full_column(full_columns_points.toArray(new Point[0])));

        int[] not_full_columns = { 9, 5 };
        Vector<Point> not_full_columns_points = new Vector<Point>();
        for (int column : not_full_columns)
        {
            for (int i = 0; i < grid.height() - 1; i++)
            {
                grid.put(column, i, value);
                not_full_columns_points.add(new Point(column, i));
            }
            assertFalse("Failure - full_column(int): ", grid.full_column(column));
        }
        assertFalse("Failure - full_column(Point[]): ", grid.full_column(not_full_columns_points.toArray(new Point[0])));
    }

    @Test
    public void test_full()
    {
        GridIA grid_1 = new GridIA();
        GridIA grid_2 = new GridIA();
        GridIA grid_3 = new GridIA();
        int value = 2;

        for (int i = 0; i < grid_2.width(); i++)
            for (int j = 0; j < grid_2.height() - 1; j++)
                grid_2.put(i, j, value);

        for (int i = 0; i < grid_3.height(); i++)
            grid_3.put(0, i, value);

        assertFalse("Failure - full(): ", grid_1.full());
        assertFalse("Failure - full(): ", grid_2.full());
        assertTrue("Failure - full(): ", grid_3.full());
    }

    @Test
    public void test_full_line()
    {
        GridIA grid = new GridIA();
        int value = 2;
        int[] full_lines = { 1, 2, 6 };
        Vector<Point> full_lines_points = new Vector<Point>();

        for (int line : full_lines)
        {
            for (int i = 0; i < grid.width(); i++)
            {
                grid.put(i, line, value);
                full_lines_points.add(new Point(i, line));
            }
            assertTrue("Failure - full_line(int): ", grid.full_line(line));
        }
        assertTrue("Failure - full_line(Point[]): ", grid.full_line(full_lines_points.toArray(new Point[0])));

        int[] not_full_lines = { 9, 5 };
        Vector<Point> not_full_lines_points = new Vector<Point>();
        for (int line : not_full_lines)
        {
            for (int i = 0; i < grid.width() - 1; i++)
            {
                grid.put(i, line, value);
                not_full_lines_points.add(new Point(i, line));
            }
            assertFalse("Failure - full_line(int): ", grid.full_line(line));
        }
        assertFalse("Failure - full_line(Point[]): ", grid.full_line(not_full_lines_points.toArray(new Point[0])));
    }

    @Test
    public void test_check()
    {
        GridIA grid_1 = new GridIA();

        for (int i = 0; i < grid_1.width(); i++)
            for (int j = 0; j < 2; j++)
                grid_1.put(i, j, i*j);

        GridIA grid_2 = new GridIA(grid_1);
        Point[] points_1 = { new Point(0, 0), new Point(0, 1) };
        int destroyed_1 = grid_1.check(points_1);

        assertEquals("Failure - check(): ", 2, destroyed_1);
        assertFalse("Failure - check(): ", grid_1.equals(grid_2));
        for (int i = 0; i < grid_1.height(); i++)
            assertFalse("Failure - check(): ", grid_1.full_line(i));

        GridIA grid_3 = new GridIA();
        Point[] points_3 = new Point[grid_3.height()];
        for (int j = 0; j < grid_3.height(); j++)
        {
            points_3[j] = new Point(0, j);
            for (int i = 0; i < grid_3.width() - 1; i++)
                grid_3.put(i, j, i*j);
        }

        GridIA grid_4 = new GridIA(grid_3);
        int destroyed_3 = grid_3.check(points_3);

        assertEquals("Failure - check(): ", 0, destroyed_3);
        assertTrue("Failure - check(): ", grid_3.equals(grid_4));
        for (int i = 0; i < grid_3.height(); i++)
            assertFalse("Failure - check(): ", grid_3.full_line(i));
    }

    /* GridIA */
    @Test
    public void test_blocks()
    {
        GridIA grid_1 = new GridIA();
        GridIA grid_2 = new GridIA();
        GridIA grid_3 = new GridIA();
        GridIA grid_4 = new GridIA();

        Vector<Point> points = new Vector<Point>();
        for (int j = 0; j < grid_2.height(); j++)
        {
            for (int i = 0; i < grid_2.width(); i++)
            {
                grid_2.put(i, j, i*j);
                grid_3.put(i, j, i*j);
            }
            points.add(new Point(0, j));
        }

        grid_2.check(points.toArray(new Point[0]));
        grid_4.copy(grid_3);

        assertEquals("Failure - blocks(): ", 0, grid_1.blocks());
        assertEquals("Failure - blocks(): ", 0, grid_2.blocks());
        assertEquals("Failure - blocks(): ", grid_3.width() * grid_3.height(), grid_3.blocks());
        assertEquals("Failure - blocks(): ", grid_3.blocks(), grid_4.blocks());
    }

    @Test
    public void test_holes()
    {
        GridIA grid_1 = new GridIA();
        GridIA grid_2 = new GridIA();
        GridIA grid_3 = new GridIA();
        GridIA grid_4 = new GridIA();
        GridIA grid_5 = new GridIA();
        int[] full_lines = { 0, 1, 3, 5 };
        Vector<Point> points = new Vector<Point>();

        for (int i = 0; i < grid_2.width(); i++)
            for (int j = 0; j < grid_2.height(); j++)
                grid_2.put(i, j, i*j);

            for (int j : full_lines)
            {
                for (int i = 0; i < grid_3.width(); i++)
                {
                    grid_3.put(i, j, i*j);
                    grid_4.put(i, j, i*j);
                }
                points.add(new Point(0, j));
            }

        for (int j : new int[] { 0, 2, 4, 5 })
            grid_4.put(j, 2, 3*j);
        for (int j : new int[] { 0, 1, 3 })
            grid_4.put(j, 4, 3*j);
        for (int j : new int[] { 0, 1, 2, 3, 6 })
            grid_4.put(j, 6, 3*j);
        points.add(new Point(0, 2));
        points.add(new Point(0, 4));
        points.add(new Point(0, 6));
        grid_4.check(points.toArray(new Point[0]));

        grid_5.copy(grid_4);

        assertEquals("Failure - holes(): ", 0, grid_1.holes());
        assertEquals("Failure - holes(): ", 0, grid_2.holes());
        assertEquals("Failure - holes(): ", 2*grid_3.width(), grid_3.holes());
        assertEquals("Failure - holes(): ", 5, grid_4.holes());
        assertEquals("Failure - holes(): ", grid_4.holes(), grid_5.holes());
    }

    @Test
    public void test_highest_blocks()
    {
        GridIA grid_1 = new GridIA();
        GridIA grid_2 = new GridIA();
        GridIA grid_3 = new GridIA();
        GridIA grid_4 = new GridIA();

        int[] blocks_1 = new int[grid_1.width()];
        for (int i = 0; i < grid_1.width(); i++)
            blocks_1[i] = -1;

        int[] blocks_2 = new int[grid_2.width()];
        for (int i = 0; i < grid_2.width(); i++)
            blocks_2[i] = grid_2.height() - 1;

        int[] blocks_3 = new int[grid_3.width()];
        for (int i = 0; i < grid_3.width(); i++)
        {
            blocks_3[i] = i;
            grid_3.put(i, i, i*i);
        }

        for (int i = 0; i < grid_2.width(); i++)
            for (int j = 0; j < grid_2.height(); j++)
                grid_2.put(i, j, i*j);

        grid_4.copy(grid_3);

        assertArrayEquals(blocks_1, grid_1.highest_blocks());
        for (int i = 0; i < grid_1.width(); i++)
        {
            assertEquals(blocks_1[i], grid_1.highest_block(i));
            assertEquals(grid_1.highest_block(i), grid_1.highest_blocks()[i]);
        }

        assertArrayEquals(blocks_2, grid_2.highest_blocks());
        for (int i = 0; i < grid_2.width(); i++)
        {
            assertEquals(blocks_2[i], grid_2.highest_block(i));
            assertEquals(grid_2.highest_block(i), grid_2.highest_blocks()[i]);
        }

        assertArrayEquals(blocks_3, grid_3.highest_blocks());
        for (int i = 0; i < grid_3.width(); i++)
        {
            assertEquals(blocks_3[i], grid_3.highest_block(i));
            assertEquals(grid_3.highest_block(i), grid_3.highest_blocks()[i]);
        }

        assertArrayEquals(blocks_3, grid_4.highest_blocks());
        assertArrayEquals(grid_3.highest_blocks(), grid_4.highest_blocks());
        for (int i = 0; i < grid_4.width(); i++)
        {
            assertEquals(blocks_3[i], grid_4.highest_block(i));
            assertEquals(grid_3.highest_block(i), grid_4.highest_block(i));
        }
    }
}
