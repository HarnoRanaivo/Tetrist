package IA;

import java.util.Vector;

import Component.Point;
import Component.Grid;
import Component.Piece;

public class Eval
{
    public static int[] eval_possibilities(Grid grid, Piece piece)
    {
        GridState best_state = null;
        int[] results = new int[3];
        Point[] needed_space = new Point[4];
        for (int i = 0; i < 4; i++)
            needed_space[i] = new Point();

        Piece piece_buffer = new Piece(piece);
        for (int i = 0; i < Piece.ROTATIONS[piece.id()] + 1; i++)
        {
            Piece piece_buffer_left = new Piece(piece_buffer);
            Piece piece_buffer_right = new Piece(piece_buffer);

            piece_buffer_left.needed_space_left(needed_space);
            while (grid.in_bonds(needed_space) && grid.is_free(needed_space))
            {
                piece_buffer_left.left();
                GridIA grid_buffer = new GridIA(grid);
                Piece piece_buffer_local = new Piece(piece_buffer_left);

                grid_buffer.brute_fall(piece_buffer_local);
                grid_buffer.check(piece_buffer_local.coordinates());

                GridState current_state = grid_buffer.eval();
                if (best_state == null)
                {
                    best_state = current_state;
                }
                else if (current_state.compareTo(best_state) > 0)
                {
                    results[0] = i;
                    results[1] = KeySender.LEFT;
                    results[2] = piece_buffer.minimum_abcissa() - piece_buffer_left.minimum_abcissa();
                }
                piece_buffer_left.needed_space_left(needed_space);
            }

            piece_buffer_right.needed_space_right(needed_space);
            while (grid.in_bonds(needed_space) && grid.is_free(needed_space))
            {
                piece_buffer_right.right();
                GridIA grid_buffer = new GridIA(grid);
                Piece piece_buffer_local = new Piece(piece_buffer_right);

                grid_buffer.brute_fall(piece_buffer_local);
                grid_buffer.check(piece_buffer_local.coordinates());

                GridState current_state = grid_buffer.eval();
                if (best_state == null)
                {
                    best_state = current_state;
                }
                else if (current_state.compareTo(best_state) > 0)
                {
                    results[0] = i;
                    results[1] = KeySender.RIGHT;
                    results[2] = piece_buffer_right.minimum_abcissa() - piece_buffer.minimum_abcissa();
                }
                piece_buffer_right.needed_space_right(needed_space);
            }

            piece_buffer.rotate();
        }

        return results;
    }

    public static int eval_state(int score, int evaluation)
    {
        return 0;
    }
}
