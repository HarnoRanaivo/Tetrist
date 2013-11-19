package Graphic;

import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Dimension;

import Component.Game;
import Graphic.Basic.DrawGrid;
import Graphic.Basic.DrawNext;

public abstract class Draw extends JPanel
{
    protected final Game game;
    protected final DrawGrid draw_grid;
    // protected final DrawNext draw_next;

    public Draw(Game g, DrawGrid dg)
    {
        super();
        game = g;
        draw_grid = dg;
        setPreferredSize(new Dimension(g.grid().width() * 24, g.grid().height() * 24));
    }

    public void paint(Graphics g)
    {
        super.paint(g);

        draw_grid.paint(g);
        // draw_next.paint(g);
    }

    public void refresh()
    {
        game.refresh();
        repaint(getVisibleRect());
    }
}
