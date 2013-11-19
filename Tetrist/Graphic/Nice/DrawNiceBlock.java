package Graphic.Nice;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics;

import Component.Grid;
import Graphic.Basic.DrawBlock;

public class DrawNiceBlock extends DrawBlock
{
    protected Image block;
    protected static final String block_img = "Pictures/block-overlay_";
    protected static final String img_postfix = ".png";

    public DrawNiceBlock(int block, Color[] c)
    {
        super(block, c);
        init_image();
    }

    private void init_image()
    {
        block = null;
        String block_path = block_img + block_size + img_postfix;

        try
        {
            block = ImageIO.read(new File(block_path));
        }
        catch (IOException ie)
        {
            System.err.println("Block image : " + ie.getMessage());
        }
    }

    public void paint_block(Graphics g, int x, int y, int value)
    {
        if (value != Grid.empty_block)
            g.drawImage(block, x, y, color_of_int(value), null);
    }
}
