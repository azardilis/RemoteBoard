import java.rmi.Naming;


public class SourceClient {

	public static void main(String [] args) {
		try {
			NotificationSource ns = new NotificationSource();
			Naming.rebind("notificationSource",ns);
			System.out.println("notification source ready");
			Thread.sleep(10000);
			ns.fireEvent(new String("hellooo"));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
