package Graphic;

import java.awt.Graphics;
import java.awt.Color;

import Component.Point;
import Component.Game;

import Graphic.Basic.DrawBlock;
import Graphic.Basic.DrawGrid;
import Graphic.Basic.DrawNext;
import Graphic.Basic.DrawInfos;

public class DrawBasic extends Draw
{
    public DrawBasic(Game g, DrawGrid dg, DrawNext dn, DrawBlock db)
    {
        super(g,
            dg,
            dn,
            db,
            new DrawInfos(g,
                new Point(dn.offset_x(), dn.height() + dn.offset_y() + db.block_size())
            )
        );
    }

    public DrawBasic(Game g, DrawGrid dg, DrawBlock db)
    {
        this(g,
            dg,
            new DrawNext(g,
                new Point(dg.width() + dg.offset_x() + 10, 20),
                db
            ),
            db
        );
    }

    public DrawBasic(Game g, DrawBlock db)
    {
        this(g,
            new DrawGrid(g,
                new Point(0, 0),
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
