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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Student student;
	private Stroke curveStroke;
    private boolean clearing;
    private Color col;
    private Color col1;

	public void init(){
		try {
			student = new Student(this);
			Naming.rebind("notificationSink",student);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		curveStroke = new BasicStroke(4);
	}
	
	public void paint(Graphics g ){
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(curveStroke);
		g2D.setColor(col);
		if (student.pointsCovered.size() > 0) {
			if (student.pointsCovered.size() > 1) {
			    for (int i=1;i<student.pointsCovered.size();i++) {
				Point p = student.pointsCovered.get(i);
				Point p1 = student.pointsCovered.get(i-1);
				g2D.draw(new CubicCurve2D.Double(p.x,p.y,p.x,p.y,p1.x,p1.y,p1.x,p1.y));
			    }
			}
		}
		if (clearing) {
		    g.setColor(this.getBackground());
		    g2D.fillRect(0,0,getWidth(),getHeight());
		}
		clearing = false;
	}

	public Student getStudent(){return this.student;}
    
    public void changeColour(Color c) {
	this.col = c;
	repaint();
    }
    
    public void changeColour1(Color c) {
	this.col1 = c;
    }
    public void clear(){
	clearing = true;
	repaint();
    }
}

class Student extends NotificationSink {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public List<Point> pointsCovered;
	private StudentApp sa;

    public Student(StudentApp sa) throws RemoteException {
		super();
		pointsCovered = new ArrayList<Point>();
		this.sa = sa;
	}

	@Override
	public Object notify(Notification n) throws RemoteException {
	    WriteSignal ws = (WriteSignal) n.getInfo();
	    if (ws.getSignal() == WriteSignal.MOUSE_RELOC) {
	    	pointsCovered = new ArrayList<Point>();
	    } else if (ws.getSignal() == WriteSignal.MOUSE_WRIT) {
	    	Point p = ws.getPoint();
	    	pointsCovered.add(p);
	    	sa.repaint();
	    } else if (ws.getSignal() == WriteSignal.CLEAR) {
		pointsCovered = new ArrayList<Point>();
		sa.clear();
	    } else if (ws.getSignal() == WriteSignal.COLOUR_CHANGE) {
		sa.changeColour(ws.getCol());
	    }
	    return null;

	}
}

