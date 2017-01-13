/*
 * Keith Williams (G00324844)
 * 16/12/2016
 */

package ie.gmit.sw.client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client{
	private final String IP_ADDRESS = "35.163.94.108";
	private Socket requestSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private String message = "";
	private Scanner stdin;
	
	// Constructors
	public Client() {
		stdin = new Scanner(System.in);
	}
	
	// Open socket connection
	public void open() {
		try {
			// Create a socket to connect to the server
			requestSocket = new Socket(IP_ADDRESS, 2004);
			
			// Get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
		}
		catch(UnknownHostException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
		catch(IOException e) {
			System.out.println("ERROR: " + e.getMessage());
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
		catch(IOException e){
			System.out.println("ERROR: " + e.getMessage());
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
			do {
				message = stdin.nextLine();
			} while(message.isEmpty());
			sendMessage(message);
			
			// Enter address
			message = (String)in.readObject();
			System.out.println(message);
			do {
				message = stdin.nextLine();
			} while(message.isEmpty());
			sendMessage(message);
			
			// Enter account no.
			message = (String)in.readObject();
		    System.out.println(message);
		    message = ((Integer)getPositiveInteger()).toString();
		    sendMessage(message);
			
			// Enter username
			message = (String)in.readObject();
			System.out.println(message);
			message = stdin.next();
			stdin.nextLine();
			sendMessage(message);
			
			// Enter password
			message = (String)in.readObject();
			System.out.println(message);
			message = stdin.next();
			stdin.nextLine();
			sendMessage(message);
			
			// Print response from server
			message = (String)in.readObject();
			System.out.println(message);
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("ERROR: " + e.getMessage());
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
			message = ((Integer)getPositiveInteger()).toString();
			sendMessage(message);
			
			// Enter username
			message = (String)in.readObject();
			System.out.println(message);
			message = stdin.next();
			stdin.nextLine();
			sendMessage(message);
			
			// Enter password
			message = (String)in.readObject();
			System.out.println(message);
			message = stdin.next();
			stdin.nextLine();
			sendMessage(message);
			
			// Print response from server
			message = (String)in.readObject();
			System.out.println(message);
			
			if (!isError(message)) {
				isSuccessful = true;
			}
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("ERROR: " + e.getMessage());
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
			do {
				message = stdin.nextLine();
			} while(message.isEmpty());
			sendMessage(message);
			
			// Enter address
			message = (String)in.readObject();
			System.out.println(message);
			do {
				message = stdin.nextLine();
			} while(message.isEmpty());
			sendMessage(message);
			
			// Enter username
			message = (String)in.readObject();
			System.out.println(message);
			message = stdin.next();
			stdin.nextLine();
			sendMessage(message);
			
			// Enter password
			message = (String)in.readObject();
			System.out.println(message);
			message = stdin.next();
			stdin.nextLine();
			sendMessage(message);
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	
	// Sequence for lodging money into the users account
	public void lodge() {
		try {
			// Initialise request sequence
			sendMessage("Lodgement");
			
			// Enter amount
			message = (String)in.readObject();
			System.out.println(message);
			message = ((Float)getPositiveFloat()).toString();
			sendMessage(message);
			
			// Print response
			message = (String)in.readObject();
			System.out.println(message);
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	
	// Sequence for withdrawing money from the current users account
	public void withdraw() {
		try {
			// Initialise request sequence
			sendMessage("Withdrawal");
			
			// Enter amount
			message = (String)in.readObject();
			System.out.println(message);
			message = ((Float)getPositiveFloat()).toString();
			sendMessage(message);
			
			// Print response
			message = (String)in.readObject();
			System.out.println(message);
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	
	// Sequence for withdrawing money from the current users account
	public void transactions() {
		try {
			// Initialise request sequence
			sendMessage("Transactions");
			
			// Print transaction information
			message = (String)in.readObject();
			System.out.println(message);
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("ERROR: " + e.getMessage());
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
		catch(IOException e){
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	
	// Continue to prompt the user for an input until they enter a positive integer and return it.
	private int getPositiveInteger() {
		int number;
		
		do {
			while (!stdin.hasNextInt()) {
				System.out.println("Please enter a positive whole number!");
				stdin.next();
			}
			
			number = stdin.nextInt();
			
			if (number < 0) {
				System.out.println("Please enter a positive whole number!");
			}
		} while(number < 0);
		
		stdin.nextLine();
		
		return number;
	}
	
	// Continue to prompt the user for an input until they enter a positive float and return it.
	private float getPositiveFloat() {
		float number;
		
		do {
			while (!stdin.hasNextFloat()) {
				System.out.println("Please enter a positive number!");
				stdin.next();
			}
			
			number = stdin.nextFloat();
			
			if (number < 0) {
				System.out.println("Please enter a positive number!");
			}
		} while(number < 0);
		
		stdin.nextLine();
		
		return number;
	}
	
	// Return true if the given message starts with "ERROR".
	// Used to check if messages returned from the server are error messages.
	private boolean isError(String msg) {
		String firstFiveLetters = msg.substring(0, Math.min(msg.length(), 5));
		return firstFiveLetters.equals("ERROR");
	}
}