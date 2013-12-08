package Graphic.Abstract;

import java.awt.Graphics;
import java.awt.Color;

import Component.Point;
import Component.Game;

/**
 * Classe de dessins génériques d'informations.
 */
public abstract class DrawInfos extends DrawPart
{
    /**
     * Variable utilisée pour la séparation des informations.
     *
     * @see DrawInfos#paint_infos(Graphics)
     */
    protected static final int line_shift = 10;

    /**
     * Constructeur d'un dessin d'informations du jeu.
     *
     * @param g
     *          Variable de jeu Tetrist.
     * @param offset
     *          Décalage exprimé à l'aide d'un point.
     * @param x
     *          Abscisse des informations.
     * @param y
     *          Ordonnée des informations.
     */
    protected DrawInfos(Game g, Point offset, int x, int y)
    {
        super(g, offset, x, y);
    }

/**
     * Constructeur d'un dessin d'informations du jeu avec une origine prédéfinie (200;200).
     *
     * @param g
     *          Variable de jeu Tetrist.
     * @param offset
     *          Décalage exprimé à l'aide d'un point.
     */
    public DrawInfos(Game g, Point offset)
    {
        this(g, offset, 200, 200);
    }

    /**
     * Dessin d'informations.
     *
     * @param g
     *          Variable employée pour le dessin dans une fenêtre.
     */
    public void paint_infos(Graphics g)
    {
        g.drawString("Level: " + game.level(), offset_x,  0 + offset_y);
        g.drawString("Score: " + game.score(), offset_x, line_shift + offset_y);
        g.drawString("Lines: " + game.lines_destroyed(), offset_x, 2 * line_shift + offset_y);
    }

    /**
     * Dessin d'informations complet.
     *
     * @param g
     *          Variable employée pour le dessin dans une fenêtre.
     */
    public abstract void paint(Graphics g);
}
