package Graphic.Nice;

import java.awt.Toolkit;
import java.net.URL;

import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics;

import Component.Game;
import Component.Grid;
import Component.Point;

import Graphic.Abstract.DrawGrid;
import Graphic.Abstract.DrawBlock;

public class DrawGridNice extends DrawGrid
{
    protected static final String background_img = "/Pictures/background_";
    protected static final String img_postfix = ".png";
    protected Image background;

    public DrawGridNice(Game g, Point offset, DrawBlock db)
    {
        super(g, offset, db);
        init_images();
    }

    private void init_images()
    {
        background = null;
        String bg_path = background_img + block_size + img_postfix;
        URL bg_url = getClass().getResource(bg_path);
        background = Toolkit.getDefaultToolkit().getImage(bg_url);
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
