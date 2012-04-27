package RemoteBoard;

import java.rmi.*;

public interface NotificationSourceInterface extends Remote {

	public void register(NotificationSinkInterface sink) 
		throws RemoteException;
	
	public void unregister(NotificationSinkInterface sink)
		throws RemoteException;
	
	public void fireEvent(Object obj) throws RemoteException;
	
	public String getName() throws RemoteException;
}
