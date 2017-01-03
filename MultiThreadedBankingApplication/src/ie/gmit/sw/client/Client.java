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
			// Tell the server that the client is closing the connection so that the ClientServiceThread will finish
			sendMessage("Exit");
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
			message = ((Integer)stdin.nextInt()).toString();
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
			
			// Print response from server
			message = (String)in.readObject();
			System.out.println(message);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Sequence for logging in to an existing account
	// Return true if the user successfully logged in
	public boolean login() {
		boolean isSuccessful = false;
		
		try {
			// Initialise request sequence
			sendMessage("Login");
			
			// Enter account no.
			message = (String)in.readObject();
			System.out.println(message);
			message = ((Integer)stdin.nextInt()).toString();
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
			
			// Print response from server
			message = (String)in.readObject();
			System.out.println(message);
			
			if (message.equals("Successfully logged in!")) {
				isSuccessful = true;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return isSuccessful;
	}
	
	// Sequence for changing all the current users details
	public void changeDetails() {
		try {
			// Initialise request sequence
			sendMessage("Details");
			
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
	
	// Tell the server that the client wants to logout
	public void logout() {
		sendMessage("Logout");
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