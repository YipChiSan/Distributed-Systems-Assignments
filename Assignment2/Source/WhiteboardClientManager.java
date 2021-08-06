import java.rmi.RemoteException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WhiteboardClientManager implements Iterable<IWhiteboardClient> {
	private Set<IWhiteboardClient> clients;
	private WhiteboardServer server;
	
	public WhiteboardClientManager(WhiteboardServer server){
		this.clients = Collections.newSetFromMap(new ConcurrentHashMap<IWhiteboardClient, Boolean>());
		this.server = server;
		new Thread(new ClientWatchdog(this)).start();;
		
	}

	@Override
	public Iterator<IWhiteboardClient> iterator() {
		// TODO Auto-generated method stub
		return clients.iterator();
	}
	
	public void addClient(IWhiteboardClient client){
		this.clients.add(client);
		this.server.globalClientNameListResync();
	}
	
	public boolean contains(IWhiteboardClient client){
		return this.clients.contains(client);
	}
	
	class ClientWatchdog implements Runnable{
		WhiteboardClientManager manager;
		
		public ClientWatchdog(WhiteboardClientManager manager){
			this.manager = manager;
		}

		@Override
		public void run() {
			while(true){
				if(this.manager.clientCount() > 0){
					System.out.println("[+] Pinging Clients:");
				
				for(Iterator<IWhiteboardClient> iterator = this.manager.iterator(); iterator.hasNext();){
					IWhiteboardClient client = iterator.next(); 
					try{
						client.pingClients();
					} catch(RemoteException e){
                        System.err.println("[-] Lost client, removing...");
                        manager.removeClient(client);
					}
				}
				}else{
					System.out.println("[+] No clients connected");
				}
				try{
					Thread.sleep(1 * 10000);
				} catch(InterruptedException e){
					continue;
				}
			}
			
		}
		
	}

	public int clientCount() {
		// TODO Auto-generated method stub
		return this.clients.size();
	}

	public void removeClient(IWhiteboardClient client) {
		// TODO Auto-generated method stub
		this.clients.remove(client);
		this.server.globalClientNameListResync();
		
	}

}
