import java.awt.Point;
import java.io.Serializable;
import java.awt.Color;

public class WriteSignal implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    public static final int MOUSE_RELOC = 0;
    public static final int MOUSE_WRIT = 1;
    public static final int CLEAR = 2;
    public static final int COLOUR_CHANGE = 3;

    private Point point;
    private int signal;
    private Color col;

    public WriteSignal(int sig, Point p) {
	this.point = p;
	this.signal = sig;
    }
    
    public WriteSignal(int sig,Color c,Point p) {
	this.signal = sig;
	this.col = c;
    }

    public int getSignal(){return this.signal;}
    public Point getPoint(){return this.point;}
    public Color getCol(){return this.col;}
    
}