package Graphic.Nice;

import java.net.URL;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Graphics;

import Graphic.Abstract.DrawBackground;

/**
 * Classe d'un dessin d'arrière-plan avec image.
 */
public class DrawBackgroundNice extends DrawBackground
{
    /**
     * Chemin vers l'image de l'arrière-plan.
     *
     * @see DrawBackgroundNice#init_image
     */
    protected static final String background_path = "/Pictures/background.png";

    /**
     * Image de l'arrière-plan.
     *
     * @see DrawBackgroundNice#Constructor(int,int)
     * @see DrawBackgroundNice#paint(Graphics)
     */
    protected final Image background;

    /**
     * Constructeur d'un arrière-plan avec une image.
     *
     * @param w
     *          Largeur de l'arrière-plan.
     * @param h
     *          Hauteur de l'arrière-plan.
     */
    public DrawBackgroundNice(int w, int h)
    {
        super(w, h);
        background = init_image();
    }

    /**
     * Dessin d'un arrière-plan avec une image.
     *
     * @param g
     *          Variable employée pour le dessin dans une fenêtre.
     */
    public void paint(Graphics g)
    {
        g.drawImage(background, 0, 0, null);
    }

    /**
     * Initialisation de l'image.
     *
     * @return l'image de l'arrière-plan.
     */
    protected Image init_image()
    {
        Image img = null;
        URL bg_url = getClass().getResource(background_path);
        img = Toolkit.getDefaultToolkit().getImage(bg_url);

        return img;
    }
}
