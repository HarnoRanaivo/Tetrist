package Graphic;

import java.awt.Graphics;
import java.awt.Color;

import Component.Point;
import Component.Game;

import Graphic.Abstract.DrawBlock;
import Graphic.Abstract.DrawGrid;
import Graphic.Abstract.DrawNext;
import Graphic.Abstract.DrawInfos;
import Graphic.Basic.DrawBlockBasic;
import Graphic.Basic.DrawGridBasic;
import Graphic.Basic.DrawInfosBasic;

public class DrawBasic extends Draw
{
    public DrawBasic(Game g, DrawGrid dg, DrawNext dn, DrawBlock db)
    {
        super(g,
            dg,
            dn,
            db,
            new DrawInfosBasic(g,
                new Point(dn.offset_x(), 20 + dn.height() + dn.offset_y() + db.block_size())
            )
        );
    }

    public DrawBasic(Game g, DrawGrid dg, DrawBlock db)
    {
        this(g,
            dg,
            new DrawNext(g,
                new Point(dg.width() + dg.offset_x() + 30, 40),
                db
            ),
            db
        );
    }

    public DrawBasic(Game g, DrawBlock db)
    {
        this(g,
            new DrawGridBasic(g,
                new Point(0, 0),
                db
            ),
            db
        );
    }

    public DrawBasic(Game g)
    {
        this(g,
            new DrawBlockBasic(24,
                new Color[] { Color.red, Color.white, Color.orange,
                    Color.pink, Color.cyan, Color.blue, Color.green
                }
            )
        );
    }
}
