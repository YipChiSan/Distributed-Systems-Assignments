
import java.awt.Shape;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author yezixin
 */
public interface ICommunicationContext extends Remote {
    public void sendMessage(String s) throws RemoteException;
    public void addShape(Shape shape) throws RemoteException;
    public void addText(Text text) throws RemoteException;
    public ArrayList<IWhiteboardItem> getShapes() throws RemoteException;
    
    
    public ArrayList<String> getClientNameList() throws RemoteException;
    public void notifyOfClientNameChange() throws RemoteException;
}
