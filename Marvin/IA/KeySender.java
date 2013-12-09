package IA;

import java.awt.Robot;
import java.awt.event.KeyEvent;

/**
 * Classe d'envoie de lettre du robot pour les déplacements.
 */
public class KeySender
{
    /**
     * Ne fait rien.
     *
     * @see KeySender#send_key(Orders)
     * @see Orders#Constructor()
     */
    public static final int NOTHING = 0;

    /**
     * Fait aller la pièce vers la droite.
     *
     * @see KeySender#send_key(Orders)
     */
    public static final int RIGHT = 1;

    /**
     * Fait aller la pièce vers la gauche.
     *
     * @see KeySender#send_key(Orders)
     */
    public static final int LEFT = 2;

    /**
     * Tourne la pièce.
     *
     * @see KeySender#send_key(Orders)
     */
    public static final int ROTATE = 3;

    /**
     * Fait descendre la pièce.
     *
     * @see KeySender#send_key(Orders)
     */
    public static final int DOWN = 4;

    /**
     * Touches assignées aux mouvements.
     *
     * @see KeySender#send_key(int)
     */
    private static final int[] KEYS =
        {
            KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_DOWN
        };

    /**
     * Robot d'automatisation de touches.
     *
     * @see KeySender#Constructor(Robot)
     */
    private Robot robot;

    /**
     * Constructeur d'un robot d'automatisation de pression de touches.
     *
     * @param r
     *          Le robot.
     */
    public KeySender(Robot r)
    {
        robot = r;
    }

    /**
     * Envoie d'une touche.
     *
     * @param key
     *          Code de la touche.
     */
    public void send_key(int key)
    {
        if (key > 0 && key <= KEYS.length)
        {
            int k = KEYS[key-1];
            robot.keyPress(k);
            robot.keyRelease(k);
        }
    }

    /**
     * Envoie d'une touche.
     *
     * @param orders
     *          Ordre d'envoie.
     */
    public void send_key(Orders orders)
    {
        for (int i = 0; i < orders.rotations(); i++)
            send_key(ROTATE);

        int direction = orders.direction();
        if (direction != KeySender.NOTHING)
            for (int i = 0; i < orders.shift(); i++)
                send_key(direction);
    }
}
