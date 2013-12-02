import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.Comparator;
import Component.Point;

@RunWith(JUnit4.class)
public class TestPoint
{
    @Test
    public void test_equals()
    {
        int x_2 = 2; int y_2 = 3;
        Point point_1 = new Point();
        Point point_2 = new Point(x_2, y_2);

        assertTrue(point_1.equals(new Point()));
        assertTrue(point_2.equals(new Point(x_2, y_2)));
        assertTrue(point_2.equals(new Point(point_2)));

        assertFalse(point_1.equals(point_2));
    }

    @Test
    public void test_getters()
    {
        int x_1 = 0; int y_1 = 0;
        int x_2 = 2; int y_2 = 9;
        int x_3 = -1; int y_3 = -8;
        int x_4 = -3; int y_4 = 10;
        int x_5 = 15; int y_5 = -6;
        Point point_1 = new Point();
        Point point_2 = new Point(x_2, y_2);
        Point point_3 = new Point(x_3, y_3);
        Point point_4 = new Point(x_4, y_4);
        Point point_5 = new Point(x_5, y_5);

        assertEquals(x_1, point_1.abcissa());
        assertEquals(x_2, point_2.abcissa());
        assertEquals(x_3, point_3.abcissa());
        assertEquals(x_4, point_4.abcissa());
        assertEquals(x_5, point_5.abcissa());

        assertEquals(y_1, point_1.ordinate());
        assertEquals(y_2, point_2.ordinate());
        assertEquals(y_3, point_3.ordinate());
        assertEquals(y_4, point_4.ordinate());
        assertEquals(y_5, point_5.ordinate());
    }

    @Test
    public void test_set()
    {
        int x_1 = 0; int y_1 = 0;
        int x_2 = 2; int y_2 = 9;
        int x_3 = -1; int y_3 = -8;
        int x_4 = -3; int y_4 = 10;
        int x_5 = 15; int y_5 = -6;
        Point point_1 = new Point();
        Point point_2 = new Point();
        Point point_3 = new Point();
        Point point_4 = new Point();
        Point point_5 = new Point();

        point_1.set(x_1, y_1);
        point_2.set(x_2, y_2);
        point_3.set(x_3, y_3);
        point_4.set(x_4, y_4);
        point_5.set(x_5, y_5);

        assertEquals(x_1, point_1.abcissa());
        assertEquals(x_2, point_2.abcissa());
        assertEquals(x_3, point_3.abcissa());
        assertEquals(x_4, point_4.abcissa());
        assertEquals(x_5, point_5.abcissa());

        assertEquals(y_1, point_1.ordinate());
        assertEquals(y_2, point_2.ordinate());
        assertEquals(y_3, point_3.ordinate());
        assertEquals(y_4, point_4.ordinate());
        assertEquals(y_5, point_5.ordinate());

        Point point_6 = new Point();
        Point point_7 = new Point();
        Point point_8 = new Point();
        Point point_9 = new Point();
        Point point_10 = new Point();

        point_6.set(point_1);
        point_7.set(point_2);
        point_8.set(point_3);
        point_9.set(point_4);
        point_10.set(point_5);

        assertEquals(point_1, point_6);
        assertEquals(point_2, point_7);
        assertEquals(point_3, point_8);
        assertEquals(point_4, point_9);
        assertEquals(point_5, point_10);
    }

    @Test
    public void test_set_abcissa()
    {
        int x_6 = 0;
        int x_7 = 180;
        int x_8 = -121;
        Point point_6 = new Point();
        Point point_7 = new Point();
        Point point_8 = new Point();

        point_6.set_abcissa(x_6);
        point_7.set_abcissa(x_7);
        point_8.set_abcissa(x_8);

        assertEquals(x_6, point_6.abcissa());
        assertEquals(0, point_6.ordinate());
        assertEquals(x_7, point_7.abcissa());
        assertEquals(0, point_7.ordinate());
        assertEquals(x_8, point_8.abcissa());
        assertEquals(0, point_8.ordinate());
    }

    @Test
    public void test_set_ordinate()
    {
        int y_9 = 0;
        int y_10 = 180;
        int y_11 = -121;
        Point point_9 = new Point();
        Point point_10 = new Point();
        Point point_11 = new Point();

        point_9.set_ordinate(y_9);
        point_10.set_ordinate(y_10);
        point_11.set_ordinate(y_11);

        assertEquals(0, point_9.abcissa());
        assertEquals(y_9, point_9.ordinate());
        assertEquals(0, point_10.abcissa());
        assertEquals(y_10, point_10.ordinate());
        assertEquals(0, point_11.abcissa());
        assertEquals(y_11, point_11.ordinate());
    }

