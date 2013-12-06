package IA;

import java.util.Vector;

import Component.Point;
import Component.PointValueGetter;
import Component.Grid;
import Component.Piece;

public class Predict
{
    private Grid grid_buffer;
    private Point[] points_buffer = new Point[4];

    public Predict()
    {
        grid_buffer = new Grid();
        for (int i = 0; i < points_buffer.length; i++)
            points_buffer[i] = new Point();
    }

    public static Point[][][] possible_falls(Grid grid, Piece piece)
    {
        GridIA grid_buffer = new GridIA(grid);
        Vector<Vector<Point[]>> falls = new Vector<Vector<Point[]>>();
        int[] blocks = grid_buffer.highest_blocks_array();
        int[] columns = possible_columns(grid_buffer, piece, blocks);
        Piece piece_buffer = new Piece(piece);

        for (int i = 0; i < 4; i++)
        {
            falls.add(new Vector<Point[]>());
            int left = piece_buffer.minimum_abcissa();
            int right = piece_buffer.maximum_abcissa();
            int piece_width = right - left;
            int piece_bottom = piece_buffer.minimum_ordinate();
            int max_shift = columns[1] - columns[0] - piece_width + 1;
            int shift = left - columns[0];

            piece_buffer.left(shift);
            for (int j = 0; j < max_shift; j++)
            {
                int current_left = piece_buffer.minimum_abcissa();
                int max_height = blocks[current_left];
                for (int k = 0; k < piece_width; k++)
                    if (blocks[current_left+k] > max_height)
                        max_height = blocks[current_left+k];

                int max_fall = piece_bottom - max_height - 1;
                max_fall = max_fall < 0 ? 0 : max_fall;
                piece_buffer.fall(max_fall);
                Point[] candidates = new Point[4];
                for (int m = 0; m < 4; m++)
                    candidates[m] = new Point(piece_buffer.coordinates()[m]);
                falls.elementAt(i).add(candidates);
                piece_buffer.fly(max_fall);
                piece_buffer.right();
            }
            piece_buffer.left(max_shift + shift);
            piece_buffer.rotate();
        }

        Point[][][] result = new Point[4][][];
        for (int i = 0; i < 4; i++)
        {
            result[i] = falls.elementAt(i).toArray(new Point[0][]);
        }

        return result;
    }

    private void copy_points(Point[] points)
    {
        for (int i = 0; i < points.length; i++)
            points_buffer[i].set(points[i]);
    }

    public static int[] possible_columns(Grid grid, Piece piece, int[] highest)
    {
        int piece_bottom = piece.minimum_ordinate();
        int piece_left = piece.minimum_abcissa();
        int piece_right = piece.maximum_abcissa();
        int[] results = new int[2];
        results[0] = piece_left;
        results[1] = piece_right;

        for (int i = piece_left-1; i >= 0 && highest[i] < piece_bottom; i--)
            results[0]--;

        for (int i = piece_right+1; i < grid.width() && highest[i] < piece_bottom; i++)
            results[1]++;

        return results;
    }
}
