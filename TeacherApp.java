import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.CubicCurve2D;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class TeacherApp extends JPanel implements MouseListener,MouseMotionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Point> pointsCovered;
	private NotificationSource source;
	private boolean isDragged;
	private Stroke curveStroke;
    private boolean clearing;
    private Color col;
    
	public void init() {
		curveStroke = new BasicStroke(4);
		pointsCovered = new ArrayList<Point>();
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		try {
			source = new NotificationSource();
			Naming.rebind("notificationSource",source);
		} catch (RemoteException e) {
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		col = Color.BLACK;
	}

    public void setColour(Color c) {
	this.col = c;
	try {
	    source.fireEvent(new WriteSignal(WriteSignal.COLOUR_CHANGE,c,null));
		} catch (RemoteException e1) {
		    e1.printStackTrace();
		}
	repaint();
    }
	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(curveStroke);
		g2D.setColor(col);
		Point p1;
		if (isDragged) {
			Point p = pointsCovered.get(pointsCovered.size()-1);
			if (pointsCovered.size() > 1) {
				p1 = pointsCovered.get(pointsCovered.size()-2);
				g2D.draw(new CubicCurve2D.Double(p.x,p.y,p.x,p.y,p1.x,p1.y,p1.x,p1.y));
			}
		}
		
		if (clearing){
		    g.setColor(this.getBackground());
		    g2D.fillRect(0,0,getWidth(),getHeight());
		}
		clearing = false;
	}
	
	public void clear(){
		pointsCovered = new ArrayList<Point>();
		try {
		    source.fireEvent(new WriteSignal(WriteSignal.CLEAR,null));
		} catch (RemoteException e1) {
		    e1.printStackTrace();
		}
		clearing = true;
		isDragged = false;
		repaint();
	}

	public void mouseDragged(MouseEvent e) {
	    System.out.println("dragged");
		isDragged = true;
		pointsCovered.add(new Point(e.getX(),e.getY()));
		try {
		    source.fireEvent(new WriteSignal(WriteSignal.MOUSE_WRIT,new Point(e.getX(),e.getY())));
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		repaint();
	}

	public void mouseClicked(MouseEvent arg0) {
	    pointsCovered = new ArrayList<Point>();
	}
	
	public void mousePressed(MouseEvent arg0) {
		pointsCovered = new ArrayList<Point>();
		try {
		    source.fireEvent(new WriteSignal(WriteSignal.MOUSE_RELOC,new Point(0,0)));
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	public void mouseReleased(MouseEvent arg0) {}
	public void mouseMoved(MouseEvent e) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	
}
