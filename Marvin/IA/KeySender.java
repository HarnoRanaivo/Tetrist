package IA;

import java.awt.Robot;
import java.awt.event.KeyEvent;

public class KeySender
{
    public static final int NOTHING = 0;
    public static final int RIGHT = 1;
    public static final int LEFT = 2;
    public static final int ROTATE = 3;
    public static final int DOWN = 4;
    private static final int[] KEYS =
        {
            KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_DOWN
        };

    private Robot robot;

    public KeySender(Robot r)
    {
        robot = r;
    }

    public void send_key(int key)
    {
        if (key > 0 && key <= KEYS.length)
        {
            int k = KEYS[key-1];
            robot.keyPress(k);
            robot.keyRelease(k);
        }
    }

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
