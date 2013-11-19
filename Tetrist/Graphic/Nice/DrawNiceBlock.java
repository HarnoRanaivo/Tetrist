package Graphic.Nice;

import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics;

import Component.Grid;
import Graphic.Basic.DrawBlock;

public class DrawNiceBlock extends DrawBlock
{
    protected Image block;

    public DrawNiceBlock(int block, Color[] c)
    {
        super(block, c);
    }

    public void paint_block(Graphics g, int x, int y, int value)
    {
        if (value != Grid.empty_block)
            g.drawImage(block, x, y, color_of_int(value), null);
    }

    public void set_block(Image i)
    {
        block = i;
    }
}
