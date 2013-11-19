package Graphic.Basic;

import java.awt.Graphics;

import Component.Game;
import Component.Point;

public abstract class DrawPart
{
    protected final Game game;
    protected final int offset_x;
    protected final int offset_y;

    public DrawPart(Game g, Point offset)
    {
        game = g;
        offset_x = offset.abcissa();
        offset_y = offset.ordinate();
    }

    public abstract void paint(Graphics g);
}
