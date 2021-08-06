import java.awt.Shape;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author yezixin
 */
public class CommunicationContext extends UnicastRemoteObject implements ICommunicationContext, Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IWhiteboardClient client;
    private IWhiteboardServer server;
    
    public CommunicationContext(IWhiteboardClient client, IWhiteboardServer server) throws RemoteException{
        this.client = client;
        this.server = server;
    }

    @Override
    public void sendMessage(String s) throws RemoteException {
        System.out.printf("Message from %s: %s", this.client, s);
        this.client.sendMessage("Hello");
    }

    @Override
    public void addShape(Shape shape) throws RemoteException {
        server.addShape(client, shape);
    }

    @Override
    public ArrayList<IWhiteboardItem> getShapes() throws RemoteException {
        return server.getShapes();
    }



    @Override
    public ArrayList<String> getClientNameList() throws RemoteException {
        return server.getClientNames();
    }

    @Override
    public void notifyOfClientNameChange() throws RemoteException {
        this.server.globalClientNameListResync();
    }

    @Override
    public void addText(Text text) throws RemoteException {
        server.addText(client, text);
    }
    
}
