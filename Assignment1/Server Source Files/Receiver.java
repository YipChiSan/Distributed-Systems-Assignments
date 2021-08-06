
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.concurrent.LinkedBlockingDeque;

public class Receiver extends Thread{
	 private volatile boolean isRunning = true;
	 private LinkedBlockingDeque<Socket> queue;
	 private ServerSocket serverSocket;
	
	public Receiver(ServerSocket serverSocket, LinkedBlockingDeque<Socket> queue){
		this.serverSocket = serverSocket;
		this.queue = queue;
	}
	
	public void run(){
		System.out.println("Receiver Running...");
		
		 while(this.isRunning)
		 {
			 try {
				queue.offer(serverSocket.accept());
				System.out.println(" Receiver Waiting for..");
			} catch (IOException e) {
				System.out.println("Something get wrong with the comunication at Receiver");
			}
		 }
		 System.out.println("[Receiver] Finished");
	 }
	 
	 
	 public synchronized void stopRunning()
	 {
		 this.isRunning = false;
		 try {
				serverSocket.close();
				System.out.println("Receiver is closing server socket");
			} catch (IOException e) {
				System.out.println("Receiver is failing closing socket");
			}
	 }
}
