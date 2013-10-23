import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Dimension;

public class Draw extends JPanel {

    private Matrice matrice;

    public void paint(Graphics g)
    { int x,y;
        super.paint(g);
	for (y=0;y<20;y++) {
	    for (x=0;x<10;x++) {
		if (matrice.get(x,y)) {
		    g.setColor(Color.red);
		} else {
		    g.setColor(Color.black);
		}
		g.fillRect(x<<4, y<<4, 16, 16);
	    }
	}
    }

    public void refresh()
    {
	matrice.refresh();
	repaint(getVisibleRect());
    }

    public Draw(Matrice m)
    {
	super();
	setPreferredSize(new Dimension(160, 320));
	matrice = m;
    }
}
