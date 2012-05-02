import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class StudentAppClient {

    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_LENGTH = 500;
    
    public static void main(String [] args) {
	SwingUtilities.invokeLater(new Runnable(){
		public void run(){
		    JFrame appFrame = new JFrame("Student Application");
		    appFrame.setVisible(true);
		    appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    appFrame.setSize(FRAME_WIDTH,FRAME_LENGTH);
		    StudentAppPane app = new StudentAppPane();
		    app.init();
		    appFrame.setContentPane(app);
		}
	    });
    }
}
