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
	private UserManager userManager;
	
	// Constructors
	public ClientServiceThread(Socket socket, int id, UserManager userManager) {
		this.clientSocket = socket;
		this.clientID = id;
		this.userManager = userManager;
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
			
			do {
				try {
					message = (String)in.readObject();
					System.out.println("Client " + clientID + "> " + message);
					
					// Determine what sequence to start
					switch(message) {
					case "Register":
						register();
						break;
					case "Login":
						login();
						break;
					}
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
	
	private void register() {
		try {
			String name, address, username, password;
			int accountNumber;
			
			// Get name from client
			sendMessage("Enter name");
			name = (String)in.readObject();
			
			// Get address from client
			sendMessage("Enter address");
			address = (String)in.readObject();
			
			// Get account number from client
			sendMessage("Enter account no.");
			accountNumber = Integer.parseInt((String) in.readObject());
			
			// Get username from client
			sendMessage("Enter username");
			username = (String)in.readObject();
			
			// Get password from client
			sendMessage("Enter password");
			password = (String)in.readObject();
			
			User user = new User(name, address, accountNumber, username, password);
			
			// Check if the user is already registered
			if (userManager.isUserRegistered(user)) {
				// If this user is registered send an error message to the client
				sendMessage("ERROR: A user with the given account number is already registered!");
			} else {
				// If the user is not registered, register the new user and send a success message to the client
				userManager.registerUser(user);
				sendMessage("Successfully registered!");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void login() {
		try {
			String username, password;
			int accountNumber;
			
			// Get account number from client
			sendMessage("Enter account no.");
			accountNumber = Integer.parseInt((String) in.readObject());
			
			// Get username from client
			sendMessage("Enter username");
			username = (String)in.readObject();
			
			// Get password from client
			sendMessage("Enter password");
			password = (String)in.readObject();
			
			User user = new User("", "", accountNumber, username, password);
			
			// Check if the user logged in successfully
			if (userManager.loginUser(user)) {
				// If the login is successful send a success message to the client
				sendMessage("Successfully logged in!");
			} else {
				// If the login is unsuccessful send an error message to the client
				sendMessage("ERROR: Incorrect account credentials or you are already logged in!");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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