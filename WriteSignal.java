import java.awt.Point;
import java.io.Serializable;

public class WriteSignal implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int MOUSE_RELOC = 0;
    public static final int MOUSE_WRIT = 1;
    private Point point;
    private int signal;

    public WriteSignal(int sig, Point p) {
	this.point = p;
	this.signal = sig;
    }

    public int getSignal(){return this.signal;}
    public Point getPoint(){return this.point;}
}