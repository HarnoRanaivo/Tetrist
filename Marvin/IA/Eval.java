package IA;

import java.util.Vector;
import java.lang.Math;

import Component.Point;
import Component.Grid;
import Component.Piece;
import IA.Orders;

public class Eval
{
    static EvalMove LEFT = new EvalMoveLeft();
    static EvalMove RIGHT = new EvalMoveRight();

    public static Orders eval_possibilities(Grid grid, Piece piece)
    {
        boolean go_on = true;
        GridState best_state = null;
        Orders orders = new Orders();
        Point[] points_buffer = new Point[4];
        for (int i = 0; i < 4; i++)
            points_buffer[i] = new Point();

        Piece piece_buffer = new Piece(piece);
        for (int i = 0; go_on && i < Piece.ROTATIONS[piece.id()] + 1; i++)
        {
            best_state = loop_move(grid, piece_buffer, best_state, orders, i, LEFT, points_buffer);
            best_state = loop_move(grid, piece_buffer, best_state, orders, i, RIGHT, points_buffer);

            Piece piece_buffer_nothing = new Piece(piece_buffer);
            GridState current_state = eval_column(grid, piece_buffer_nothing);
            best_state = check_state(best_state, current_state, piece_buffer, piece_buffer_nothing, orders, i, KeySender.NOTHING);

            piece_buffer.needed_space_rotation(points_buffer);
            if (grid.in_bonds(points_buffer) && grid.is_free(points_buffer))
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

    private static GridState loop_move(Grid grid, Piece piece, GridState current, Orders orders, int rotation, EvalMove e_move, Point[] points_buffer)
    {
        GridState best_state = current;

        Piece piece_buffer = new Piece(piece);
        e_move.needed_space(piece_buffer, points_buffer);
        while (grid.in_bonds(points_buffer) && grid.is_free(points_buffer))
        {
            e_move.move(piece_buffer);
            GridState current_state = eval_column(grid, piece_buffer);
            best_state = check_state(current, current_state, piece, piece_buffer, orders, rotation, e_move.key);
            e_move.needed_space(piece_buffer, points_buffer);
        }

        return best_state;
    }

    public static int eval_state(int score, int evaluation)
    {
        return 0;
    }
}

abstract class EvalMove
{
    public final int key;
    public EvalMove(int k)
    {
        key = k;
    }
    public abstract void needed_space(Piece piece, Point[] buffer);
    public abstract void move(Piece piece);
}

class EvalMoveLeft extends EvalMove
{
    public EvalMoveLeft()
    {
        super(KeySender.LEFT);
    }

    public void needed_space(Piece piece, Point[] buffer)
    {
        piece.needed_space_left(buffer);
    }

    public void move(Piece piece)
    {
        piece.left();
    }
}

class EvalMoveRight extends EvalMove
{
    public EvalMoveRight()
    {
        super(KeySender.RIGHT);
    }

    public void needed_space(Piece piece, Point[] buffer)
    {
        piece.needed_space_right(buffer);
    }

    public void move(Piece piece)
    {
        piece.right();
    }
}
