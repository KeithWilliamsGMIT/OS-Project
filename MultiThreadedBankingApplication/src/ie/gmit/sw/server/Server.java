/*
 * Keith Williams (G00324844)
 * 16/12/2016
 */

package ie.gmit.sw.server;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = new ServerSocket(2004, 10);
		int id = 0;
		UserManager userManager = new UserManager();
		
		// Stay alive
		while (true) {
			// A blocking call that listens for new connections
			Socket clientSocket = serverSocket.accept();
			
			// If a new client connects create a new client thread
			ClientServiceThread clientThread = new ClientServiceThread(clientSocket, id++, userManager);
			clientThread.start();
		}
	}
}