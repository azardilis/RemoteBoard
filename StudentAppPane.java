import javax.swing.*;
import java.rmi.*;
import java.net.MalformedURLException;

public class StudentAppPane extends JTabbedPane {

    public void init() {
	Student student;
	StudentAppPanel sa = new StudentAppPanel();
	sa.init(0);
	StudentAppPanel sa1 = new StudentAppPanel();
	sa1.init(1);
	this.add("T1",sa);
	this.add("T2",sa1);
      	try {
	    student = new Student(sa,sa1);
	    Naming.rebind("notificationSink",student);
	    sa.setStudent(student);
	    sa1.setStudent(student);
	} catch (RemoteException e) {
	    e.printStackTrace();
	} catch (MalformedURLException e) {
	    e.printStackTrace();
	}
	
    }
}