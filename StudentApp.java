import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.CubicCurve2D;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.Color;

public class StudentApp extends JPanel {
    
    private static final int CURVE_WIDTH = 4;
    private static final long serialVersionUID = 1L;
    private Stroke curveStroke;
    private boolean clearing;
    private Color col;
    private List<Point> pointsCovered;

    public void init(){
	pointsCovered = new ArrayList<Point>();
	curveStroke = new BasicStroke(StudentApp.CURVE_WIDTH);
    }
	
    public void paint(Graphics g ){
	Graphics2D g2D = (Graphics2D) g;
	g2D.setStroke(curveStroke);
	g2D.setColor(col);
	if (pointsCovered.size() > 1) {
	    for (int i=1;i<pointsCovered.size();i++) {
		Point p = pointsCovered.get(i);
		Point p1 = pointsCovered.get(i-1);
		g2D.draw(new CubicCurve2D.Double(p.x,p.y,p.x,p.y,p1.x,p1.y,p1.x,p1.y));
	    }
	}
	if (clearing) {
	    g.setColor(this.getBackground());
	    g2D.fillRect(0,0,getWidth(),getHeight());
	}
	clearing = false;
    }
    
    public void changeColour(Color c) {
	this.col = c;
	repaint();
    }
    
    public void clear(){
	clearing = true;
	repaint();
    }

    public void interpretSignal(WriteSignal ws) {
	if (ws.getSignal() == WriteSignal.MOUSE_RELOC) {
	    pointsCovered = new ArrayList<Point>();
	} else if (ws.getSignal() == WriteSignal.MOUSE_WRIT) {
	    Point p = ws.getPoint();
	    pointsCovered.add(p);
	    repaint();
	} else if (ws.getSignal() == WriteSignal.CLEAR) {
	    pointsCovered = new ArrayList<Point>();
	    clear();
	} else if (ws.getSignal() == WriteSignal.COLOUR_CHANGE) {
	    changeColour(ws.getCol());
	}
    }
}

class Student extends NotificationSink {

    private static final long serialVersionUID = 1L;
    private StudentAppPanel sa1;
    private StudentAppPanel sa2;

    public Student(StudentAppPanel sa1,StudentAppPanel sa2) throws RemoteException {
	super();
	this.sa1 = sa1;
	this.sa2 = sa2;
    }

    @Override
    public Object notify(Notification n) throws RemoteException {
	WriteSignal ws = (WriteSignal) n.getInfo();
	if (n.getSource().equals("source0")) {
	    sa1.passSignal(ws);
	} else {
	    sa2.passSignal(ws);
	}
	return null;
    }
}

