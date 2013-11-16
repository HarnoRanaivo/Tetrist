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

import Basic.Draw;
import Basic.Game;

class Tetrist
{
    static Draw draw;
    static Game game;

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
        if (game.game_is_over())
        {
            System.out.println("Score : " + game.points);
            System.exit(0);
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
        draw = new Draw(game);
        f.getContentPane().add(draw);
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        f.pack();
        f.setVisible(true);

        /* Chute. */
        Timer timer = new Timer();
        TimerTask fall_task = new TimerTask()
            {
                public void run()
                {
                    action_fall();
                }
            };
        timer.schedule(fall_task, 0, 500);


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
    }
}
