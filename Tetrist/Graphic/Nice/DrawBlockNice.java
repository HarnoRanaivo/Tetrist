package Graphic.Nice;

import java.awt.Toolkit;
import java.net.URL;

import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics;

import Component.Grid;
import Graphic.Abstract.DrawBlock;

public class DrawBlockNice extends DrawBlock
{
    protected static final String block_img = "/Pictures/block-overlay_";
    protected static final String img_postfix = ".png";

    protected final Image block;

    public DrawBlockNice(int size, Color[] c)
    {
        super(size, c);
        block = init_image();
    }

    protected Image init_image()
    {
        Image img = null;

        String block_path = block_img + block_size + img_postfix;
        URL block_url = getClass().getResource(block_path);
        img = Toolkit.getDefaultToolkit().getImage(block_url);

        return img;
    }

    public void paint_block(Graphics g, int x, int y, int value)
    {
        if (value != Grid.empty_block)
            g.drawImage(block, x, y, color_of_int(value), null);
    }
}
