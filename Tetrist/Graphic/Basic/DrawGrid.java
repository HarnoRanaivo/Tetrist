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
    protected final DrawBlock draw_block;

    public DrawGrid(Game g, Point offset, DrawBlock db)
    {
        super(g, offset);
        block_size = db.block_size();
        draw_block = db;
        width = game.grid().width();
        height = game.grid().height();
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

                draw_block.paint_block(g, x, y, grid.get(i, j));
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
            draw_block.paint_block(g, x, y, id);
        }
    }
}
