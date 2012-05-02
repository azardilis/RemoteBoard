import javax.swing.*;

public class StudentAppPane extends JTabbedPane {

    public void init() {
	StudentAppPanel sa = new StudentAppPanel();
	sa.init(0);
	StudentAppPanel sa1 = new StudentAppPanel();
	sa1.init(1);
	this.add("T1",sa);
	this.add("T2",sa1);

    }
}