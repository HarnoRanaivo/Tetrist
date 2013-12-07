package IA;

import java.util.Vector;

import Component.Point;
import Component.Grid;
import Component.Piece;

public class Eval
{
    public static int[] eval_possibilities(Grid grid, Piece piece)
    {
        boolean go_on = true;
        GridState best_state = null;
        int[] results = new int[3];
        Point[] points_buffer = new Point[4];
        for (int i = 0; i < 4; i++)
            points_buffer[i] = new Point();

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
                    best_state = current_state;
                    results[0] = i;
                    results[1] = KeySender.LEFT;
                    results[2] = piece_buffer.minimum_abcissa() - piece_buffer_left.minimum_abcissa();
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
                    best_state = current_state;
                    results[0] = i;
                    results[1] = KeySender.RIGHT;
                    results[2] = piece_buffer_right.minimum_abcissa() - piece_buffer.minimum_abcissa();
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
                best_state = current_state;
                results[0] = i;
                results[1] = KeySender.NOTHING;
                results[2] = 0;
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

        return results;
    }

    public static int eval_state(int score, int evaluation)
    {
        return 0;
    }
}
