package Graphic.Basic;

import java.awt.Color;
import java.awt.Graphics;

import Graphic.Abstract.DrawBackground;

/**
 * Classe d'un dessin d'arrière-plan sans image.
 */
public class DrawBackgroundBasic extends DrawBackground
{
    /**
     * Constructeur d'un arrière-plan sans image.
     *
     * @param w
     *          Largeur de l'arrière-plan.
     * @param h
     *          Hauteur de l'arrière-plan.
     */
    public DrawBackgroundBasic(int w, int h)
    {
        super(w, h);
    }

    /**
     * Dessin d'un arrière-plan sans image.
     *
     * @param g
     *          Variable employée pour le dessin dans une fenêtre.
     */
    public void paint(Graphics g)
    {
        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);
    }
}
