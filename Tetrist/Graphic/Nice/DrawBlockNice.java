package Graphic.Nice;

import java.awt.Toolkit;
import java.net.URL;

import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics;

import Component.Grid;
import Graphic.Abstract.DrawBlock;

/**
 * Classe d'un dessin de bloc avec une image.
 */
public class DrawBlockNice extends DrawBlock
{
	/**
	 * Chemin vers l'image d'un bloc.
	 * 
	 * @see DrawBlockNice#init_image()
	 */
    protected static final String block_img = "/Pictures/block-overlay_";
    
    /**
     * Format de l'image.
     * 
     * @see DrawBlockNice#init_image()
	 */
    protected static final String img_postfix = ".png";


	/**
	 * Image d'un bloc.
	 * 
	 * @see DrawBlockNice#Constructor(int,Color[])
	 * @see DrawBlockNice#paint_block(Graphics,int,int,int)
	 */
    protected final Image block;

	/**
	 * Constructeur d'un dessin de bloc avec une image.
	 * 
	 * @param size
	 * 			Taille d'un bloc.
	 * @param c
	 * 			Palette de couleurs d'un bloc.
	 */
    public DrawBlockNice(int size, Color[] c)
    {
        super(size, c);
        block = init_image();
    }

	/**
	 * Initialisation de l'image.
	 * 
	 * @return image d'un bloc.
	 */
    protected Image init_image()
    {
        Image img = null;

        String block_path = block_img + block_size + img_postfix;
        URL block_url = getClass().getResource(block_path);
        img = Toolkit.getDefaultToolkit().getImage(block_url);

        return img;
    }

	/**
	 * Dessin d'un bloc avec une image.
	 * 
	 * @param g
	 * 			Variable employée pour le dessin dans une fenêtre.
	 * @param x
	 * 			Abscisse du bloc.
	 * @param y
	 * 			Ordonnée du bloc.
	 * @param value
	 * 			Code de la pièce. (ou du vide)
	 */
    public void paint_block(Graphics g, int x, int y, int value)
    {
        if (value != Grid.EMPTY_BLOCK)
            g.drawImage(block, x, y, color_of_int(value), null);
    }
}
