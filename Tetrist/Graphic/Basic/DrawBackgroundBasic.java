package Graphic.Basic;

import java.awt.Color;
import java.awt.Graphics;

import Graphic.Abstract.DrawBackground;

public class DrawBackgroundBasic extends DrawBackground
{
    public DrawBackgroundBasic(int w, int h)
    {
        super(w, h);
    }

    public void paint(Graphics g)
    {
        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);
    }
}
