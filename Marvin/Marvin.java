// Robot
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
// Client
import java.rmi.* ;
import java.net.MalformedURLException ;
import java.util.*;

import IA.KeySender;
import IA.Eval;
import IA.Predict;
import Component.Point;
import Component.Grid;
import Component.Piece;
import Component.GameInterface;

public class Marvin
{
    static Robot robot;
    static GameInterface game;
    static KeySender sender;
    static Grid grid;
    static Piece piece;

    static void my_sleep(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch (InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    static public void run(String exec)
    {
        try
        {
            Runtime.getRuntime().exec(exec);
            System.out.println("IA : Wait running " + exec);
            my_sleep(1000);
        }
        catch (IOException ex)
        {
            Logger.getLogger(Marvin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void display_matrice(boolean t[][])
    {
        int x, y;

        System.out.println("Matrice vue par la IA : ");
        for (y = 0; y < 20; y++)
        {
            for (x = 0; x < 10; x++)
                if (t[x][y])
                    System.out.print("[]");
                else
                    System.out.print("--");
            System.out.println("");
        }
    }

    public static void get_game()
    {
        boolean got_game = false;
        try
        {
            do
            {
                int[][] g = game.get_grid();
                int[][] p = game.get_piece();
                int id = game.get_piece_id();

                if (g == null || p == null || id == -1)
                    my_sleep(10);
                else
                {
                    piece = new Piece(id, g.length/2, g[0].length-1);
                    Point[] c = new Point[4];
                    for (int i = 0; i < 4; i++)
                        c[i] = new Point(p[i][0], p[i][1]);
                    piece.set_coordinates(c);
                    grid = new Grid(g.length, g[0].length);
                    for (int i = 0; i < grid.width(); i++)
                        for (int j = 0; j < grid.height(); j++)
                            grid.put(i, j, g[i][j]);
                    got_game = true;
                }
            }
            while (! got_game);
        }
        catch (RemoteException re)
        {
            System.out.println(re);
        }
    }

    public static void main(String[] args) throws AWTException, IOException
    {
        // Execution
        if (args.length > 0)
            run(args[0]);

        // Robot
        try
        {
            robot = new Robot();
            robot.setAutoDelay(100); // 100 ms
            robot.setAutoWaitForIdle(false);
        }
        catch (AWTException ex)
        {
            Logger.getLogger(Marvin.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Client
        try
        {
            game = (GameInterface)Naming.lookup("//localhost/matrice");
        }
        catch (MalformedURLException e)
        {
            System.out.println(e);
        }
        catch (NotBoundException re)
        {
            System.out.println(re);
        }

        sender = new KeySender(robot);
        // my_sleep(1000);
        // for (int i = 0; i < 10; i++)
        // {
        //     sender.send_key(KeySender.LEFT);
        //     my_sleep(100);
        //     for (int j = 0; j < 37; i++)
        //     {
        //         sender.send_key(KeySender.DOWN);
        //     }
        // }
        while (true)
        {
            get_game();
            Point[][][] falls = Predict.possible_falls(grid, piece);
            int[] directions = Eval.eval_possibilities(grid, falls, piece);
            sender.send_key(directions);
        }
    }
}
