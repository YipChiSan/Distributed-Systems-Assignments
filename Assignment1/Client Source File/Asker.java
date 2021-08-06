

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;

import java.net.Socket;

public class Asker {

	public String request(Socket socket, String word) throws IOException
	{
		String answer = "";

		OutputStream outputStream = socket.getOutputStream();
		DataOutputStream dataOutput = new DataOutputStream(outputStream);
		dataOutput.writeUTF(word);
		//System.out.println("Sending request...\n");
		dataOutput.flush();
		InputStream inputStream = socket.getInputStream();
		DataInputStream objectInput = new DataInputStream(inputStream);
		
		answer = objectInput.readUTF();
		
		dataOutput.close();
		outputStream.close();
		objectInput.close();
		inputStream.close();
		
		socket.close();
		return answer;
	}
	
}