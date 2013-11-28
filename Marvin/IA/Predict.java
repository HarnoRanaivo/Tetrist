package IA;

import java.util.Vector;

import Component.Point;
import Component.Grid;
import Component.Piece;

public class Predict
{
    private Grid grid_buffer;
    private Point[] points_buffer;
    private Getter abcissa_getter = new Getter(true);
    private Getter ordinate_getter = new Getter(false);
    private IntComp lower_than = new IntComp(true);
    private IntComp greater_than = new IntComp(false);

    public Predict()
    {
        grid_buffer = new Grid();
        points_buffer = new Point[4];
        for (int i = 0; i < points_buffer.length; i++)
            points_buffer[i] = new Point();
    }

    public Vector<Point[]> possible_falls(Grid grid, Piece piece)
    {
        Vector<Integer> columns = possible_columns(grid, piece);
        Piece piece_buffer = new Piece(piece);
        Point[] initial_coordinates = piece.coordinates();

        return null;
    }

    private void copy_points(Point[] points)
    {
        for (int i = 0; i < points.length(); i++)
            points_buffer[i].set(points[i]);
    }

    private void copy_grid(Grid grid)
    {
        for (int i = 0; i < grid.width(); i++)
            for (int j = 0; i < grid.height(); j++)
                grid_buffer.put(i, j, grid.get(i, j));
    }

    private int min_max(Point[] points, IntComp comparator, Getter getter)
    {
        int value = getter.get_point_value(points[0]);

        for (Point point : points)
        {
            int candidate = getter.get_point_value(point);
            if (comparator.compare(value, candidate))
                    value = candidate;
        }

        return value;
    }

    private int min_abcissa(Point[] points)
    {
        return min_max(points, greater_than, abcissa_getter);
    }

    private int max_abcissa(Point[] points)
    {
        return min_max(points, lower_than, abcissa_getter);
    }

    private int min_ordinate(Point[] points)
    {
        return min_max(points, greater_than, ordinate_getter);
    }

    private int max_ordinate(Point[] points)
    {
        return min_max(points, lower_than, ordinate_getter);
    }

    Vector<Integer> possible_columns(Grid grid, Piece piece)
    {
        int left = 0;
        int right = 0;
        Vector<Integer> possible = new Vector<Integer>();
        Point[] coordinates = piece.coordinates();
        int piece_bottom = min_ordinate(coordinates);
        int piece_left = min_abcissa(coordinates);
        int piece_right = max_abcissa(coordinates);

        for (int i = 0; i < piece_left; i++)
            if (grid.highest_block(i) < piece_bottom)
                possible.add(i);

        for (int i = piece_right + 1; i < grid.width(); i++)
            if (grid.highest_block(i) < piece_bottom)
                possible.add(i);

        return possible;
    }

    class Getter
    {
        boolean get_abcissa;

        Getter(boolean abcissa)
        {
            get_abcissa = abcissa;
        }

        int get_point_value(Point point)
        {
            if (get_abcissa)
                return point.abcissa();
            else
                return point.ordinate();
        }
    }

    class IntComp
    {
        boolean lower;

        IntComp(boolean low)
        {
            lower = low;
        }

        boolean compare(int a, int b)
        {
            if (lower)
                return a < b;
            else
                return a >= b;
        }
    }
}
