import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;

import javax.swing.*;


public class StudentAppPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private StudentApp drawPanel;
	
	public void init(){
		this.setLayout(new BorderLayout());
		drawPanel = new StudentApp();
		drawPanel.init();
		LowerPanel lp = new LowerPanel();
		lp.init(drawPanel.getStudent());
		this.add(drawPanel,BorderLayout.CENTER);
		this.add(lp,BorderLayout.SOUTH);
	}
	
}

@SuppressWarnings("serial")
class LowerPanel extends JPanel {
	
	private JButton registerButton;
	private boolean isRegistered = false;
    private static final int MAX_TRIES = 10;

	public void init(final Student s){
		this.setLayout(new GridLayout(1,3));
		this.add(new JLabel());
		registerButton = new JButton("Register");
		this.add(registerButton);
		this.add(new JLabel());
		class registerListener implements ActionListener {
			public void actionPerformed(ActionEvent evt) {
				if (isRegistered) {
					try {
					    NotificationSourceInterface source = (NotificationSourceInterface)
						Naming.lookup("rmi://localhost/notificationSource");
					    source.unregister(s);
					    registerButton.setText("register");
					} catch (Exception ex) {
					    if (retryUnregister(s)) registerButton.setText("register");
					}
					
				} else {
					//run the register procedure
					try {
					    NotificationSourceInterface source = (NotificationSourceInterface)
						Naming.lookup("rmi://localhost/notificationSource");
					    source.register(s);
					    registerButton.setText("unregister");
					} catch (Exception ex) {
					    if (retryRegister(s)) registerButton.setText("unregister");
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
						Naming.lookup("rmi://localhost/notificationSource");
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
						Naming.lookup("rmi://localhost/notificationSource");
		source.register(sink);
		return true;
	    } catch (Exception e) {}
	}
	return false;
     }
}
