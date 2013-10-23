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

public class IA {
    
    static Robot robot;
    static MatriceInterface matrice;

    static void my_sleep(int ms)
    {
	try {
	    Thread.sleep(ms);
	} catch(InterruptedException ex) {
	    Thread.currentThread().interrupt();
	}
    }
    
    static public void run(String exec)
    {
	try {
	    Runtime.getRuntime().exec(exec);
	    System.out.println("IA : Wait running "+exec);
	    my_sleep(1000);
	} catch (IOException ex) {
	    Logger.getLogger(IA.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    public static void send_key(int key) 
    { int k=0;
	switch (key) {
	case 0:k = KeyEvent.VK_RIGHT;	break; 	// 0:Droite
	case 1:k = KeyEvent.VK_LEFT; 	break;	// 1:Gauche
	case 2:k = KeyEvent.VK_UP;	break;	// 2:Rotation
	case 3:k = KeyEvent.VK_DOWN; 	break;	// 3:Tomber
	};
	robot.keyPress(k);
	robot.keyRelease(k); 	        
    }

    public static void display_matrice(boolean t[][])
    {   int x,y;
	System.out.println("Matrice vue par la IA : ");
	for (y=0;y<20;y++) {
	    for (x=0;x<10;x++) {
		if (t[x][y]) {
		    System.out.print("[]");
		} else {
		    System.out.print("--");
		}		
	    }
	    System.out.println("");
	}
    }

    public static boolean[][] get_matrice()
    { boolean [][] result=null;
	try {
	    do {
		result = matrice.get_matrice();
		if (result == null) my_sleep(10);
	    } while (result == null);
	} catch (RemoteException re) { System.out.println(re) ; }
	return result;
    }
    
    public static void main(String[] args) throws AWTException, IOException {	
	// Execution 
	run("java tetris");
	// Robot
	try {
	    robot = new Robot();
	    robot.setAutoDelay(100); // 100 ms
	    robot.setAutoWaitForIdle(false);
	} catch (AWTException ex) {
	    Logger.getLogger(IA.class.getName()).log(Level.SEVERE, null, ex);
	}
	// Client
	try {
	    matrice = (MatriceInterface)Naming.lookup("//localhost/matrice");
	} catch (MalformedURLException e) { System.out.println(e) ; }
	catch (NotBoundException re) { System.out.println(re) ; }

	// For Example
	String txt="0000030303333313131111122222222233333333333333333330000000202022222121211";
	for (int j=0; j<txt.length(); j++) {	   
	    send_key((int)(txt.charAt(j)-'0'));
	    my_sleep(100);
	    display_matrice(get_matrice());
	}
    }
} 
