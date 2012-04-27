import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import javax.swing.*;


public class SquareSink {
	
	public static void main(String [] args) {
		JFrame appFrame = new JFrame("Square Sink Test");
		appFrame.setSize(300,300);
		SquareSinkPanel sqPanel = new SquareSinkPanel();
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
}

class SquareSinkPanel extends JPanel implements MouseListener{
	
	private static final long serialVersionUID = 1L;
	private Square sq;
	private NotificationSink sink;
	
	public void init() throws RemoteException, MalformedURLException {
		sq = new Square(Color.RED);
		sink = new NotificationSink(1,sq,this);
		Naming.rebind("notificationSink",sink);
		this.addMouseListener(this);
		
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		sq.draw(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			NotificationSourceInterface source = (NotificationSourceInterface) 
					Naming.lookup("rmi://localhost/notificationSource");
			source.register(sink);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}



