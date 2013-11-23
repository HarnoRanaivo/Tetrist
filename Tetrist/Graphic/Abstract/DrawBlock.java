package Graphic.Abstract;

import java.awt.Graphics;
import java.awt.Color;

import Component.Piece;

public abstract class DrawBlock
{
    protected final int block_size;
    protected final Color[] colors;

    public DrawBlock(int size, Color[] c)
    {
        block_size = size;
        colors = c;
    }

    public int block_size()
    {
        return block_size;
    }

    protected Color color_of_int(int value)
    {
        if (value < 0 || value >= Piece.cardinal)
            return Color.black;
        else
            return colors[value];
    }

    public abstract void paint_block(Graphics g, int x, int y, int value);
}
