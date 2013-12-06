package Component;

/**
 * Interface utilisée <b>uniquement</b> pour la création de pièces dans une partie de Tetrist.
 */
public interface PieceGenerator
{
    public Piece new_piece(int x, int y);
}
