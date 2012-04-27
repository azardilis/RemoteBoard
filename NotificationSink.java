import java.awt.Point;
import java.rmi.*;
import java.rmi.server.*;

@SuppressWarnings("serial")
public class NotificationSink extends UnicastRemoteObject
	implements NotificationSinkInterface{
	
	@SuppressWarnings("unused")
	private int id;
	private Square s;
	private SquareSinkPanel sqp;
	
	public NotificationSink(int i,Square s, SquareSinkPanel sqp) throws RemoteException { 
		super();
		this.id = i;
		this.s = s;
		this.sqp = sqp;
	}

	@Override
	public Object notify(Notification n) throws RemoteException {
		//NotificationSourceInterface source = n.getSource();
		//System.out.println("Sink " + this.id + " received note from "  + source.getName());
		Point p = (Point) n.getInfo();
		s.setX((int)p.getX());
		s.setY((int)p.getY());
		sqp.repaint();
		return null;
	}	
}
