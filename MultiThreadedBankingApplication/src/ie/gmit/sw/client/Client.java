/*
 * Keith Williams (G00324844)
 * 16/12/2016
 */

package ie.gmit.sw.client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client{
	private Socket requestSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String message = "";
	private Scanner stdin;
	
	// Open socket connection
	public void open() {
		String ipaddress;
		stdin = new Scanner(System.in);

		try {
			// Create a socket to connect to the server
			System.out.println("Please Enter your IP Address");
			ipaddress = stdin.next();
			requestSocket = new Socket(ipaddress, 2004);
			System.out.println("Connected to " + ipaddress + " in port 2004");
			
			// Get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
		}
		catch(UnknownHostException unknownHost) {
			System.err.println("You are trying to connect to an unknown host!");
		}
		catch(IOException ioException) {
			ioException.printStackTrace();
		}
	}
	
	// Close socket connection
	public void close() {
		try {
			in.close();
			out.close();
			requestSocket.close();
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	// Sequence for registering a new account
	public void register() {
		try {
			// Initialise request sequence
			sendMessage("Register");
			
			// Enter name
			message = (String)in.readObject();
			System.out.println(message);
			message = stdin.next();
			sendMessage(message);
			
			// Enter address
			message = (String)in.readObject();
			System.out.println(message);
			message = stdin.next();
			sendMessage(message);
			
			// Enter account no.
			message = (String)in.readObject();
			System.out.println(message);
			message = stdin.next();
			sendMessage(message);
			
			// Enter username
			message = (String)in.readObject();
			System.out.println(message);
			message = stdin.next();
			sendMessage(message);
			
			// Enter password
			message = (String)in.readObject();
			System.out.println(message);
			message = stdin.next();
			sendMessage(message);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Send a message to the server
	private void sendMessage(String msg) {
		try{
			out.writeObject(msg);
			out.flush();
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
}