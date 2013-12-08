package Graphic.Basic;

import java.awt.Graphics;

import Component.Game;
import Component.Point;
import Graphic.Abstract.DrawGrid;
import Graphic.Abstract.DrawBlock;

/**
 * Classe de dessin d'une grille sans image.
 */
public class DrawGridBasic extends DrawGrid
{
    /**
     * Constructeur d'un dessin d'une grille sans image.
     *
     * @param g
     *          Variable du jeu Tetrist.
     * @param offset
     *          Décalage à l'aide d'un point.
     * @param db
     *          Dessin d'un bloc.
     */
    public DrawGridBasic(Game g, Point offset, DrawBlock db)
    {
        super(g, offset, db);
    }

    /**
     * Dessin de la grille complète.
     *
     * @param g
     *          Variable employée pour le dessin dans une fenêtre.
     */
    public void paint(Graphics g)
    {
        paint_grid(g);
        paint_piece(g);
    }
}
