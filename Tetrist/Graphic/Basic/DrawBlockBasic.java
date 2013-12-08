package Graphic.Basic;

import java.awt.Color;
import java.awt.Graphics;

import Graphic.Abstract.DrawBlock;

/**
 * Classe d'un dessin de bloc sans image.
 */
public class DrawBlockBasic extends DrawBlock
{
    /**
     * Constructeur d'un dessin de bloc sans image.
     *
     * @param size
     *          Taille d'un bloc.
     * @param c
     *          Palette de couleurs d'un bloc.
     */
    public DrawBlockBasic(int size, Color[] c)
    {
        super(size, c);
    }

    /**
     * Dessin d'un bloc sans image.
     *
     * @param g
     *          Variable employée pour le dessin dans une fenêtre.
     * @param x
     *          Abscisse du bloc.
     * @param y
     *          Ordonnée du bloc.
     * @param value
     *          Code de la pièce. (ou du vide)
     */
    public void paint_block(Graphics g, int x, int y, int value)
    {
        g.setColor(color_of_int(value));
        g.fillRect(x, y, block_size, block_size);
    }

}
