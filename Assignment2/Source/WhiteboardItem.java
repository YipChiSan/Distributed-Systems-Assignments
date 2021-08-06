import java.awt.Shape;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

/**
 *
 * @author yezixin
 */
public class WhiteboardItem extends UnicastRemoteObject implements IWhiteboardItem{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Shape shape;
    private final IWhiteboardClient client;
    private Date creationDate;
    private IText text;
    private boolean isShape = false;
    private boolean isText = false;
    
    public WhiteboardItem(IWhiteboardClient client, Shape shape) throws RemoteException{
        this.client = client;
        this.shape = shape;
        this.isShape = true;
    }
    
    public WhiteboardItem(IWhiteboardClient client, Text text) throws RemoteException{
        this.client = client;
        this.text = text;
        this.isText = true;
    }

    @Override
    public Shape getShape() throws RemoteException {
        return this.shape;
    }

    @Override
    public IText getText() throws RemoteException {
        return this.text;
    }

    @Override
    public Date getCreationTime() throws RemoteException {
        return this.creationDate;
    }

    @Override
    public IWhiteboardClient getOwner() throws RemoteException {
        return this.client;
    }

    @Override
    public boolean isShape() throws RemoteException {
        return this.isShape;
    }

    @Override
    public boolean isText() throws RemoteException {
        return this.isText;
    }
}