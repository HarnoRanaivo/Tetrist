package Graphic;

import java.util.Vector;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Dimension;

import Component.Game;
import Graphic.Basic.DrawPart;
import Graphic.Basic.DrawGrid;
import Graphic.Basic.DrawNext;
import Graphic.Basic.DrawBlock;
import Graphic.Basic.DrawInfos;

import Graphic.DrawBasic;
import Graphic.DrawNice;

public abstract class Draw extends JPanel
{
    protected final Game game;
    protected final DrawGrid draw_grid;
    protected final DrawNext draw_next;
    protected final DrawBlock draw_block;
    protected final DrawInfos draw_infos;
    protected final DrawPart[] parts;

    public Draw(Game g, DrawGrid dg, DrawNext dn, DrawBlock db, DrawInfos di)
    {
        super();
        game = g;

        draw_grid = dg;
        draw_next = dn;
        draw_block = db;
        draw_infos = di;

        parts = new DrawPart[] { dg, dn, di };

        set_size();
    }

    public void paint(Graphics g)
    {
        super.paint(g);

        draw_grid.paint(g);
        draw_next.paint(g);
        draw_infos.paint(g);
    }

    public void refresh()
    {
        game.refresh();
        repaint(getVisibleRect());
    }

    private void set_size()
    {
        int width = 0;
        int height = 0;

        for (DrawPart part : parts)
        {
            int part_x = part.offset_x();
            int part_y = part.offset_y();
            int part_width = part.width();
            int part_height = part.height();

            if (part_x < width)
            {
                if (part_x + part_width > width)
                    width += part_width - (width - part_x);
            }
            else
                width += part_width + (part_x - width);

            if (part_y < height)
            {
                if (part_y + part_height > height)
                    height += part_height - (height - part_y);
            }
            else
                height += part_height + (part_y - height);
        }

        setPreferredSize(new Dimension(width, height));
    }

    public static Draw factory(Game g)
    {
        Draw draw = null;
        try
        {
            draw = new DrawNice(g);
        }
        catch (Exception e)
        {
            draw = new DrawBasic(g);
        }

        return draw;
    }
}
