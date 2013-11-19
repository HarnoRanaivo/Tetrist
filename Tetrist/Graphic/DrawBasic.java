package Graphic;

import java.awt.Graphics;

import Component.Point;
import Component.Game;
import Graphic.Basic.DrawGrid;

public class DrawBasic extends Draw
{
    public DrawBasic(Game g)
    {
        super(g, new DrawGrid(g, new Point(0, 0), 24));
    }
}
