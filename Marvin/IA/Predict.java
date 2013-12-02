package IA;

import java.util.Vector;

import Component.Point;
import Component.Grid;
import Component.Piece;

public class Predict
{
    private Grid grid_buffer;
    private Point[] points_buffer = new Point[4];
    private IntComp lower_than = new IntComp(true);
    private IntComp greater_than = new IntComp(false);

    public Predict()
    {
        grid_buffer = new Grid();
        for (int i = 0; i < points_buffer.length; i++)
            points_buffer[i] = new Point();
    }

    public Point[][][] possible_falls(Grid grid, Piece piece)
    {
        Vector<Point[]>[] falls = new Vector<Point[]>[4];
        int[] blocks = grid.highest_blocks();
        int[] columns = possible_columns(grid, piece, blocks);
        Piece piece_buffer = new Piece(piece);

        for (int i = 0; i < 4; i++)
        {
            falls[i] = new Vector<Point[]>();
            Point[] current_coordinates = piece_buffer.coordinates();
            int left = min_abcissa(current_coordinates);
            int right = max_abcissa(current_coordinates);
            int piece_width = right - left;
            int piece_bottom = min_ordinate(current_coordinates);
            int max_shift = columns[1] - columns[0] - piece_width;

            piece_buffer.left(left - columns[0]);
            for (int j = 0; j < max_shift; j++)
            {
                int current_left = min_abcissa(current_coordinates);
                int max_height = blocks[current_left];
                for (int k = 0; k < 4; k++)
                    if (blocks[current_left+k] > max_height)
                        max_height = blocks[current_left+k];

                int max_fall = piece_bottom - max_height;
                piece_buffer.fall(max_fall);
                Point[] candidates = new Point[4];
                for (int m = 0; m < 4; m++)
                    candidates = new Point(piece_buffer.coordinates()[m]);
                falls[i].add(candidates);
                piece_buffer.fly(max_fall);
                piece_buffer.right();
            }
            piece_buffer.left(max_shift + 1);
            piece_buffer.rotate();
        }

        Point[][][] result = new Point[4][][];
        for (int i = 0; i < 4; i++)
        {
            result[i] = falls[i].toArray(new Point[falls[i].size()][]);
        }

        return result;
    }

    private void copy_points(Point[] points)
    {
        for (int i = 0; i < points.length(); i++)
            points_buffer[i].set(points[i]);
    }

    private int min_max(Point[] points, IntComp comparator, PointValueGetter getter)
    {
        int value = getter.get_point_value(points[0]);

        for (Point point : points)
        {
            int candidate = getter.get_value(point);
            if (comparator.compare(value, candidate))
                    value = candidate;
        }

        return value;
    }

    private int min_abcissa(Point[] points)
    {
        return min_max(points, greater_than, Point.ABCISSA_GETTER);
    }

    private int max_abcissa(Point[] points)
    {
        return min_max(points, lower_than, Point.ABCISSA_GETTER);
    }

    private int min_ordinate(Point[] points)
    {
        return min_max(points, greater_than, Point.ORDINATE_GETTER);
    }

    private int max_ordinate(Point[] points)
    {
        return min_max(points, lower_than, Point.ORDINATE_GETTER);
    }

    int[] possible_columns(Grid grid, Piece piece, int[] highest)
    {
        Point[] coordinates = piece.coordinates();
        int piece_bottom = min_ordinate(coordinates);
        int piece_left = min_abcissa(coordinates);
        int piece_right = max_abcissa(coordinates);
        int[] results = new results[3];
        results[0] = piece_left;
        results[1] = piece_right;

        for (int i = piece_left-1; i >= 0 && highest[i] < piece_bottom; i--)
            left--;

        for (int i = piece_right+1; i <= grid.width() && highest[i] < piece_bottom; i++)
            right++;

        return results;
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
