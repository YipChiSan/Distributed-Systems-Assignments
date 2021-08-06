




import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author yezixin
 */
public interface IWhiteboardClient extends Remote {
    public void sendMessage(String message) throws RemoteException;
    public String getName() throws RemoteException;
    public void setName(String names) throws RemoteException;
    public double pingClients() throws RemoteException;
    public void retrieveShapes(IWhiteboardItem shape) throws RemoteException;
    public ICommunicationContext getContext() throws RemoteException;
    public void addItemListener(IWhiteboardItemListener listener) throws RemoteException;
    public void addClientListener(IWhiteboardClientListener listener) throws RemoteException;
    public void resyncShapes() throws RemoteException;
    public void resyncClientNames() throws RemoteException;
}
