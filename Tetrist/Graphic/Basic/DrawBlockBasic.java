package Graphic.Basic;

import java.awt.Color;
import java.awt.Graphics;

import Graphic.Abstract.DrawBlock;

public class DrawBlockBasic extends DrawBlock
{
    public DrawBlockBasic(int size, Color[] c)
    {
        super(size, c);
    }

    public void paint_block(Graphics g, int x, int y, int value)
    {
        g.setColor(color_of_int(value));
        g.fillRect(x, y, block_size, block_size);
    }

}
