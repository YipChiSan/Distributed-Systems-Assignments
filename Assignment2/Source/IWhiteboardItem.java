
import java.awt.Shape;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

/**
 *
 * @author yezixin
 */
public interface IWhiteboardItem extends Remote {
    public Shape getShape() throws RemoteException;
    public IText getText() throws RemoteException;
    public Date getCreationTime() throws RemoteException;
    public IWhiteboardClient getOwner() throws RemoteException;
    public boolean isShape() throws RemoteException;
    public boolean isText() throws RemoteException;
    
    
}