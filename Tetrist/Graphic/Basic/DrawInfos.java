package Graphic.Basic;

import java.awt.Graphics;

import Component.Point;
import Component.Game;

public class DrawInfos extends DrawPart
{
    public DrawInfos(Game g, Point offset)
    {
        super(g, offset, 100, 50);
    }

    public void paint(Graphics g)
    {
        g.drawString("Level: " + game.level(), offset_x,  0 + offset_y);
        g.drawString("Score: " + game.score(), offset_x, 10 + offset_y);
        g.drawString("Lines: " + game.lines_destroyed(), offset_x, 20 + offset_y);
    }
}
