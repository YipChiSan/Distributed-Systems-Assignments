import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class WhiteboardServerMain {
	public static final String server_name = "localhost";
	public static final int port = 1000; 

	public static void main(String[] args) {
		try{
			WhiteboardServer server = new WhiteboardServer();
			Registry reg = LocateRegistry.createRegistry(port);
			
			System.out.printf("[+] Created local registry%n");
			Naming.rebind(server_name, server);
			
			 System.out.printf("[+] Bound to %s%n", server_name);

	}catch(Exception e){
        System.err.println("An error has occurred! Server cannot be opened.");
        e.printStackTrace();
		}
	}

}
