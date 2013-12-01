package IA;

import java.util.Vector;

import Component.Point;
import Component.Grid;
import Component.Piece;

public class Eval
{
    public int eval_possibilities(Grid grid, Point[][][] falls)
    {
        GridIA grid_buffer = new GridIA(grid);
        grid_buffer.copy_grid(grid);

        for (int i = 0; i < falls.length; i++)
            for (int j = 0; i < falls[i].length; j++)
            {
                grid_buffer.copy_grid(grid);
                Point[] points = falls[i][j];
                grid_buffer.put(points);
                int score = grid_buffer.check_and_delete(points);
                int evaluation = grid_buffer.eval();
            }
    }
}
