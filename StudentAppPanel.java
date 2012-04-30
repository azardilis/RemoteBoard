import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;

import javax.swing.*;

public class StudentAppPanel extends JPanel {

	private StudentApp drawPanel;
	
	public void init(){
		System.out.println("init drawPanel");
		this.setLayout(new BorderLayout());
		drawPanel = new StudentApp();
		drawPanel.init();
		LowerPanel lp = new LowerPanel();
		lp.init(drawPanel.getStudent());
		this.add(drawPanel,BorderLayout.CENTER);
		this.add(lp,BorderLayout.SOUTH);
	}
	
}

class LowerPanel extends JPanel {
	
	private JButton registerButton;
	private boolean isRegistered = false;
	
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
					} catch (Exception ex) {
					    ex.printStackTrace();
					}
					registerButton.setText("register");
				} else {
					//run the register procedure
					try {
					    NotificationSourceInterface source = (NotificationSourceInterface)
						Naming.lookup("rmi://localhost/notificationSource");
					    source.register(s);
					} catch (Exception ex) {
					    ex.printStackTrace();
					}
					registerButton.setText("unregister");
				}
				isRegistered =! isRegistered;
			}
		}
		registerButton.addActionListener(new registerListener());
	}
}
