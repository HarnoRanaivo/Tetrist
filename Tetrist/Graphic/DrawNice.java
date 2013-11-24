package Graphic;

import java.awt.Graphics;
import java.awt.Color;

import Component.Point;
import Component.Game;

import Graphic.Abstract.DrawGrid;
import Graphic.Abstract.DrawBlock;
import Graphic.Abstract.DrawNext;
import Graphic.Abstract.DrawInfos;
import Graphic.Abstract.DrawBackground;
import Graphic.Nice.DrawInfosNice;
import Graphic.Nice.DrawGridNice;
import Graphic.Nice.DrawBlockNice;
import Graphic.Nice.DrawBackgroundNice;

public class DrawNice extends Draw
{
    public DrawNice(Game g, DrawGrid dg, DrawNext dn, DrawBlock db) throws Exception
    {
        super(g,
            dg,
            dn,
            db,
            new DrawInfosNice(g,
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
            new DrawGridNice(g,
                new Point(0, 0),
                db
            ),
            db
        );

    }

    public DrawNice(Game g) throws Exception
    {
        this(g,
            new DrawBlockNice(24,
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

    public DrawBackground create_draw_background(int w, int h)
    {
        return new DrawBackgroundNice(w, h);
    }
}
