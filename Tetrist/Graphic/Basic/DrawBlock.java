package Graphic.Basic;

import java.awt.Graphics;
import java.awt.Color;

import Component.Piece;

public class DrawBlock
{
    protected final int block_size;
    protected final Color[] colors;

    public DrawBlock(int block, Color[] c)
    {
        block_size = block;
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

    public void paint_block(Graphics g, int x, int y, int value)
    {
        g.setColor(color_of_int(value));
        g.fillRect(x, y, block_size, block_size);
    }
}
