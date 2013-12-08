package Graphic.Abstract;

import java.awt.Graphics;
import java.awt.Color;

import Component.Piece;

/**
 * Classe abstraite de dessins génériques de blocs.
 */
public abstract class DrawBlock
{
	/**
	 * Taille d'un bloc.
	 * 
	 * @see DrawBlock#Constructor(int,Color[])
	 * @see DrawBlock#block_size()
	 */
    protected final int block_size;
    
    /**
     * Palette de couleurs pour les pièces ou le vide.
     * 
	 * @see DrawBlock#Constructor(int,Color[])
	 * @see color_of_int(int)
	 */
    protected final Color[] colors;

	/**
	 * Constructeur d'un bloc générique.
	 * 
	 * @param size
	 * 			Taille du bloc.
	 * @param c
	 * 			Palette de couleurs pour le bloc.
	 */
    public DrawBlock(int size, Color[] c)
    {
        block_size = size;
        colors = c;
    }

	/**
	 * Retourne la taille d'un bloc.
	 * 
	 * @return taille d'un bloc.
	 */
    public int block_size()
    {
        return block_size;
    }

	/**
	 * Retourne une couleur pour un entier donné.</br>
	 * <i>(utilisée pour définir les couleurs en fonction du code d'une pièce)</i>
	 * 
	 * @return la couleur associée.
	 */
    protected Color color_of_int(int value)
    {
        if (value < 0 || value >= Piece.CARDINAL)
            return Color.black;
        else
            return colors[value];
    }

	/**
	 * Méthode pour peindre un bloc.
	 * 
	 * @param g
	 * 			Variable employée pour le dessin dans une fenêtre.
	 * @param x
	 * 			Abscisse du bloc.
	 * @param y
	 * 			Ordonnée du bloc.
	 * @param value
	 * 			Valeur du bloc <i>(pièce)</i>
	 */
    public abstract void paint_block(Graphics g, int x, int y, int value);
}
