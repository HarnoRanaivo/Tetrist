package Graphic.Nice;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics;

import Component.Game;
import Component.Grid;
import Component.Point;

import Graphic.Basic.DrawGrid;
import Graphic.Basic.DrawBlock;

public class DrawNiceGrid extends DrawGrid
{
    protected static final String block_img = "Pictures/block-overlay_";
    protected static final String img_postfix = ".png";
    protected static final String background_img = "Pictures/background_";
    protected Image block;
    protected Image background;

    public DrawNiceGrid(Game g, Point offset, DrawNiceBlock db)
    {
        super(g, offset, db);
        init_block();
        db.set_block(block);
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
