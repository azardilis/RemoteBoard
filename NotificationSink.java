import java.rmi.*;
import java.rmi.server.*;

@SuppressWarnings("serial")
public abstract class NotificationSink extends UnicastRemoteObject
	implements NotificationSinkInterface{
	
	public NotificationSink() throws RemoteException { 
		super();
	}

	@Override
	public abstract Object notify(Notification n) throws RemoteException;
}
