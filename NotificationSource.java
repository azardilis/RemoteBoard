import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.Vector;

public class NotificationSource extends UnicastRemoteObject 
								implements Serializable, NotificationSourceInterface{

    private ArrayList<NotificationSinkInterface> registeredSinks;
    private String name;
    private Vector<ArrayBlockingQueue<Notification>> messages = new Vector<ArrayBlockingQueue<Notification>>();

	public NotificationSource(String name) throws RemoteException {
		registeredSinks = new ArrayList<NotificationSinkInterface>();
		this.name = name;
	}
	
	public synchronized void fireEvent(Object info) {
		Notification note = new Notification(info,this.name);
		for (int i=0;i<registeredSinks.size();i++) {
		    NotificationSinkInterface sink = registeredSinks.get(i);
		    if (messages.get(i).size() > 5) unregister(sink);
		    else {
			messages.get(i).add(note);
			deliverMessages(sink,i);
		     }
		}
	}

    public void deliverMessages(NotificationSinkInterface sink, int ind) {
	ArrayBlockingQueue<Notification> msgs = messages.get(ind);
	Notification note = null;
	    for (int i=0;i<msgs.size();i++) {
		try {
		    note = msgs.take();
		    sink.notify(note);
		} catch (Exception e) {
		    msgs.add(note);
		}
	    }
    }

	public synchronized String getName() throws RemoteException{ return this.name; }

	@Override
	public synchronized void register(NotificationSinkInterface sink) {
	    
		if (!registeredSinks.contains(sink)) {
			registeredSinks.add(sink);
			messages.add(new ArrayBlockingQueue<Notification>(10));
		}
	}

	@Override
	public synchronized void unregister(NotificationSinkInterface sink){
		if (registeredSinks.contains(sink)) {
		    messages.remove(registeredSinks.indexOf(sink));
			registeredSinks.remove(sink);
			
		    } 
	}
}
