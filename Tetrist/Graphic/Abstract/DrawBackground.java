package Graphic.Abstract;

import java.awt.Graphics;

public abstract class DrawBackground
{
    protected final int width;
    protected final int height;

    public DrawBackground(int w, int h)
    {
        width = w;
        height = h;
    }

    public int width()
    {
        return width;
    }

    public int height()
    {
        return height;
    }

    public abstract void paint(Graphics g);
}
