package Graphic.Nice;

import java.net.URL;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Graphics;

import Graphic.Abstract.DrawBackground;

public class DrawBackgroundNice extends DrawBackground
{
    protected static final String background_path = "/Pictures/background.png";
    protected final Image background;

    public DrawBackgroundNice(int w, int h)
    {
        super(w, h);
        background = init_image();
    }

    public void paint(Graphics g)
    {
        g.drawImage(background, 0, 0, null);
    }

    protected Image init_image()
    {
        Image img = null;
        URL bg_url = getClass().getResource(background_path);
        img = Toolkit.getDefaultToolkit().getImage(bg_url);

        return img;
    }
}
