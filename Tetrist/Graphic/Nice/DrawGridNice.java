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
    protected static final Color bg_color = new Color(0, 0, 0, 150);
    protected static final Color border_color = new Color(200, 200, 200);

    public DrawGridNice(Game g, Point offset, DrawBlock db)
    {
        super(g, offset, db, 2);
    }

    protected void paint_border(Graphics g)
    {
        g.setColor(border_color);
        g.fillRect(offset_x, offset_y, width, border);
        g.fillRect(offset_x, offset_y + height - border, width, border);
        g.fillRect(offset_x, offset_y, border, height);
        g.fillRect(offset_x + width - border, offset_y, border, height);
    }

    protected void paint_background(Graphics g)
    {
        g.setColor(bg_color);
        g.fillRect(offset_x, offset_y, width, height);
    }

    public void paint(Graphics g)
    {
        paint_background(g);
        paint_border(g);
        paint_grid(g);
        paint_piece(g);
    }
}
