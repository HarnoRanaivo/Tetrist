package Graphic.Basic;

import java.awt.Color;
import java.awt.Graphics;

import Component.Point;
import Component.Piece;
import Component.Grid;
import Component.Game;

public class DrawGrid extends DrawPart
{
    protected final int width;
    protected final int height;
    protected final int block_size;
    protected final Color[] colors;

    public DrawGrid(Game g, Point offset, int block)
    {
        this(g, offset, block, new Color[] { Color.red, Color.white, Color.orange, Color.pink, Color.cyan, Color.blue, Color.green });
    }

    protected DrawGrid(Game g, Point offset, int block, Color[] c)
    {
        super(g, offset);
        block_size = block;
        width = game.grid().width();
        height = game.grid().height();
        colors = c;
    }

    protected Color color_of_int(int value)
    {
        if (value < 0 || value >= Piece.cardinal)
            return Color.black;
        else
            return colors[value];
    }

    public void paint(Graphics g)
    {
        paint_grid(g);
        paint_piece(g);
    }

    protected void paint_grid(Graphics g)
    {
        Grid grid = game.grid();

        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
            {
                int x = i * block_size + offset_x;
                int y = (height - 1 - j) * block_size + offset_y;

                paint_block(g, x, y, grid.get(i, j));
            }
    }

    protected void paint_piece(Graphics g)
    {
        Piece current = game.current_piece();
        int id = current.id();

        for (Point point : current.coordinates())
        {
            int x = point.abcissa() * block_size + offset_x;
            int y = (height - 1 - point.ordinate()) * block_size + offset_y;
            paint_block(g, x, y, id);
        }
    }

    protected void paint_block(Graphics g, int x, int y, int value)
    {
        g.setColor(color_of_int(value));
        g.fillRect(x, y, block_size, block_size);
    }
}
