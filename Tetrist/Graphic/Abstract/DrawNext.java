package Graphic.Abstract;

import java.awt.Graphics;

import Component.Point;
import Component.Game;
import Component.Piece;

import Graphic.Abstract.DrawBlock;

public class DrawNext extends DrawPart
{
    protected static final int next_height = 2;
    protected static final int next_width = 4;
    protected final int block_size;
    protected final DrawBlock draw_block;

    public DrawNext(Game g, Point offset, DrawBlock db)
    {
        super(g, offset, db.block_size() * next_width, db.block_size() * next_height);
        draw_block = db;
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
