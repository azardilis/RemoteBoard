import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class NotificationSource extends UnicastRemoteObject 
								implements Serializable, NotificationSourceInterface{

	private static final long serialVersionUID = 1L;
	private ArrayList<NotificationSinkInterface> registeredSinks;
	private String name;
	
	public NotificationSource() throws RemoteException {
		registeredSinks = new ArrayList<NotificationSinkInterface>();
		this.name = "exampleSource";
	}
	
	public synchronized void fireEvent(Object info) throws RemoteException {
		Notification note = new Notification(info,this);
		System.out.println("sending notes");
		for (NotificationSinkInterface sink : registeredSinks) {
			sink.notify(note);
		}
	}
	
	public synchronized String getName() throws RemoteException{ return this.name; }

	@Override
	public synchronized void register(NotificationSinkInterface sink) throws RemoteException {
	    
		if (!registeredSinks.contains(sink)) {
			registeredSinks.add(sink);
		}
	}

	@Override
	public synchronized void unregister(NotificationSinkInterface sink)
			throws RemoteException {
		if (registeredSinks.contains(sink)) {
			registeredSinks.remove(sink);
		}
	}
}
