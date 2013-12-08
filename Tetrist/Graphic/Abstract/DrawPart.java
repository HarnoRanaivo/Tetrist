package Graphic.Abstract;

import java.awt.Graphics;

import Component.Game;
import Component.Point;

/**
 * Classe de dessins génériques de jeux.
 */
public abstract class DrawPart
{
    /**
     * Variable de jeu Tetrist.
     *
     * @see DrawPart#Constructor(Game,Point,int,int)
     */
    protected final Game game;

    /**
     * Décalage d'abscisse.
     *
     * @see DrawPart#Constructor(Game,Point,int,int)
     * @see DrawPart#offset_x()
     */
    protected final int offset_x;

    /**
     * Décalage d'ordonnée.
     *
     * @see DrawPart#Constructor(Game,Point,int,int)
     * @see DrawPart#offset_y()
     */
    protected final int offset_y;

    /**
     * Largeur du jeu.
     *
     * @see DrawPart#Constructor(Game,Point,int,int)
     * @see DrawPart#width()
     */
    protected final int width;

    /**
     * Hauteur du jeu.
     *
     * @see DrawPart#Constructor(Game,Point,int,int)
     * @see DrawPart#height()
     */
    protected final int height;

    /**
     * Constructeur générique d'un dessin d'une partie.
     *
     * @param g
     *          Variable de jeu Tetrist.
     * @param offset
     *          Décalage exprimé à l'aide d'un point.
     * @param w
     *          Largeur du jeu.
     * @param h
     *          Hauteur du jeu.
     */
    public DrawPart(Game g, Point offset, int w, int h)
    {
        game = g;
        offset_x = offset.abcissa();
        offset_y = offset.ordinate();
        width = w;
        height = h;
    }

    /**
     * Méthode de dessin d'une partie.
     *
     * @param g
     *          Variable employée pour le dessin dans une fenêtre.
     */
    public abstract void paint(Graphics g);

    /**
     * Retourne le décalage d'abscisse.
     *
     * @return offset d'x.
     */
    public int offset_x()
    {
        return offset_x;
    }

    /**
     * Retourne le décalage d'ordonnée.
     *
     * @return offset d'y.
     */
    public int offset_y()
    {
        return offset_y;
    }

    /**
     * Retourne la largeur du jeu.
     *
     * @return largeur du jeu.
     */
    public int width()
    {
        return width;
    }

    /**
     * Retourne la hauteur du jeu.
     *
     * @return hauteur du jeu.
     */
    public int height()
    {
        return height;
    }
}
