package Graphic.Nice;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;

import java.net.URL;
import java.net.URLConnection;

import Component.Point;
import Component.Game;

import Graphic.Abstract.DrawInfos;

/**
 * Classe de dessins des informations avec une font.
 */
public class DrawInfosNice extends DrawInfos
{
	/**
	 * Font pour les informations.
	 * 
	 * @see DrawInfosNice#Constructor(Game,Point)
	 */
    protected final Font font;
    
    
    /**
     * Taille de l'écriture.
     * 
     * @see DrawInfosNice#init_font()
     */
    protected static final int font_size = 20;
    
    /**
     * L'espace entre deux informations.
     * 
     * @see DrawInfosNice#paint_infos(Graphics)
     */
    protected static final int line_space = font_size + line_shift;

	/**
	 * Constructeur d'un dessin d'informations avec une font.
	 * 
	 * @param g
	 * 			Variable du jeu Tetrist.
	 * @param offset
	 * 			Décalage exprimée à l'aide d'un point.
	 * @throws Si la font n'arrive pas à être chargée.
	 */
    public DrawInfosNice(Game g, Point offset) throws Exception
    {
        super(g, offset, 200, 200);
        font = init_font();
    }

	/**
	 * Initialisation de la font.
	 * 
	 * @throws Si la font n'arrive pas à être chargée.
	 * @return La font pour les informations.
	 */
    private Font init_font() throws Exception
    {
        Font f = null;

        String font_path = "/Fonts/heavy_data.ttf";
        URL font_url = getClass().getResource(font_path);
        URLConnection font_connection = font_url.openConnection();
        f = Font.createFont(Font.TRUETYPE_FONT, font_connection.getInputStream());
        f = f.deriveFont(Font.PLAIN, font_size);

        return f;
    }

	/**
	 * Dessin complet des informations avec une font.
	 * 
	 * @param g
	 * 			Variable employée pour le dessin dans une fenêtre.
	 */
    public void paint(Graphics g)
    {
        g.setColor(Color.yellow);
        g.setFont(font);
        paint_infos(g);
    }

	/**
	 * Dessin des informations uniquement.
	 * 
	 * @param g
	 * 			Variable employée pour le dessin dans une fenêtre.
	 */
    public void paint_infos(Graphics g)
    {
        g.drawString("Level: " + game.level(), offset_x,  0 + offset_y);
        g.drawString("Score: " + game.score(), offset_x, line_space + offset_y);
        g.drawString("Lines: " + game.lines_destroyed(), offset_x, 2 * line_space + offset_y);
    }
}
