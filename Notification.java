import java.io.Serializable;

public class Notification implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object info;
	private NotificationSourceInterface source;
	
	public Notification(Object o, NotificationSourceInterface src) {
		this.info = o;
		this.source = src;
	}
	
	public Object getInfo(){ return this.info; }
	
	public NotificationSourceInterface getSource() { return this.source; }
}
