import java.awt.Color;
import java.rmi.*;

public class SinkServer {
	
	public static void main(String [] args) {
		try {
			NotificationSink ns = new NotificationSink(1,new Square(Color.BLACK), new SquareSinkPanel());
			Naming.rebind("notificationSink",ns);
			System.out.println("notification sink ready");
			NotificationSourceInterface source = (NotificationSourceInterface) 
						Naming.lookup("rmi://localhost/notificationSource");
			source.register(ns);
			System.out.println("registed for callbacks");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
