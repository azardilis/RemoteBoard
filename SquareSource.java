import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import javax.swing.*;

public class SquareSource {
	
	public static void main(String [] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame appFrame = new JFrame("Square Source Test");
				appFrame.setSize(300,300);
				SquareSourcePanel sqPanel = new SquareSourcePanel();
				try {
					sqPanel.init();
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				appFrame.setContentPane(sqPanel);
				appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				appFrame.setVisible(true);
			}
		});
	}
}

class SquareSourcePanel extends JPanel implements MouseListener, MouseMotionListener{
	

	private static final long serialVersionUID = 1L;
	private Shape sq;
	private NotificationSource source;
	private boolean squareClicked;
	
	public void init() throws RemoteException, MalformedURLException {
		sq = new Square(Color.BLACK);
		squareClicked = false;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		source = new NotificationSource();
		Naming.rebind("notificationSource",source);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		sq.draw(g);
	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		if (squareClicked) {
			sq.setX(evt.getX());
			sq.setY(evt.getY());
			Point p = new Point(evt.getX(),evt.getY());
			try {
				source.fireEvent(p);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			repaint();
		}
	}

	public void mouseReleased(MouseEvent evt) {}
	public void mouseMoved(MouseEvent evt) {}
	public void mouseClicked(MouseEvent evt) {}
	public void mouseEntered(MouseEvent evt) {}
	public void mouseExited(MouseEvent evt) {}

	@Override
	public void mousePressed(MouseEvent evt) {	
		if (sq.isInside(evt.getX(), evt.getY())) {
			squareClicked = true;
		} else {
			squareClicked = false;
		}
	}
}

abstract class Shape {
	public abstract void setX(int x);
	public abstract void setY(int y);
	public abstract boolean isInside(int x, int y);
	public abstract void draw(Graphics g);
}

class Square extends Shape {
	
	private int x;
	private int y;
	private int width;
	private Color col;
	
	public Square(Color c){
		this.x = 50;
		this.y = 50;
		this.width = 50;
		this.col = c;
	}
	
	public int getX() { return this.x; }
	public int getY() { return this.y; }
	public void setX(int x) { this.x = x;}
	public void setY(int y) { this.y = y; }
	
	public void draw(Graphics g) {
		g.setColor(col);
		g.fillRect(x, y, width, width);
	}
	
	public boolean isInside(int x, int y) {
		if (x > this.x && x < this.x + this.width &&
					y > this.y && y < this.y + this.width) return true;
		return false;
	}
}

class Circle extends Shape {

	private Color col;
	private int x;
	private int y;
	private int width;
	
	
	@Override
	public void setX(int x) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setY(int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isInside(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void draw(Graphics g) {
		
	}
	
}
