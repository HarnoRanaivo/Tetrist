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
    public DrawGridNice(Game g, Point offset, DrawBlock db)
    {
        super(g, offset, db);
    }

    protected void paint_background(Graphics g)
    {
        g.setColor(Color.black);
        g.fillRect(offset_x, offset_y, width, height);
    }

    public void paint(Graphics g)
    {
        paint_background(g);
        paint_grid(g);
        paint_piece(g);
    }
}
