import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import javax.swing.*;

public class StudentAppPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private StudentApp drawPanel;
    private Student st;
    
    public void init(int id){
	this.setLayout(new BorderLayout());
	drawPanel = new StudentApp();
	drawPanel.init();
	LowerPanel lp = new LowerPanel();
	lp.init(this,id);
	this.add(drawPanel,BorderLayout.CENTER);
	this.add(lp,BorderLayout.SOUTH);
    }

    public void passSignal(WriteSignal ws) {
	drawPanel.interpretSignal(ws);
    }
    
    public void setStudent(Student s){
	this.st = s;
    }
    
    public Student getStudent(){return this.st;}
	
}

@SuppressWarnings("serial")
class LowerPanel extends JPanel {
	
    private JButton registerButton;
    private boolean isRegistered = false;
    private static final int MAX_TRIES = 10;
    private boolean isRegistered1 = false;
    private int ident;
    
    public void init(final StudentAppPanel s, final int id){
	this.setLayout(new GridLayout(1,4));
	this.add(new JLabel());
	registerButton = new JButton("Register");
	this.add(registerButton);
	this.add(new JLabel());
	this.ident = id;
	class registerListener implements ActionListener {
	    public void actionPerformed(ActionEvent evt) {
		if (isRegistered) {
		    try {
			NotificationSourceInterface source = (NotificationSourceInterface)
			    Naming.lookup(new String("rmi://localhost/notificationSource"+Integer.toString(ident)));
			source.unregister(s.getStudent());
			registerButton.setText("register");
		    } catch (Exception ex) {
			if (retryUnregister(s.getStudent())) registerButton.setText("register");
		    }
		} else {
		    //run the register procedure
		    try {
			NotificationSourceInterface source = (NotificationSourceInterface)
			    Naming.lookup(new String("rmi://localhost/notificationSource"+Integer.toString(ident)));
			source.register(s.getStudent());
			registerButton.setText("unregister");
		    } catch (Exception ex) {
			if (retryRegister(s.getStudent())) registerButton.setText("unregister");
		    }
		}
		isRegistered =! isRegistered;
	    }
	}
	registerButton.addActionListener(new registerListener());
    }

    public boolean retryUnregister(NotificationSinkInterface sink){
	for (int i=0;i<MAX_TRIES;i++) {
	    try {
		NotificationSourceInterface source = (NotificationSourceInterface)
		    Naming.lookup(new String("rmi://localhost/notificationSource"+Integer.toString(ident)));
		source.unregister(sink);
		return true;
	    } catch (Exception e) {}
	}
	return false;
    }
    
    public boolean retryRegister(NotificationSinkInterface sink){
      	for (int i=0;i<MAX_TRIES;i++) {
	    try {
		NotificationSourceInterface source = (NotificationSourceInterface)
		    Naming.lookup(new String("rmi://localhost/notificationSource"+Integer.toString(ident)));
		source.register(sink);
		return true;
	    } catch (Exception e) {}
	}
	return false;
    }
}
