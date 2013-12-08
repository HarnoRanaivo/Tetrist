package Graphic.Abstract;

import java.awt.Graphics;

/**
 * Classe abstraite dessins génériques d'arrières plans.
 */
public abstract class DrawBackground
{
	/**
	 * Largeur.
	 * 
	 * @see DrawBackground#Constructor(int,int)
	 * @see DrawBackground#width()
	 */
    protected final int width;
    
    /**
	 * Hauteur.
	 * 
	 * @see DrawBackground#Constructor(int,int)
	 * @see DrawBackground#height()
	 */
    protected final int height;

	/**
	 * Constructeur d'un arrière plan abstrait générique.
	 * 
	 * @param w
	 * 			Largeur.
	 * @param h
	 * 			Hauteur.
	 */
    public DrawBackground(int w, int h)
    {
        width = w;
        height = h;
    }

	/**
	 * Retourne la largeur de l'arrière plan.
	 * 
	 * @return largeur.
	 */
    public int width()
    {
        return width;
    }

	/**
	 * Retourne la hauteur de l'arrière plan.
	 * 
	 * @return hauteur.
	 */
    public int height()
    {
        return height;
    }
	
	/**
	 * Méthode de dessin d'un arrière plan.
	 * 
	 * @param g
	 * 			Variable employée pour le dessin dans une fenêtre.
	 */
    public abstract void paint(Graphics g);
}
