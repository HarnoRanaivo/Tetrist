package Graphic.Basic;

import java.awt.Graphics;
import java.awt.Color;

import Component.Point;
import Component.Game;

public class DrawInfos extends DrawPart
{
    protected DrawInfos(Game g, Point offset, int x, int y)
    {
        super(g, offset, x, y);
    }

    public DrawInfos(Game g, Point offset)
    {
        this(g, offset, 200, 200);
    }

    public void paint(Graphics g)
    {
        g.setColor(Color.black);
        g.drawString("Level: " + game.level(), offset_x,  0 + offset_y);
        g.drawString("Score: " + game.score(), offset_x, 10 + offset_y);
        g.drawString("Lines: " + game.lines_destroyed(), offset_x, 20 + offset_y);
    }
}
