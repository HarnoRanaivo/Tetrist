package Graphic;

import java.awt.Graphics;
import java.awt.Color;

import Component.Point;
import Component.Game;

import Graphic.Nice.DrawNiceGrid;
import Graphic.Nice.DrawNiceBlock;

public class DrawNice extends Draw
{
    public DrawNice(Game g)
    {
        super(g,
            new DrawNiceGrid(g,
                new Point(0, 0),
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
            )
        );
    }
}
