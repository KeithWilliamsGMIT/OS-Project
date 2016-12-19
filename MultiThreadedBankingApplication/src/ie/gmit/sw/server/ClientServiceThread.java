/*
 * Keith Williams (G00324844)
 * 16/12/2016
 */

package ie.gmit.sw.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientServiceThread extends Thread {
	private Socket clientSocket;
	private String message;
	private int clientID = -1;
	private boolean running = true;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public ClientServiceThread(Socket socket, int id) {
		clientSocket = socket;
		clientID = id;
	}

	/**
	 * The code within this run() method will be threaded. Each user will spawn a new thread when they connect to the server.
	 */
	public void run() {
		System.out.println("Accepted Client : ID - " + clientID + " : Address - "
				+ clientSocket.getInetAddress().getHostName());
		
		try {
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(clientSocket.getInputStream());
			System.out.println("Accepted Client : ID - " + clientID + " : Address - "
					+ clientSocket.getInetAddress().getHostName());

			sendMessage("Connection successful");
			
			do {
				try {
					System.out.println("client>" + clientID + "  " + message); 
					sendMessage("server got the following: " + message);
					message = (String)in.readObject();
				}
				catch(ClassNotFoundException classnot){
					System.err.println("Data received in unknown format");
				}
			} while(running);

			System.out.println("Ending Client : ID - " + clientID + " : Address - "
					+ clientSocket.getInetAddress().getHostName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sendMessage(String msg) {
		try{
			out.writeObject(msg);
			out.flush();
			System.out.println("client> " + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
}