import java.io.Serializable;

public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;
    private Object info;
    private String source;
    
    public Notification(Object o,String src) {
	this.info = o;
	this.source = src;
    }
    
    public Object getInfo(){ return this.info; }
    
    public String getSource() { return this.source; }
}
