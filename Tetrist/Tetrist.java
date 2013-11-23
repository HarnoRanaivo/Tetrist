// Connexion IA
import java.net.*;
import java.rmi.*;

// Graphique
import javax.swing.*;

// Clavier
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.Timer;
import java.util.TimerTask;

import Graphic.Draw;
import Component.Game;

class Tetrist
{
    static Draw draw;
    static Game game;

    static int level = 0;
    static final int time_max = 500;
    static final int time_min = 100;
    static final int time_step = 100;
    static Timer timer = null;
    static int x, y; // For example

    public static void action_right()
    {
        game.right();
        draw.refresh();
    }

    public static void action_left()
    {
        game.left();
        draw.refresh();
    }

    public static void action_rotation()
    {
        game.rotate();
        draw.refresh();
        return;
    }

    public static void action_fall()
    {
        game.fall();
        draw.refresh();
        new_level(game.level());
        if (game.game_is_over())
        {
            System.out.println("Score : " + game.score());
            System.out.println("Level : " + game.level());
            System.out.println("Lines : " + game.lines_destroyed());
            System.exit(0);
        }
    }

    public static int time_of_level(int n)
    {
        int time = time_max - (n-1) * time_step;
        return time >= time_min ? time : time_min;
    }

    public static void new_level(int n)
    {
        if (level != n)
        {
            level = n;
            int time = time_of_level(level);
            if (timer != null)
                timer.cancel();
            timer = new Timer();
            TimerTask fall_task = new TimerTask()
                {
                    public void run()
                    {
                        action_fall();
                    }
                };
            timer.schedule(fall_task, 0, time);
        }
    }

    public static void main(String[] args)
    {
        // Connexion IA
        try
        {
            game = new Game();
            Naming.rebind("matrice", game);
        }
        catch (RemoteException re)
        {
            System.out.println(re);
        }
        catch (MalformedURLException e)
        {
            System.out.println(e);
        }

        // Base Graphique
        JFrame f = new JFrame("TetriS");
        draw = Draw.factory(game);
        f.getContentPane().add(draw);
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        f.pack();
        f.setVisible(true);

        // Clavier
        f.addKeyListener(new KeyAdapter()
            {
                public void keyPressed(KeyEvent e)
                {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_RIGHT:
                            action_right();
                            break;
                        case KeyEvent.VK_LEFT:
                            action_left();
                            break;
                        case KeyEvent.VK_UP:
                            action_rotation();
                            break;
                        case KeyEvent.VK_DOWN:
                            action_fall();
                            break;
                    }
                }

                public void keyReleased(KeyEvent e)
                {
                    // KEY RELEASED
                }
            }
        );

        /* Chute. */
        new_level(game.level());
    }
}
