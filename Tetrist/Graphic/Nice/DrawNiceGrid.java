package Graphic.Nice;

import Graphic.Basic.DrawGrid;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics;

import Component.Game;
import Component.Grid;
import Component.Point;

public class DrawNiceGrid extends DrawGrid
{
    protected static final String block_img = "Pictures/block-overlay_";
    protected static final String img_postfix = ".png";
    protected static final String background_img = "Pictures/background_";
    protected Image block;
    protected Image background;

    public DrawNiceGrid(Game g, Point offset, int block)
    {
        super(g, offset, block,
            new Color[] {
                /* I : rouge. */
                new Color(240, 40, 40),
                /* J : blanc. */
                new Color(205, 205, 205),
                /* T : brun. */
                new Color(180, 140, 70),
                /* L : magenta. */
                new Color(230, 85, 155),
                /* Z : cyan. */
                new Color(50, 200, 220),
                /* O : bleu. */
                new Color(40, 90, 240),
                /* S : vert. */
                new Color(50, 190, 20)
            }
        );

        init_block();
    }

    private void init_block()
    {
        block = null;
        background = null;
        try
        {
            block = ImageIO.read(new File(block_img + block_size + img_postfix));
            background = ImageIO.read(new File(background_img + block_size + img_postfix));
        }
        catch(IOException ie)
        {
            System.err.println(ie.getMessage()); }
    }

    protected void paint_block(Graphics g, int x, int y, int value)
    {
        if (value != Grid.empty_block)
            g.drawImage(block, x, y, color_of_int(value), null);
    }

    protected void paint_background(Graphics g)
    {
        g.drawImage(background, offset_x, offset_y, Color.black, null);
    }

    public void paint(Graphics g)
    {
        paint_background(g);
        paint_grid(g);
        paint_piece(g);
    }
}
