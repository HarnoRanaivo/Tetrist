package Graphic.Abstract;

import java.awt.Graphics;

import Component.Point;
import Component.Game;
import Component.Piece;

import Graphic.Abstract.DrawBlock;

/**
 * Classe pour le dessin de la pièce suivante.
 */
public class DrawNext extends DrawPart
{
	/**
	 * Hauteur du dessin de la prochaine pièce.
	 * 
	 * @see DrawNext#Constructor(Game,Point,DrawBlock)
	 */
    protected static final int next_height = 2;
    
    /**
	 * Largeur du dessin de la prochaine pièce.
	 * 
	 * @see DrawNext#Constructor(Game,Point,DrawBlock)
	 */
    protected static final int next_width = 4;
    
    /**
     * Taille d'un bloc pour la prochaine pièce.
     * 
     * @see DrawNext#Constructor(Game,Point,DrawBlock)
     * @see DrawNext#paint(Graphics)
     */
    protected final int block_size;
    
    /**
     * Dessin d'un bloc.
     * 
     * @see DrawNext#Constructor(Game,Point,DrawBlock)
     * @see DrawNext#paint(Graphics)
     */
    protected final DrawBlock draw_block;

	/**
	 * Constructeur d'un dessin d'une prochaine pièce.
	 * 
	 * @param g
	 * 			Variable du jeu Tetrist.
	 * @param offset
	 * 			Décalage exprimée à l'aide d'un point.
	 * @param db
	 * 			Dessin d'un bloc.
	 */
    public DrawNext(Game g, Point offset, DrawBlock db)
    {
        super(g, offset, db.block_size() * next_width, db.block_size() * next_height);
        draw_block = db;
        block_size = db.block_size();
    }

	/**
	 * Dessin de la pièce suivante.
	 * 
	 * @param g
	 * 			Variable employée pour le dessin dans une fenêtre.
	 */
    public void paint(Graphics g)
    {
        Piece next = game.next_piece();
        Point[] coordinates = next.face()[3];
        int id = next.id();

        for (Point point : coordinates)
        {
            int x = point.abcissa() * block_size + offset_x;
            int y = ((-1) * point.ordinate()) * block_size + offset_y;
            draw_block.paint_block(g, x, y, id);
        }
    }
}
