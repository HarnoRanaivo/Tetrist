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
    protected static final int block_size = 24;
    protected static final Color[] ugly_colors =
    {
        Color.red, Color.white, Color.orange, Color.pink, Color.cyan,
        Color.blue, Color.green
    };

    protected final Game game;
    protected final int grid_width;
    protected final int grid_height;

    protected static Color color_of_int(int x)
    {
        if (x < 0 || x >= Piece.cardinal)
            return Color.black;
        else
            return ugly_colors[x];
    }

    public void paint(Graphics g)
    {
        super.paint(g);

        paint_grid(g);
        paint_piece(g);
    }

    protected void paint_grid(Graphics g)
    {
        Grid grid = game.grid();

        for (int i = 0; i < grid_width; i++)
            for (int j = 0; j < grid_height; j++)
            {
                int x = i * block_size;
                int y = (grid_height - 1 - j) * block_size;

                paint_block(g, x, y, grid.get(i, j));
            }
    }

    protected void paint_piece(Graphics g)
    {
        Piece current = game.current_piece();
        int id = current.id();

        for (Point point : current.coordinates())
        {
            int x = point.abcissa() * block_size;
            int y = (grid_height -1 - point.ordinate()) * block_size;
            paint_block(g, x, y, id);
        }
    }

    protected void paint_block(Graphics g, int x, int y, int value)
    {
        g.setColor(color_of_int(value));
        g.fillRect(x, y, block_size, block_size);
    }

    public void refresh()
    {
        game.refresh();
        repaint(getVisibleRect());
    }

    public Draw(Game g)
    {
        super();
        game = g;
        grid_width = g.grid().width();
        grid_height = g.grid().height();
        setPreferredSize(new Dimension(grid_width * block_size, grid_height * block_size));
    }
}
