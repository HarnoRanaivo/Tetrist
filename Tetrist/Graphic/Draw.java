package Graphic;

import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Dimension;

import Component.Game;
import Graphic.Basic.DrawGrid;
import Graphic.Basic.DrawNext;
import Graphic.Basic.DrawBlock;

public abstract class Draw extends JPanel
{
    protected final Game game;
    protected final DrawGrid draw_grid;
    protected final DrawNext draw_next;
    protected final DrawBlock draw_block;

    public Draw(Game g, DrawGrid dg, DrawNext dn, DrawBlock db)
    {
        super();
        game = g;
        draw_grid = dg;
        draw_next = dn;
        draw_block = db;
        setPreferredSize(new Dimension(400, g.grid().height() * 24));
    }

    public void paint(Graphics g)
    {
        super.paint(g);

        draw_grid.paint(g);
        draw_next.paint(g);
    }

    public void refresh()
    {
        game.refresh();
        repaint(getVisibleRect());
    }
}
