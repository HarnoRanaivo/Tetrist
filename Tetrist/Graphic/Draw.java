package Graphic;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Dimension;

import Component.Point;
import Component.Piece;
import Component.Grid;
import Component.Game;

public class Draw extends JPanel
{
    protected final Game game;
    protected final DrawGrid draw_grid;

    public Draw(Game g)
    {
        super();
        game = g;
        draw_grid = new DrawGrid(game, new Point(0, 0), 24);
        setPreferredSize(new Dimension(grid_width * block_size, grid_height * block_size));
    }

    public void paint(Graphics g)
    {
        super.paint(g);

        draw_grid.paint(g);
    }

    public void refresh()
    {
        game.refresh();
        repaint(getVisibleRect());
    }

}
