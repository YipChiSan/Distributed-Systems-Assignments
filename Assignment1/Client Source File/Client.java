


public class Client {
	public static void main(String[] args) {
		
		if (args.length != 2) {
			System.err.println("The input format should be Java Client <address> <port>");
			System.exit(1);
		}

		String address = args[0];
		String port = args[1];
		OperationFrame myGUI = new OperationFrame(address,Integer.parseInt(port));
		myGUI.setVisible(true);
		
		
		
	}
	
}

