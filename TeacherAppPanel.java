import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;


public class TeacherAppPanel extends JPanel {

    private TeacherApp ta;

    public void init(int id){
	this.setLayout(new BorderLayout());
	this.ta = new TeacherApp();
	ta.init(id);
	LowerPanelTeacher lp = new LowerPanelTeacher();
	lp.init(ta);
	this.add(ta,BorderLayout.CENTER);
	this.add(lp,BorderLayout.SOUTH);
    }
}

@SuppressWarnings("serial")
class LowerPanelTeacher extends JPanel {
	
    private TeacherApp ta;
    
    public void init(TeacherApp t){
	this.setLayout(new GridLayout(1,3));
	JButton clearButton = new JButton("Clear");
	JButton chooseColour = new JButton("Colour");
	this.ta = t;
	class clearListener implements ActionListener {
	    public void actionPerformed(ActionEvent evt) {
		ta.clear();
	    }
	}
	
	class changeColour implements ActionListener {
	    public void actionPerformed(ActionEvent evt) {
		ColorChooser.createAndShowGUI(ta);
	    }
	}
	chooseColour.addActionListener(new changeColour());
	this.add(clearButton);
	this.add(chooseColour);
	clearButton.addActionListener(new clearListener());
    }
}
