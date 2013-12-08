package Graphic.Basic;

import java.awt.Color;
import java.awt.Graphics;

import Component.Point;
import Component.Game;
import Graphic.Abstract.DrawInfos;

/**
 * Classe de dessins des informations sans font.
 */
public class DrawInfosBasic extends DrawInfos
{
    /**
     * Constructeur d'un dessin d'informations sans font.
     *
     * @param g
     *          Variable du jeu Tetrist.
     * @param offset
     *          Décalage exprimée à l'aide d'un point.
     */
    public DrawInfosBasic(Game g, Point offset)
    {
        super(g, offset);
    }

    /**
     * Dessin complet des informations sans font.
     *
     * @param g
     *          Variable employée pour le dessin dans une fenêtre.
     */
    public void paint(Graphics g)
    {
        g.setColor(Color.yellow);
        paint_infos(g);
    }
}
