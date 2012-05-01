import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class NotificationSource extends UnicastRemoteObject 
								implements Serializable, NotificationSourceInterface{

    private static final int MAX_TRIES = 10;
    private static final long serialVersionUID = 1L;
    private ArrayList<NotificationSinkInterface> registeredSinks;
    private String name;
    private int index;
    private int numTries;
    
	public NotificationSource() throws RemoteException {
		registeredSinks = new ArrayList<NotificationSinkInterface>();
		this.name = "exampleSource";
	}
	
	public synchronized void fireEvent(Object info) {
		Notification note = new Notification(info,this);
		for (int i=index;i<registeredSinks.size();i++) {
		    try {
			NotificationSinkInterface sink = registeredSinks.get(i);
			sink.notify(note);
		    } catch (RemoteException e) {
			if (!retry(i,note)) unregister(registeredSinks.get(i));
		    }
		}
	}
	
    public boolean retry(int index, Notification note) {
	NotificationSinkInterface sink = registeredSinks.get(index);
	for (int i=0;i<MAX_TRIES;i++) {
	    try {
		sink.notify(note);
		return true;
	    } catch (RemoteException e) {}
	}
	return false;
    }
	public synchronized String getName() throws RemoteException{ return this.name; }

	@Override
	public synchronized void register(NotificationSinkInterface sink) {
	    
		if (!registeredSinks.contains(sink)) {
			registeredSinks.add(sink);
		}
	}

	@Override
	public synchronized void unregister(NotificationSinkInterface sink){
		if (registeredSinks.contains(sink)) {
			registeredSinks.remove(sink);
		    } 
		
	}
}
