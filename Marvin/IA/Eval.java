package IA;

import java.util.Vector;
import java.lang.Math;

import Component.Point;
import Component.Grid;
import Component.Piece;
import IA.Orders;

public class Eval
{
    public static Orders eval_possibilities(Grid grid, Piece piece)
    {
        boolean go_on = true;
        GridState best_state = null;
        Point[] points_buffer = new Point[4];
        for (int i = 0; i < 4; i++)
            points_buffer[i] = new Point();
        Orders orders = new Orders();
        int[] results = new int[3];

        Piece piece_buffer = new Piece(piece);
        for (int i = 0; go_on && i < Piece.ROTATIONS[piece.id()] + 1; i++)
        {
            Piece piece_buffer_left = new Piece(piece_buffer);
            Piece piece_buffer_right = new Piece(piece_buffer);
            Piece piece_buffer_nothing = new Piece(piece_buffer);

            piece_buffer_left.needed_space_left(points_buffer);
            while (grid.in_bonds(points_buffer) && grid.is_free(points_buffer))
            {
                piece_buffer_left.left();
                GridState current_state = eval_column(grid, piece_buffer_left);
                best_state = check_state(best_state, current_state, piece_buffer, piece_buffer_left, orders, i, KeySender.LEFT);
                piece_buffer_left.needed_space_left(points_buffer);
            }

            piece_buffer_right.needed_space_right(points_buffer);
            while (grid.in_bonds(points_buffer) && grid.is_free(points_buffer))
            {
                piece_buffer_right.right();
                GridState current_state = eval_column(grid, piece_buffer_right);
                best_state = check_state(best_state, current_state, piece_buffer, piece_buffer_right, orders, i, KeySender.RIGHT);
                piece_buffer_right.needed_space_right(points_buffer);
            }

            GridState current_state = eval_column(grid, piece_buffer_nothing);
            best_state = check_state(best_state, current_state, piece_buffer, piece_buffer_nothing, orders, i, KeySender.NOTHING);

            Point[] rotation_buffer = new Point[4];
            for (int j = 0; j < 4; j++)
                rotation_buffer[j] = new Point();
            piece_buffer.needed_space_rotation(rotation_buffer);
            if (grid.in_bonds(rotation_buffer) && grid.is_free(rotation_buffer))
                piece_buffer.rotate();
            else
                go_on = false;
        }

        return orders;
    }

    private static GridState eval_column(Grid grid, Piece piece)
    {
        GridIA grid_buffer = new GridIA(grid);
        Piece piece_buffer = new Piece(piece);

        grid_buffer.brute_fall(piece_buffer);
        grid_buffer.check(piece_buffer.coordinates());

        return grid_buffer.eval();
    }

    private static GridState check_state(GridState current, GridState candidate_state, Piece original, Piece candidate_piece, Orders orders, int rotation, int key)
    {
        GridState best = current;
        if (current == null || candidate_state.compareTo(current) > 0)
        {
            int shift = Math.abs(original.minimum_abcissa() - candidate_piece.minimum_abcissa());
            orders.set(rotation, key, shift);
            best = candidate_state;
        }

        return best;
    }

    public static int eval_state(int score, int evaluation)
    {
        return 0;
    }
}
