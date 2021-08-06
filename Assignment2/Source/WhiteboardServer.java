import java.awt.Shape;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class WhiteboardServer extends UnicastRemoteObject implements IWhiteboardServer, Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WhiteboardClientManager clientManager;
	private ArrayList<IWhiteboardItem> shapes;
	
	public WhiteboardServer() throws RemoteException{
		super();
		this.clientManager = new WhiteboardClientManager(this);
		this.shapes = new ArrayList<IWhiteboardItem>();
	}

	public void globalClientNameListResync() {
		// TODO Auto-generated method stub
		for(IWhiteboardClient client: this.clientManager){
			try{
				client.resyncClientNames();
			}catch(RemoteException e){
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public ICommunicationContext register(IWhiteboardClient client) throws RemoteException, IllegalStateException {
		if(this.clientManager.contains(client)){
			throw new IllegalStateException(String.format("Client %s has already been registered", client.toString()));
		}
		
		this.clientManager.addClient(client);
		
		ICommunicationContext clientContext = new CommunicationContext(client,this);
		System.out.printf("[+] Registered client %s%n", client.toString());
		printClients();
		return clientContext;
	}

	public void printClients() throws RemoteException {
		// TODO Auto-generated method stub
		if (this.clientManager.clientCount()>0){
			System.out.printf("%d active clients:%n%n", this.clientManager.clientCount());
			for(IWhiteboardClient client: this.clientManager){
				System.out.printf("Client:\t%s%n", client.getName());
			}
		}else{
			System.out.println("No clients.");
		}
		
	}

	@Override
	public void addShape(IWhiteboardClient client, Shape shape) throws RemoteException {
		IWhiteboardItem shapeItem = new WhiteboardItem(client, shape);
		this.shapes.add(shapeItem);
		for(IWhiteboardClient c: this.clientManager){
			c.retrieveShapes(shapeItem);
		}
		
	}

	@Override
	public void addText(IWhiteboardClient client, Text text) throws RemoteException {
		IWhiteboardItem shapeItem = new WhiteboardItem(client, text);
		this.shapes.add(shapeItem);
		for(IWhiteboardClient c: this.clientManager){
			c.retrieveShapes(shapeItem);
		}
		
		
	}

	@Override
	public ArrayList<IWhiteboardItem> getShapes() throws RemoteException {
		
		return this.shapes;
	}

	@Override
	public ArrayList<String> getClientNames() throws RemoteException {
        ArrayList<String> clientNames = new ArrayList<String>();
        for(IWhiteboardClient client : this.clientManager){
            clientNames.add(client.getName());
        }

        return clientNames;
    }
	

	@Override
	public void clearAllShapes() throws RemoteException {
        System.out.printf("[!] Removing All Items");
		this.shapes = new ArrayList<IWhiteboardItem>();
		globalShapeResync();
		
	}

	private void globalShapeResync() {
        for(IWhiteboardClient client : clientManager){
            try {
                client.resyncShapes();
            }catch(RemoteException err){
                err.printStackTrace();
            }
        }
		
	}

}
