package RemoteBoard;

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

import javax.swing.JPanel;

public class StudentApp extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Student student;
	private Stroke curveStroke;
	
	public void init(){
		try {
			student = new Student(this);
			Naming.rebind("notificationSink",student);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		curveStroke = new BasicStroke(4);
	}
	
	public void paint(Graphics g ){
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(curveStroke);
		Point p1;
		if (student.pointsCovered.size() > 0) {
			Point p = student.pointsCovered.get(student.pointsCovered.size()-1);
			if (student.pointsCovered.size() > 1) {
				p1 = student.pointsCovered.get(student.pointsCovered.size()-2);
				g2D.draw(new CubicCurve2D.Double(p.x,p.y,p.x,p.y,p1.x,p1.y,p1.x,p1.y));
			}
		}		
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
		Point p = (Point)n.getInfo();
		pointsCovered.add(p);
		sa.repaint();
		return null;
	}
	
}