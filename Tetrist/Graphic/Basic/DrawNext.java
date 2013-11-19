package Graphic.Basic;

import java.awt.Graphics;

import Component.Point;
import Component.Game;

import Graphic.Basic.DrawBlock;

public class DrawNext extends DrawPart
{
    protected DrawBlock draw_block;

    public DrawNext(Game g, Point offset, DrawBlock db)
    {
        super(g, offset);
        draw_block = db;
    }

    public void paint(Graphics g)
    {
    }
}
