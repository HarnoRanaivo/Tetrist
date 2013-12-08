package Graphic.Nice;

import java.awt.Toolkit;
import java.net.URL;

import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics;

import Component.Game;
import Component.Grid;
import Component.Point;

import Graphic.Abstract.DrawGrid;
import Graphic.Abstract.DrawBlock;

/**
 * Classe de dessin d'une grille avec une image.
 */
public class DrawGridNice extends DrawGrid
{
	/**
	 * Couleur du fond de la grille.
	 * 
	 * @see DrawGridNice#paint_background(Graphics)
	 */
    protected static final Color bg_color = new Color(0, 0, 0, 150);
    
    /**
     * Couleur de la bordure de la grille.
     * 
     * @see DrawGridNice#paint_border(Graphics)
     */
    protected static final Color border_color = new Color(200, 200, 200);

	/**
	 * Constructeur d'un dessin d'une grille avec une image.
	 * 
	 * @param g
	 * 			Variable du jeu Tetrist.
	 * @param offset
	 * 			Décalage à l'aide d'un point.
	 * @param db
	 * 			Dessin d'un bloc.
	 */
    public DrawGridNice(Game g, Point offset, DrawBlock db)
    {
        super(g, offset, db, 2);
    }

	/**
	 * Dessin de la bordure de la grille.
	 * 
	 * @param g
	 * 			Variable employée pour le dessin dans une fenêtre.
	 */
    protected void paint_border(Graphics g)
    {
        g.setColor(border_color);
        g.fillRect(offset_x, offset_y, width, border);
        g.fillRect(offset_x, offset_y + height - border, width, border);
        g.fillRect(offset_x, offset_y, border, height);
        g.fillRect(offset_x + width - border, offset_y, border, height);
    }

	/**
	 * Dessin de l'arrière-plan de la grille.
	 * 
	 * @param g
	 * 			Variable employée pour le dessin dans une fenêtre.
	 */
    protected void paint_background(Graphics g)
    {
        g.setColor(bg_color);
        g.fillRect(offset_x, offset_y, width, height);
    }

	/**
	 * Dessin de la grille complète.
	 * 
	 * @param g
	 * 			Variable employée pour le dessin dans une fenêtre.
	 */
    public void paint(Graphics g)
    {
        paint_background(g);
        paint_border(g);
        paint_grid(g);
        paint_piece(g);
    }
}
