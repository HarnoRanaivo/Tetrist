package Graphic;

import java.awt.Graphics;
import java.awt.Color;

import Component.Point;
import Component.Game;

import Graphic.Basic.DrawBlock;
import Graphic.Basic.DrawGrid;

public class DrawBasic extends Draw
{
    public DrawBasic(Game g)
    {
        super(g,
            new DrawGrid(g,
                new Point(0, 0),
                new DrawBlock(24,
                    new Color[] { Color.red, Color.white, Color.orange,
                        Color.pink, Color.cyan, Color.blue, Color.green
                    }
                )
            )
        );
    }
}
