package Graphic.Basic;

import java.awt.Color;
import java.awt.Graphics;

import Component.Point;
import Component.Game;
import Graphic.Abstract.DrawInfos;

public class DrawInfosBasic extends DrawInfos
{

    public DrawInfosBasic(Game g, Point offset)
    {
        super(g, offset);
    }

    public void paint(Graphics g)
    {
        g.setColor(Color.yellow);
        paint_infos(g);
    }
}
