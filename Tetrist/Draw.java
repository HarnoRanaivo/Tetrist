import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Dimension;

public class Draw extends JPanel
{
    protected Game game;
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
        for (int y = 0; y < 20; y++)
            for (int x = 0; x < 10; x++)
            {
                g.setColor(int_to_color(game.grid.get(x, y)));
                g.fillRect(x << 4, (game.grid.height - 1 - y) << 4, 16, 16);
            }

        g.setColor(int_to_color(game.current.id));
        for (int i = 0; i < 4; i++)
        {
            int x = game.current.abcissae[i];
            int y = game.current.ordinates[i];
            g.fillRect(x << 4, (game.grid.height - 1 - y) << 4, 16, 16);
        }
    }

    public void refresh()
    {
        game.grid.refresh();
        repaint(getVisibleRect());
    }

    public Draw(Game g)
    {
        super();
        setPreferredSize(new Dimension(160, 320));
        game = g;
    }
}
