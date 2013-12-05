package Graphic.Abstract;

import java.awt.Color;
import java.awt.Graphics;

import Component.Point;
import Component.Piece;
import Component.Grid;
import Component.Game;

public abstract class DrawGrid extends DrawPart
{
    protected final int block_size;
    protected final DrawBlock draw_block;
    protected final int grid_width;
    protected final int grid_height;
    protected final int border;

    public DrawGrid(Game g, Point offset, DrawBlock db)
    {
        this(g, offset, db, 0);
    }

    public DrawGrid(Game g, Point offset, DrawBlock db, int b)
    {
        super(
            g,
            offset,
            g.grid().width() * db.block_size() + b * 2,
            g.grid().height() * db.block_size() + b * 2
        );

        draw_block = db;
        grid_width = g.grid().width();
        grid_height = g.grid().height();
        border = b;
        block_size = db.block_size();

    }

    public abstract void paint(Graphics g);

    protected void paint_grid(Graphics g)
    {
        Grid grid = game.grid();

        for (int i = 0; i < grid_width; i++)
            for (int j = 0; j < grid_height; j++)
            {
                int x = i * block_size + offset_x + border;
                int y = (grid_height - 1 - j) * block_size + offset_y + border;

                draw_block.paint_block(g, x, y, grid.get(i, j));
            }
    }

    protected void paint_piece(Graphics g)
    {
        Piece current = game.current_piece();
        int id = current.id();

        for (Point point : current.coordinates())
        {
            int x = point.abcissa() * block_size + offset_x + border;
            int y = (grid_height - 1 - point.ordinate()) * block_size + offset_y + border;
            draw_block.paint_block(g, x, y, id);
        }
    }
}
