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
	private int clientId = -1;
	private boolean running = true;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private UserManager userManager;
	private User currentUser;
	private UserIO io;
	
	// Constructors
	public ClientServiceThread(Socket clientSocket, int clientId, UserManager userManager) {
		this.clientSocket = clientSocket;
		this.clientId = clientId;
		this.userManager = userManager;
		this.io = new UserIO();
	}
	
	// Methods
	// The code within this run() method will be threaded.
	// Each user will spawn a new thread when they connect to the server.
	public void run() {
		System.out.println("Accepted Client : ID - " + clientId + " : Address - "
				+ clientSocket.getInetAddress().getHostName());
		
		try {
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(clientSocket.getInputStream());
			System.out.println("Accepted Client : ID - " + clientId + " : Address - "
					+ clientSocket.getInetAddress().getHostName());
			
			do {
				try {
					message = (String)in.readObject();
					System.out.println("Client " + clientId + "> " + message);
					
					// Determine what sequence to start
					switch(message) {
					case "Register":
						register();
						break;
					case "Login":
						login();
						break;
					case "Details":
						changeDetails();
						break;
					case "Lodgement":
						lodgement();
						break;
					case "Withdrawal":
						withdrawal();
						break;
					case "Transactions":
						transactions();
						break;
					case "Logout":
						logout();
						break;
					case "Exit":
						running = false;
						break;
					}
				}
				catch(ClassNotFoundException classnot){
					System.err.println("Data received in unknown format");
				}
			} while(running);

			System.out.println("Ending Client : ID - " + clientId + " : Address - "
					+ clientSocket.getInetAddress().getHostName());
		} catch (Exception e) {
			System.out.println("ERROR Client : ID - " + clientId + " : " + e.getMessage());
		}
	}
	
	// Create a new user object with the variables supplied by the client.
	// Register the user if the account number is unique
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
				
				// Save user
				io.saveUser(user);
				
				sendMessage("Successfully registered!");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Ask for the users credentials. If they match the credentials of a registered user
	// and that user is not already logged in, then login the user.
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
			
			// Try to login
			try {
				// If the user logged in successfully send them their current balance
				currentUser = userManager.loginUser(user);
				
				// Save user
				io.saveUser(currentUser);
				
				sendMessage("Current balance: €" + currentUser.getAccount().getFormattedBalance());
			} catch(Exception e) {
				// If an error occurred send the user the error message
				sendMessage("ERROR: " + e.getMessage());
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Ask the user to re-enter all the details they entered when they registered except their account number.
	private void changeDetails() {
		try {
			// Get name from client
			sendMessage("Enter name (" + currentUser.getName() + ")");
			message = (String)in.readObject();
			currentUser.setName(message);
			
			// Get address from client
			sendMessage("Enter address (" + currentUser.getAddress() + ")");
			message = (String)in.readObject();
			currentUser.setAddress(message);
			
			// Get username from client
			sendMessage("Enter username (" + currentUser.getUsername() + ")");
			message = (String)in.readObject();
			currentUser.setUsername(message);
			
			// Get password from client
			sendMessage("Enter password (" + currentUser.getPassword() + ")");
			message = (String)in.readObject();
			currentUser.setPassword(message);
			
			// Save user
			io.saveUser(currentUser);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Ask the user to enter the amount they would like to lodge into their account
	private void lodgement() {
		try {
			float amount;
			
			// Get amount to lodge from client
			sendMessage("Enter the amount you would like to lodge into your account");
			amount = Float.parseFloat((String) in.readObject());
			
			currentUser.getAccount().lodge(amount);
			
			// Save user
			io.saveUser(currentUser);
			
			sendMessage("New balance is €" + currentUser.getAccount().getFormattedBalance());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Ask the user to enter the amount they would like to withdraw from their account
	private void withdrawal() {
		try {
			float amount;
			
			// Get amount to withdraw from client
			sendMessage("Enter the amount you would like to withdraw from your account");
			amount = Float.parseFloat((String) in.readObject());
			
			// Check if the amount exceeds either the credit limit or the users credit balance
			if (amount > 1000) {
				sendMessage("ERROR: The amount you entered exceeded the credit limit of €1000.00");
			} else if (currentUser.getAccount().getBalance() < amount) {
				sendMessage("ERROR: The amount you entered exceeded your current balance of €" + currentUser.getAccount().getFormattedBalance());
			} else {
				currentUser.getAccount().withdraw(amount);
				
				// Save user
				io.saveUser(currentUser);
				
				sendMessage("New balance is €" + currentUser.getAccount().getFormattedBalance());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Send the client a list of previous transactions
	private void transactions() {
		// Send transaction information to client
		sendMessage(currentUser.getAccount().getTransactions());
	}
	
	// Log the current user out.
	private void logout() {
		userManager.logoutUser(currentUser);
		currentUser = null;
	}
	
	// Send the given message to the client.
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