    @Test
    public void test_shifters()
    {
        int x_1 = 0; int y_1 = 0;
        int x_2 = 2; int y_2 = 9;
        int x_3 = -1; int y_3 = -8;
        int x_4 = -3; int y_4 = 10;
        int x_5 = 15; int y_5 = -6;
        int x_6 = 11; int y_6 = 3;
        int x_7 = 5; int y_7 = 0;
        Point point_1 = new Point();
        Point point_2 = new Point(x_2, y_2);
        Point point_3 = new Point(x_3, y_3);
        Point point_4 = new Point(x_4, y_4);
        Point point_5 = new Point(x_5, y_5);
        Point point_6 = new Point(x_6, y_6);
        Point point_7 = new Point(x_7, y_7);

        int s_x_1 = 0; int s_y_1 = 0;
        int s_x_2 = 10; int s_y_2 = 0;
        int s_x_3 = 0; int s_y_3 = 9784;
        int s_x_4 = -381; int s_y_4 = 0;
        int s_x_5 = 0; int s_y_5 = -482;
        int s_x_6 = 529; int s_y_6 = 173;
        int s_x_7 = -6823; int s_y_7 = -252;
        point_1.shift(s_x_1, s_y_1);
        point_2.shift(s_x_2, s_y_2);
        point_3.shift(s_x_3, s_y_3);
        point_4.shift(s_x_4, s_y_4);
        point_5.shift(s_x_5, s_y_5);
        point_6.shift(s_x_6, s_y_6);
        point_7.shift(s_x_7, s_y_7);

        assertEquals(x_1 + s_x_1, point_1.abcissa());
        assertEquals(x_2 + s_x_2, point_2.abcissa());
        assertEquals(x_3 + s_x_3, point_3.abcissa());
        assertEquals(x_4 + s_x_4, point_4.abcissa());
        assertEquals(x_5 + s_x_5, point_5.abcissa());
        assertEquals(x_6 + s_x_6, point_6.abcissa());
        assertEquals(x_7 + s_x_7, point_7.abcissa());

        assertEquals(y_1 + s_y_1, point_1.ordinate());
        assertEquals(y_2 + s_y_2, point_2.ordinate());
        assertEquals(y_3 + s_y_3, point_3.ordinate());
        assertEquals(y_4 + s_y_4, point_4.ordinate());
        assertEquals(y_5 + s_y_5, point_5.ordinate());
        assertEquals(y_6 + s_y_6, point_6.ordinate());
        assertEquals(y_7 + s_y_7, point_7.ordinate());
    }

    @Test
    public void test_abcissa_getter()
    {
        int x_1 = 0; int y_1 = 0;
        int x_2 = 2; int y_2 = 9;
        int x_3 = -1; int y_3 = -8;
        int x_4 = -3; int y_4 = 10;
        int x_5 = 15; int y_5 = -6;
        Point point_1 = new Point();
        Point point_2 = new Point(x_2, y_2);
        Point point_3 = new Point(x_3, y_3);
        Point point_4 = new Point(x_4, y_4);
        Point point_5 = new Point(x_5, y_5);

        assertEquals(x_1, Point.ABCISSA_GETTER.get_value(point_1));
        assertEquals(x_2, Point.ABCISSA_GETTER.get_value(point_2));
        assertEquals(x_3, Point.ABCISSA_GETTER.get_value(point_3));
        assertEquals(x_4, Point.ABCISSA_GETTER.get_value(point_4));
        assertEquals(x_5, Point.ABCISSA_GETTER.get_value(point_5));
    }

    @Test
    public void test_ordinate_getter()
    {
        int x_1 = 0; int y_1 = 0;
        int x_2 = 2; int y_2 = 9;
        int x_3 = -1; int y_3 = -8;
        int x_4 = -3; int y_4 = 10;
        int x_5 = 15; int y_5 = -6;
        Point point_1 = new Point();
        Point point_2 = new Point(x_2, y_2);
        Point point_3 = new Point(x_3, y_3);
        Point point_4 = new Point(x_4, y_4);
        Point point_5 = new Point(x_5, y_5);

        assertEquals(y_1, Point.ORDINATE_GETTER.get_value(point_1));
        assertEquals(y_2, Point.ORDINATE_GETTER.get_value(point_2));
        assertEquals(y_3, Point.ORDINATE_GETTER.get_value(point_3));
        assertEquals(y_4, Point.ORDINATE_GETTER.get_value(point_4));
        assertEquals(y_5, Point.ORDINATE_GETTER.get_value(point_5));
    }

    @Test
    public void test_abcissa_comparator()
    {
        int x_1 = 0; int y_1 = 0;
        int x_2 = 2; int y_2 = 9;
        int x_3 = -1; int y_3 = -8;
        Point point_1 = new Point();
        Point point_2 = new Point(x_2, y_2);
        Point point_3 = new Point(x_3, y_3);

        int c_1_1 = Integer.valueOf(x_1).compareTo(Integer.valueOf(x_1));
        int c_1_2 = Integer.valueOf(x_1).compareTo(Integer.valueOf(x_2));
        int c_1_3 = Integer.valueOf(x_1).compareTo(Integer.valueOf(x_3));

        assertEquals(c_1_1, Point.ABCISSA_COMPARATOR.compare(point_1, point_1));
        assertEquals(c_1_2, Point.ABCISSA_COMPARATOR.compare(point_1, point_2));
        assertEquals(c_1_3, Point.ABCISSA_COMPARATOR.compare(point_1, point_3));
    }

    @Test
    public void test_ordinate_comparator()
    {
        int x_1 = 0; int y_1 = 0;
        int x_2 = 2; int y_2 = 9;
        int x_3 = -1; int y_3 = -8;
        Point point_1 = new Point();
        Point point_2 = new Point(x_2, y_2);
        Point point_3 = new Point(x_3, y_3);

        int c_1_1 = Integer.valueOf(y_1).compareTo(Integer.valueOf(y_1));
        int c_1_2 = Integer.valueOf(y_1).compareTo(Integer.valueOf(y_2));
        int c_1_3 = Integer.valueOf(y_1).compareTo(Integer.valueOf(y_3));

        assertEquals(c_1_1, Point.ORDINATE_COMPARATOR.compare(point_1, point_1));
        assertEquals(c_1_2, Point.ORDINATE_COMPARATOR.compare(point_1, point_2));
        assertEquals(c_1_3, Point.ORDINATE_COMPARATOR.compare(point_1, point_3));
    }

}
