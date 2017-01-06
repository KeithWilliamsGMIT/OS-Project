/*
 * Keith Williams (G00324844)
 * 30/12/2016
 */

package ie.gmit.sw.client;

import java.util.Scanner;

public class UI {
	Client client = new Client();
	Scanner sc = new Scanner(System.in);
	
	// Start the client connection and close it again once the user exits the application
	public void start () {
		client.open();
		mainmenu();
		client.close();
	}
	
	// Display menu to allow user to register or login
	public void mainmenu() {
		int option;
		
		do {
			do {
				System.out.println("1) Register");
				System.out.println("2) Login");
				System.out.println("3) Exit");
				option = sc.nextInt();
			} while(option < 1 || option > 3);
			
			switch(option) {
			case 1:
				// Register
				client.register();
				break;
			case 2:
				// Login
				if (client.login()) {
					submenu();
				}
				break;
			}
		} while(option != 3);
	}
	
	// Display menu after user logs in
	public void submenu() {
		int option;
		
		do {
			do {
				System.out.println("1) Change Customer Details");
				System.out.println("2) Make Lodgement");
				System.out.println("3) Make Withdrawal");
				System.out.println("4) View Previous Transactions");
				System.out.println("5) Logout");
				option = sc.nextInt();
			} while(option < 1 || option > 5);
			
			switch(option) {
			case 1:
				// Change Customer Details
				client.changeDetails();
				break;
			case 2:
				// Make Lodgement
				client.lodge();
				break;
			case 3:
				// Make Withdrawal
				client.withdraw();
				break;
			case 4:
				// View Last 10 Transactions
				client.transactions();
				break;
			}
		} while(option != 5);
		
		client.logout();
	}
}
