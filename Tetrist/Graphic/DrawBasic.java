package Graphic;

import java.awt.Graphics;
import java.awt.Color;

import Component.Point;
import Component.Game;

import Graphic.Basic.DrawBlock;
import Graphic.Basic.DrawGrid;
import Graphic.Basic.DrawNext;

public class DrawBasic extends Draw
{
    public DrawBasic(Game g, DrawBlock db)
    {
        super(g,
            new DrawGrid(g,
                new Point(0, 0),
                db
            ),
            new DrawNext(g,
                new Point(250, 10),
                db
            ),
            db
        );
    }

    public DrawBasic(Game g)
    {
        this(g,
            new DrawBlock(24,
                new Color[] { Color.red, Color.white, Color.orange,
                    Color.pink, Color.cyan, Color.blue, Color.green
                }
            )
        );
    }
}
