// Connexion IA
import java.net.* ;
import java.rmi.* ;
// Graphique
import javax.swing.*;
// Clavier
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class tetris {

    static Matrice matrice;
    static Draw draw;

    static int x,y; // For example

    public static void action_right()
    { 
	// For example
	if (x<9) {
	    x++;
	    matrice.put(x,y,true);
	    draw.refresh();
	    System.err.print("0");
	}
    }

    public static void action_left()
    { 
	// For example
	if (x>0) {
	    x--;
	    matrice.put(x,y,true);
	    draw.refresh();
	    System.err.print("1");
	}
    }

    public static void action_rotation()
    { 
	// For example
	if (y>0) {
	    y--;
	    matrice.put(x,y,true);
	    draw.refresh();
	    System.err.print("2");
	}
    }

    public static void action_fall()
    { 
	// For example
	if (y<19) {
	    y++;
	    matrice.put(x,y,true);
	    draw.refresh();
	    System.err.print("3");
	}
    }

    public static void main(String[] args)
    {
	// Connexion IA
	try {
	    matrice = new Matrice();
	    Naming.rebind("matrice",matrice) ;
	} catch (RemoteException re) { System.out.println(re) ; }
	  catch (MalformedURLException e) { System.out.println(e) ; }

	// Base Graphique
	JFrame f = new JFrame("TetriS");
	draw = new Draw(matrice);
	f.getContentPane().add(draw);	
	f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	f.pack();
	f.setVisible(true);

	// Clavier
	f.addKeyListener(new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
		    switch (e.getKeyCode()) {
		    case KeyEvent.VK_RIGHT: action_right();    break;
		    case KeyEvent.VK_LEFT:  action_left();     break;
		    case KeyEvent.VK_UP:    action_rotation(); break;
		    case KeyEvent.VK_DOWN:  action_fall();     break;
		    }
		}
		public void keyReleased(KeyEvent e) {
		    // KEY RELEASED
		}		
        });
	System.out.println("Début du jeu...");
    }
}