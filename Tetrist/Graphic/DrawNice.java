package Graphic;

import java.awt.Graphics;

import Component.Point;
import Component.Game;

import Graphic.Nice.DrawNiceGrid;

public class DrawNice extends Draw
{
    public DrawNice(Game g)
    {
        super(g, new DrawNiceGrid(g, new Point(0,0), 24));
    }
}
