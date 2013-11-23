package Graphic;

import java.awt.Graphics;
import java.awt.Color;

import Component.Point;
import Component.Game;

import Graphic.Basic.DrawGrid;
import Graphic.Basic.DrawBlock;
import Graphic.Basic.DrawNext;
import Graphic.Basic.DrawInfos;
import Graphic.Nice.DrawNiceInfos;
import Graphic.Nice.DrawNiceGrid;
import Graphic.Nice.DrawNiceBlock;

public class DrawNice extends Draw
{
    public DrawNice(Game g, DrawGrid dg, DrawNext dn, DrawBlock db) throws Exception
    {
        super(g,
            dg,
            dn,
            db,
            new DrawNiceInfos(g,
                new Point(dn.offset_x(), 20 + dn.height() + dn.offset_y() + db.block_size())
            )
        );
    }

    public DrawNice(Game g, DrawGrid dg, DrawBlock db) throws Exception
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

    public DrawNice(Game g, DrawBlock db) throws Exception
    {
        this(g,
            new DrawNiceGrid(g,
                new Point(0, 0),
                db
            ),
            db
        );

    }

    public DrawNice(Game g) throws Exception
    {
        this(g,
            new DrawNiceBlock(24,
                new Color[] {
                    /* I : rouge. */
                    new Color(240, 40, 40),
                    /* J : blanc. */
                    new Color(205, 205, 205),
                    /* T : brun. */
                    new Color(180, 140, 70),
                    /* L : magenta. */
                    new Color(230, 85, 155),
                    /* Z : cyan. */
                    new Color(50, 200, 220),
                    /* O : bleu. */
                    new Color(40, 90, 240),
                    /* S : vert. */
                    new Color(50, 190, 20)
                }
            )
        );
    }
}
