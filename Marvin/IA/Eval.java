package IA;

import java.util.Vector;

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

            piece_buffer_left.needed_space_left(points_buffer);
            while (grid.in_bonds(points_buffer) && grid.is_free(points_buffer))
            {
                piece_buffer_left.left();
                GridIA grid_buffer = new GridIA(grid);
                Piece piece_buffer_local = new Piece(piece_buffer_left);

                grid_buffer.brute_fall(piece_buffer_local);
                grid_buffer.check(piece_buffer_local.coordinates());

                GridState current_state = grid_buffer.eval();
                if (best_state == null || current_state.compareTo(best_state) > 0)
                {
                    int shift = piece_buffer.minimum_abcissa() - piece_buffer_left.minimum_abcissa();
                    orders.set(i, KeySender.LEFT, shift);
                    best_state = current_state;
                }
                piece_buffer_left.needed_space_left(points_buffer);
            }

            piece_buffer_right.needed_space_right(points_buffer);
            while (grid.in_bonds(points_buffer) && grid.is_free(points_buffer))
            {
                piece_buffer_right.right();
                GridIA grid_buffer = new GridIA(grid);
                Piece piece_buffer_local = new Piece(piece_buffer_right);

                grid_buffer.brute_fall(piece_buffer_local);
                grid_buffer.check(piece_buffer_local.coordinates());

                GridState current_state = grid_buffer.eval();
                if (best_state == null || current_state.compareTo(best_state) > 0)
                {
                    int shift = piece_buffer_right.minimum_abcissa() - piece_buffer.minimum_abcissa();
                    orders.set(i, KeySender.RIGHT, shift);
                    best_state = current_state;
                }
                piece_buffer_right.needed_space_right(points_buffer);
            }

            Piece piece_buffer_nothing = new Piece(piece_buffer);
            GridIA grid_buffer = new GridIA(grid);
            grid_buffer.brute_fall(piece_buffer_nothing);
            grid_buffer.check(piece_buffer_nothing.coordinates());
            GridState current_state = grid_buffer.eval();
            if (best_state == null || current_state.compareTo(best_state) > 0)
            {
                orders.set(i, KeySender.NOTHING, 0);
                best_state = current_state;
            }

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

    public static int eval_state(int score, int evaluation)
    {
        return 0;
    }
}
