package Basic;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Dimension;

public class Draw extends JPanel
{
    protected Game game;
    protected int scale = 24;
    static final Color[] rainbow =
        {
            Color.red, Color.orange, Color.yellow, Color.green, Color.blue,
            Color.cyan, Color.pink
        };

    protected static Color int_to_color(int x)
    {
        if (x < 0 || x > 6)
            return Color.black;
        else
            return rainbow[x];
    }

    public void paint(Graphics g)
    {
        super.paint(g);

        paint_grid(g);
        paint_piece(g);
    }

    protected void paint_grid(Graphics g)
    {
        for (int y = 0; y < Grid.height; y++)
            for (int x = 0; x < Grid.width; x++)
            {
                g.setColor(int_to_color(game.grid.get(x, y)));
                g.fillRect(x * scale, (game.grid.height - 1 - y) * scale, scale, scale);
            }
    }

    protected void paint_piece(Graphics g)
    {
        g.setColor(int_to_color(game.current.id));
        for (int i = 0; i < 4; i++)
        {
            int x = game.current.abcissae[i];
            int y = game.current.ordinates[i];
            g.fillRect(x * scale, (game.grid.height - 1 - y) * scale, scale, scale);
        }
    }

    public void refresh()
    {
        game.refresh();
        repaint(getVisibleRect());
    }

    public Draw(Game g)
    {
        super();
        setPreferredSize(new Dimension(Grid.width * scale, Grid.height * scale));
        game = g;
    }
}
