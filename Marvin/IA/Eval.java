package IA;

import java.util.Vector;

import Component.Point;
import Component.Grid;
import Component.Piece;

public class Eval
{
    public static int[] eval_possibilities(Grid grid, Point[][][] falls)
    {
        int best_state = 0;
        int[] results = new int[2] { 0, 0 };

        for (int i = 0; i < falls.length; i++)
            for (int j = 0; j < falls[i].length; j++)
            {
                GridIA grid_buffer = new GridIA(grid);
                Point[] points = falls[i][j];
                grid_buffer.put(points);
                int score = grid_buffer.check_and_delete(points);
                int evaluation = grid_buffer.eval();
                int current_state = eval_state(score, evaluation);
                if (current_state > best_state)
                {
                    results[0] = i;
                    results[1] = j;
                }
            }

        return results;
    }

    public static int eval_state(int score, int evaluation)
    {
        return 0;
    }
}
