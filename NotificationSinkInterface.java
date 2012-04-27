package RemoteBoard;

import java.rmi.*;

public interface NotificationSinkInterface extends Remote {
	
	public Object notify(Notification n) throws RemoteException;
	
}
