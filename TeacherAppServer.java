import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class TeacherAppServer {

    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_LENGTH = 500;
    
    public static void main(final String [] args) {
	SwingUtilities.invokeLater(new Runnable(){
		public void run(){
		    JFrame appFrame = new JFrame("Teacher Application");
		    appFrame.setVisible(true);
		    appFrame.setSize(FRAME_WIDTH,FRAME_LENGTH);
		    appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    TeacherAppPanel source = new TeacherAppPanel();
		    source.init(Integer.parseInt(args[0]));
		    appFrame.setContentPane(source);
		}
	    });
    }
}
