
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingDeque;


public class Worker extends Thread{

	private LinkedBlockingDeque<Socket> queue;
	private volatile boolean isRunning;
	// Constructors
	public Worker(LinkedBlockingDeque<Socket> queue) {
		this.queue = queue;
		this.isRunning = true;
	}

	@Override
	public void run(){
		String threadName = Thread.currentThread().getName();
		System.out.println("[" + threadName + "] Running...");
		System.out.println("[" + threadName + "] Initalizing variables...");
		
		//Initializing variables
		Socket socket;
		DataInputStream inputStream = null;
		String word = "";
		DataOutputStream dataOutput = null;
		String result = "";
		
		while (isRunning) {
			synchronized (queue){
			while (!queue.isEmpty()) {
				//Takes the corresponding socket that was first in the queue
				socket = (Socket) queue.poll();
				
				System.out.println("[" + threadName + "] Connecting with: "
						+ socket.getPort());
				//Initiates the input stream communication
				try {
					inputStream = new DataInputStream(socket.getInputStream());
					word = inputStream.readUTF();
					System.out.println("[" + threadName + "] Word: " + word
							+ " from " + socket.getPort());

				} catch (IOException e) {
					System.out
							.println("["
									+ threadName
									+ "] Error: something get wrong with the inputStream");
				}
				
				//Get the result
				result = parseExecution(word);
				
				System.out.println("[" + threadName
						+ "] Sending result of the input query to "
						+ socket.getPort());
				
				//Output stream: response
				OutputStream output;
				try {
					output = socket.getOutputStream();
					dataOutput = new DataOutputStream(output);
					dataOutput.writeUTF(result);
				} catch (IOException e1) {
					System.out
							.println("["
									+ threadName
									+ "] Error: something get wrong while sending response");
				}

				System.out.println("[" + threadName
						+ "] Closing connections with: " + socket.getPort());
				try {
					inputStream.close();
					dataOutput.close();
					socket.close();
				} catch (Exception e) {
					System.out
							.println("["
									+ threadName
									+ "] Error: something get wrong while closing socket");
				}
			}
		}}
		System.out.println("[" + threadName + "] Finished");	
	}
	
	//Method helps breaking the while loop
	public synchronized void stopRunning()
	{
		this.isRunning = false;
	}
	
	private String parseExecution(String line){
		String result = "";
		String [] elements = line.split(":");
		switch (elements[0].charAt(0)){
		case '+': // Add a word to the dictionary
			if (elements.length != 3){
				System.out.println("Not enough arguments. Expected three arguments input");
				return "Not enough arguments. Expected three arguments input";
			}
			DictOperation dictAdd = new DictOperation("add",elements[1],elements[2]);
			result = dictAdd.run();
			break;
		case '-': // Delete a word from the dictionary
			if (elements.length != 2){
				System.out.println("Not enough arguments. Expected two arguments input");
				return "Not enough arguments. Expected three arguments input";
			}
			DictOperation dictDel = new DictOperation("del",elements[1]);
			result = dictDel.run();
			break;
		case '=': // Find a word from the dictionary
			if (elements.length != 2){
				System.out.println("Not enough arguments. Expected two arguments input");
				return "Not enough arguments. Expected three arguments input";
			}
			DictOperation dictQuery = new DictOperation("query",elements[1]);
			result = dictQuery.run();
			break;
		default:
			result = "Wrong operation. This server does not support.";
 		}
		return result;
	}
}
