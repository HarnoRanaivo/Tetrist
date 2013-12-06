package IA;

import java.awt.Robot;
import java.awt.event.KeyEvent;

public class KeySender
{
    public static final int NOTHING = -1;
    public static final int RIGHT = 0;
    public static final int LEFT = 1;
    public static final int ROTATE = 2;
    public static final int DOWN = 3;
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
        if (key >= 0 && key < KEYS.length)
        {
            int k = KEYS[key];
            robot.keyPress(k);
            robot.keyRelease(k);
        }
    }

    public void send_key(int[] directions)
    {
        for (int i = 0; i < directions[0]; i++)
            send_key(ROTATE);

        if (directions[1] != NOTHING)
            for (int i = 0; i < directions[2]; i++)
                send_key(directions[1]);
    }
}
