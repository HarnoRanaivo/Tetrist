package Graphic;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Graphics;

import Component.Game;
import Component.Grid;
import Component.Piece;

public class DrawImage extends Draw
{
    protected static final String block_img = "Pictures/block-overlay_";
    protected static final String img_postfix  = ".png";
    protected static final String background_img = "Pictures/background_";
    protected static final Color[] nice_colors =
    {
        /* I : rouge. */
        new Color(255, 95, 95),
        /* J : blanc. */
        new Color(215, 215, 215),
        /* T : brun. */
        new Color(180, 140, 70),
        /* L : magenta. */
        new Color(250, 140, 195),
        /* Z : cyan. */
        new Color(145, 210, 255),
        /* O : bleu. */
        new Color(105, 175, 255),
        /* S : vert. */
        new Color(125, 240, 55)
    };

    private Image block;
    private Image background;

    protected static final Color black1 = new Color(0, 0, 0);
    protected static final Color black2 = new Color(0, 0, 0);

    public DrawImage(Game g)
    {
        super(g);
        init_block();
    }

    private void init_block()
    {
        block = null;
        background = null;
        try
        {
            block = ImageIO.read(new File(block_img + block_size + img_postfix));
            background = ImageIO.read(new File(background_img + block_size + img_postfix));
        }
        catch(IOException ie)
        {
            System.err.println(ie.getMessage()); }
    }

    protected void paint_block(Graphics g, int x, int y, int value)
    {
        if (value != Grid.empty_block)
            g.drawImage(block, x, y, color_of_int(value), null);
    }

    protected Color bg_color(int x)
    {
        return (x / block_size % 2 == 0) ? black1 : black2;
    }

    protected static Color color_of_int(int id)
    {
        if (id < 0 || id >= Piece.cardinal)
            return Color.black;
        else
            return nice_colors[id];
    }

    protected void paint_background(Graphics g)
    {
        g.drawImage(background, 0, 0, Color.black, null);
    }

    public void paint_game(Graphics g)
    {
        paint_background(g);
        paint_grid(g);
        paint_piece(g);
    }
}
