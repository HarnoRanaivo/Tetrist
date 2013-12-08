package Graphic.Abstract;

import java.awt.Color;
import java.awt.Graphics;

import Component.Point;
import Component.Piece;
import Component.Grid;
import Component.Game;

/**
 * Classe de dessins génériques de grilles.
 */
public abstract class DrawGrid extends DrawPart
{
	/**
	 * Taille d'un bloc.
	 * 
	 * @see DrawGrid#Constructor(Game,Point,DrawBlock,int)
	 * @see DrawGrid#paint_grid(Graphics)
	 * @see DrawGrid#paint_piece(Graphics)
	 */
    protected final int block_size;
    
    /**
     * Dessin de bloc.
     * 
     * @see DrawGrid#Constructor(Game,Point,DrawBlock,int)
     * @see DrawGrid#Constructor(Game,Point,DrawBlock)
     * @see DrawGrid#paint_grid(Graphics)
	 * @see DrawGrid#paint_piece(Graphics)
	 */
    protected final DrawBlock draw_block;
    
    /**
     * Largeur de la grille.
     * 
     * @see DrawGrid#Constructor(Game,Point,DrawBlock,int)
	 * @see DrawGrid#paint_grid(Graphics)
	 */
    protected final int grid_width;
    
    /**
     * Hauteur de la grille.
     * 
     * @see DrawGrid#Constructor(Game,Point,DrawBlock,int)
	 * @see DrawGrid#paint_grid(Graphics)
	 * @see DrawGrid#paint_piece(Graphics)
	 */
    protected final int grid_height;
    
    /**
     * Bordure de la grille.
     * 
     * @see DrawGrid#Constructor(Game,Point,DrawBlock,int)
	 * @see DrawGrid#paint_grid(Graphics)
	 * @see DrawGrid#paint_piece(Graphics)
	 */
    protected final int border;

	/**
	 * Constructeur d'un dessin générique avec une bordure prédéfinie (0).
	 * 
	 * @param g
	 * 			Variable de jeu Tetrist.
	 * @param offset
	 * 			Décalage de la grille.
	 * @param db
	 * 			Dessin d'un bloc.
	 */
    public DrawGrid(Game g, Point offset, DrawBlock db)
    {
        this(g, offset, db, 0);
    }

	/**
	 * Constructeur d'un dessin générique.
	 * 
	 * @param g
	 * 			Variable de jeu Tetrist.
	 * @param offset
	 * 			Décalage de la grille.
	 * @param db
	 * 			Dessin d'un bloc.
	 * @param b
	 * 			Bordure de la grille.
	 */
    public DrawGrid(Game g, Point offset, DrawBlock db, int b)
    {
        super(
            g,
            offset,
            g.grid().width() * db.block_size() + b * 2,
            g.grid().height() * db.block_size() + b * 2
        );

        draw_block = db;
        grid_width = g.grid().width();
        grid_height = g.grid().height();
        border = b;
        block_size = db.block_size();

    }

	/**
	 * Dessin d'une grille complète.
	 * 
	 * @param g
	 * 			Variable employée pour le dessin dans une fenêtre.
	 */
    public abstract void paint(Graphics g);

	/**
	 * Dessin d'une grille.
	 * 
	 * @param g
	 * 			Variable employée pour le dessin dans une fenêtre.
	 */
    protected void paint_grid(Graphics g)
    {
        Grid grid = game.grid();

        for (int i = 0; i < grid_width; i++)
            for (int j = 0; j < grid_height; j++)
            {
                int x = i * block_size + offset_x + border;
                int y = (grid_height - 1 - j) * block_size + offset_y + border;

                draw_block.paint_block(g, x, y, grid.get(i, j));
            }
    }

	/**
	 * Dessin d'une pièce de la grille.
	 * 
	 * @param g
	 * 			Variable employée pour le dessin dans une fenêtre.
	 */
    protected void paint_piece(Graphics g)
    {
        Piece current = game.current_piece();
        int id = current.id();

        for (Point point : current.coordinates())
        {
            int x = point.abcissa() * block_size + offset_x + border;
            int y = (grid_height - 1 - point.ordinate()) * block_size + offset_y + border;
            draw_block.paint_block(g, x, y, id);
        }
    }
}
