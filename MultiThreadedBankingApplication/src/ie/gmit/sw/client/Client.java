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
	private String ipaddress;
	private Scanner stdin;

	private void run() {
		stdin = new Scanner(System.in);

		try {
			// 1. creating a socket to connect to the server
			System.out.println("Please Enter your IP Address");
			ipaddress = stdin.next();
			requestSocket = new Socket(ipaddress, 2004);
			System.out.println("Connected to "+ipaddress+" in port 2004");
			// 2. get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			System.out.println("Hello");
			// 3: Communicating with the server
			do {
				try {
					message = (String)in.readObject();
					System.out.println("Please Enter the Message to send...");
					message = stdin.next();
					sendMessage(message);
				}
				catch(ClassNotFoundException classNot) {
					System.err.println("data received in unknown format");
				}
			}while(!message.equals("bye"));
		}
		catch(UnknownHostException unknownHost) {
			System.err.println("You are trying to connect to an unknown host!");
		}
		catch(IOException ioException) {
			ioException.printStackTrace();
		}
		finally {
			//4: Closing connection
			try {
				in.close();
				out.close();
				requestSocket.close();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
	}

	private void sendMessage(String msg) {
		try{
			out.writeObject(msg);
			out.flush();
			System.out.println("client>" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Client client = new Client();
		client.run();
	}
}