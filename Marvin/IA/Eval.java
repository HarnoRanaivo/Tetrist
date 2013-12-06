package IA;

import java.util.Vector;

import Component.Point;
import Component.Grid;
import Component.Piece;

public class Eval
{
    public static int[] eval_possibilities(Grid grid, Point[][][] falls, Piece piece)
    {
        int best_state = 0;
        int[] results = new int[3];

        for (int i = 0; i < falls.length; i++)
            for (int j = 0; j < falls[i].length; j++)
            {
                GridIA grid_buffer = new GridIA(grid);
                Point[] points = falls[i][j];
                grid_buffer.put(points, piece.id());
                int score = grid_buffer.check(points);
                int evaluation = grid_buffer.eval();
                int current_state = eval_state(score, evaluation);
                if (current_state > best_state)
                {
                    results[0] = i;

                    Piece buffer = new Piece(0, 0, 0);
                    buffer.set_coordinates(falls[i][j]);
                    int buffer_x = buffer.minimum_abcissa();
                    int piece_x = piece.minimum_abcissa();
                    if (buffer_x < piece_x)
                    {
                        results[1] = KeySender.LEFT;
                        results[2] = piece_x - buffer_x;
                    }
                    else if (buffer_x > piece_x)
                    {
                        results[1] = KeySender.RIGHT;
                        results[2] = buffer_x - piece_x;
                    }
                    else
                        results[1] = KeySender.NOTHING;
                }
            }

        return results;
    }

    public static int eval_state(int score, int evaluation)
    {
        return 0;
    }
}
