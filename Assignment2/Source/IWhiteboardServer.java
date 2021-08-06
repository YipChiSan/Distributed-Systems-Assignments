import java.awt.Shape;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IWhiteboardServer extends Remote {
    public ICommunicationContext register(IWhiteboardClient client) throws RemoteException;
    public void addShape(IWhiteboardClient client, Shape shape) throws RemoteException;
    public void addText(IWhiteboardClient client, Text text) throws RemoteException;
    public ArrayList<IWhiteboardItem> getShapes() throws RemoteException;
    public ArrayList<String> getClientNames() throws RemoteException;
    public void globalClientNameListResync() throws RemoteException;
    public void clearAllShapes() throws RemoteException;
    
}
