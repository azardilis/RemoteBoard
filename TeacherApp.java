package RemoteBoard;

import java.awt.BasicStroke;
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
	}

	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(curveStroke);
		Point p1;
		if (isDragged) {
			Point p = pointsCovered.get(pointsCovered.size()-1);
			if (pointsCovered.size() > 1) {
				p1 = pointsCovered.get(pointsCovered.size()-2);
				g2D.draw(new CubicCurve2D.Double(p.x,p.y,p.x,p.y,p1.x,p1.y,p1.x,p1.y));
			}
		}	
	}

	public void mouseDragged(MouseEvent e) {
		isDragged = true;
		pointsCovered.add(new Point(e.getX(),e.getY()));
		try {
			source.fireEvent(new Point(e.getX(),e.getY()));
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
	}
	public void mouseReleased(MouseEvent arg0) {}
	public void mouseMoved(MouseEvent e) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	
}