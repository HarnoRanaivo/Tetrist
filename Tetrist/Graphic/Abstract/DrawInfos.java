package Graphic.Abstract;

import java.awt.Graphics;
import java.awt.Color;

import Component.Point;
import Component.Game;

public abstract class DrawInfos extends DrawPart
{
    protected static final int line_shift = 10;

    protected DrawInfos(Game g, Point offset, int x, int y)
    {
        super(g, offset, x, y);
    }

    public DrawInfos(Game g, Point offset)
    {
        this(g, offset, 200, 200);
    }

    public void paint_infos(Graphics g)
    {
        g.drawString("Level: " + game.level(), offset_x,  0 + offset_y);
        g.drawString("Score: " + game.score(), offset_x, line_shift + offset_y);
        g.drawString("Lines: " + game.lines_destroyed(), offset_x, 2 * line_shift + offset_y);
    }

    public abstract void paint(Graphics g);
}
