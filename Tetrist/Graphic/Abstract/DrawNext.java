package Graphic.Abstract;

import java.awt.Graphics;

import Component.Point;
import Component.Game;
import Component.Piece;

import Graphic.Abstract.DrawBlock;

public class DrawNext extends DrawPart
{
    protected final int block_size;
    protected final DrawBlock draw_block;
    protected final int next_height;
    protected final int next_width;

    public DrawNext(Game g, Point offset, DrawBlock db)
    {
        super(g, offset, db.block_size() * 4, db.block_size() * 2);
        draw_block = db;
        next_height = 2;
        next_width = 4;
        block_size = db.block_size();
    }

    public void paint(Graphics g)
    {
        Piece next = game.next_piece();
        Point[] coordinates = next.face()[3];
        int id = next.id();

        for (Point point : coordinates)
        {
            int x = point.abcissa() * block_size + offset_x;
            int y = ((-1) * point.ordinate()) * block_size + offset_y;
            draw_block.paint_block(g, x, y, id);
        }
    }
}
