package Graphic.Basic;

import java.awt.Graphics;

import Component.Point;
import Component.Game;
import Component.Piece;

import Graphic.Basic.DrawBlock;

public class DrawNext extends DrawPart
{
    protected int block_size;
    protected DrawBlock draw_block;

    public DrawNext(Game g, Point offset, DrawBlock db)
    {
        super(g, offset);
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
            int y = (2 - 1 - point.ordinate()) * block_size + offset_y;
            draw_block.paint_block(g, x, y, id);
        }
    }
}
