package Graphic.Basic;

import java.awt.Graphics;

import Component.Game;
import Component.Point;

public abstract class DrawPart
{
    protected final Game game;
    protected final int offset_x;
    protected final int offset_y;
    protected final int width;
    protected final int height;

    public DrawPart(Game g, Point offset, int w, int h)
    {
        game = g;
        offset_x = offset.abcissa();
        offset_y = offset.ordinate();
        width = w;
        height = h;
    }

    public abstract void paint(Graphics g);

    public int offset_x()
    {
        return offset_x;
    }

    public int offset_y()
    {
        return offset_y;
    }

    public int width()
    {
        return width;
    }

    public int height()
    {
        return height;
    }
}
