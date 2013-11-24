package Graphic.Nice;

import java.awt.Color;
import java.awt.Graphics;

import Graphic.Abstract.DrawBackground;

public class DrawBackgroundNice extends DrawBackground
{
    public DrawBackgroundNice(int w, int h)
    {
        super(w, h);
    }

    public void paint(Graphics g)
    {
        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);
    }
}
