package IA;

import java.util.Vector;
import java.lang.Math;

import Component.Point;
import Component.Grid;
import Component.Piece;
import IA.Orders;

/**
 * Classe d'évaluation d'un mouvement de l'IA.
 */
public class Eval
{
    /**
     * Evalutation d'un mouvement à gauche.
     *
     * @see Eval#eval_possibilities(Grid,Piece)
     */
    static EvalMove LEFT = new EvalMoveLeft();

    /**
     * Evalutation d'un mouvement à droite.
     *
     * @see Eval#eval_possibilities(Grid,Piece)
     */
    static EvalMove RIGHT = new EvalMoveRight();

    /**
     * Retourne un ordre à donner en fonction de l'évaluation des possibilités.
     *
     * @param piece
     *          Pièce courante.
     * @param grid
     *          Grille courante.
     * @return Ordre pour l'IA.
     */
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

    /**
     * Evaluation de l'état d'une colonne.
     *
     * @param piece
     *          Pièce courante.
     * @param grid
     *          Grille courante.
     * @return l'état de la grille.
     */
    private static GridState eval_column(Grid grid, Piece piece)
    {
        GridIA grid_buffer = new GridIA(grid);
        Piece piece_buffer = new Piece(piece);

        grid_buffer.brute_fall(piece_buffer);
        grid_buffer.check(piece_buffer.coordinates());

        return grid_buffer.eval();
    }

    /**
     * Retourne le meilleur état après un test de placement de pièce.
     *
     * @param current
     *          Etat courant.
     * @param candidate_state
     *          Etat à tester.
     * @param original
     *          Pièce courante.
     * @param candidate_piece
     *          Pièce à tester.
     * @param orders
     *          Ordre à donner pour tester.
     * @param rotation
     *          Rotation de la pièce.
     * @param key
     *          Touche assignée.
     * @return Le meilleur état entre le courant et le testé.
     */
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

    /**
     * Recherche du meilleur état en mouvement.
     *
     * @param grid
     *          Grille.
     * @param piece
     *          Pièce courante.
     * @param current
     *          Etat de la grille d'IA courante.
     * @param orders
     *          Ordre à donner.
     * @param rotation
     *          Rotation de la pièce.
     * @param e_move
     *          Evaluation d'un mouvement.
     * @param points_buffer
     *          Buffer de points.
     * @return Le meilleur état parmis tous ceux testés constamment.
     */
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

    /**
     * Evaluation d'un état.
     *
     * @param score
     *          Le score.
     * @param evaluation
     *          Evaluation.
     * @return 0
     */
    public static int eval_state(int score, int evaluation)
    {
        return 0;
    }
}

/**
 * Classe générique d'évaluation d'un déplacement.
 */
abstract class EvalMove
{
    /**
     * Touche à presser.
     *
     * @see EvalMove#Constructor(int)
     */
    public final int key;

    /**
     * Constructeur d'une évaluation d'un mouvement.
     *
     * @param k
     *          Touche pressée.
     */
    public EvalMove(int k)
    {
        key = k;
    }

    /**
     * Teste de la place dans une direction.
     *
     * @param piece
     *          Pièce à tester.
     * @param buffer
     *          Tableau de points.
     */
    public abstract void needed_space(Piece piece, Point[] buffer);

    /**
     * Déplacement d'une pièce dans une direction.
     *
     * @param piece
     *          Pièce à déplacer.
     */
    public abstract void move(Piece piece);
}

/**
 * Classe d'évaluation d'un déplacement vers la gauche.
 */
class EvalMoveLeft extends EvalMove
{
    public EvalMoveLeft()
    {
        super(KeySender.LEFT);
    }

    /**
     * @see EvalMove#needed_space(Piece,Point[])
     */
    public void needed_space(Piece piece, Point[] buffer)
    {
        piece.needed_space_left(buffer);
    }

    /**
     * @see EvalMove#move(Piece)
     */
    public void move(Piece piece)
    {
        piece.left();
    }
}

/**
 * Classe d'évaluation d'un déplacement vers la droite.
 */
class EvalMoveRight extends EvalMove
{
    public EvalMoveRight()
    {
        super(KeySender.RIGHT);
    }

    /**
     * @see EvalMove#needed_space(Piece,Point[])
     */
    public void needed_space(Piece piece, Point[] buffer)
    {
        piece.needed_space_right(buffer);
    }

    /**
     * @see EvalMove#move(Piece)
     */
    public void move(Piece piece)
    {
        piece.right();
    }
}
