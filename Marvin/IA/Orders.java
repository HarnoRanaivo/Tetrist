package IA;

import IA.KeySender;

/**
 * Classe d'ordres utilisés par le robot dans KeySender.
 */
public class Orders
{
	/**
	 * Nombre de rotations.
	 * 
	 * @see Orders#Constructor()
	 * @see Orders#set_rotations(int)
	 * @see Orders#rotations()
	 */
    private int rotations;
    
    /**
	 * Direction du déplacement.
	 * 
	 * @see Orders#Constructor()
	 * @see Orders#set_direction(int)
	 * @see Orders#direction()
	 */
    private int direction;
    
    /**
     * Décalage.
     * 
     * @see Orders#Constructor()
	 * @see Orders#set_shift(int)
	 * @see Orders#shift()
	 */
    private int shift;

	/**
	 * Constructeur d'un ordre de pression de touche.
	 */
    public Orders()
    {
        rotations = 0;
        shift = 0;
        direction = KeySender.NOTHING;
    }

	/**
	 * Modifie le nombre de rotations.
	 * 
	 * @param n
	 * 			Nombre de rotations.
	 */
    public void set_rotations(int n)
    {
        if (n >= 0 && n < 4)
            rotations = n;
    }

	/**
	 * Modifie la direction.
	 * 
	 * @param n
	 * 			Direction.
	 */
    public void set_direction(int n)
    {
        direction = (n < 0 || n > 4) ? 0 : n;
    }

	/**
	 * Modifie le décalage
	 * 
	 * @param n
	 * 			Décalage.
	 */
    public void set_shift(int n)
    {
        shift = n;
    }

	/**
	 * Modifie les ordres à donner
	 * 
	 * @param r
	 * 			Nombre de rotations.
	 * @param d
	 * 			Direction.
	 * @param s
	 * 			Décalage.
	 */
    public void set(int r, int d, int s)
    {
        set_rotations(r);
        set_direction(d);
        set_shift(s);
    }

	/**
	 * Retourne le nombre de rotations.
	 * 
	 * @return nombre de rotations.
	 */
    public int rotations()
    {
        return rotations;
    }

	/**
	 * Retourne la direction.
	 * 
	 * @return direction.
	 */
    public int direction()
    {
        return direction;
    }

	/**
	 * Retourne le décalage.
	 * 
	 * @return décalage.
	 */
    public int shift()
    {
        return shift;
    }
}
