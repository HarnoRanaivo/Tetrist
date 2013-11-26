package Graphic.Basic;

import java.awt.Graphics;

import Component.Game;
import Component.Point;
import Graphic.Abstract.DrawGrid;
import Graphic.Abstract.DrawBlock;

public class DrawGridBasic extends DrawGrid
{
    public DrawGridBasic(Game g, Point offset, DrawBlock db)
    {
        super(g, offset, db);
    }

    public void paint(Graphics g)
    {
        paint_grid(g);
        paint_piece(g);
    }
}
