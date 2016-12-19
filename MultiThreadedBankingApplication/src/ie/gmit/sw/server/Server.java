/*
 * Keith Williams (G00324844)
 * 16/12/2016
 */

package ie.gmit.sw.server;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = new ServerSocket(2004,10);
		int id = 0;
		
		// Stay alive
		while (true) {
			// If a new client connects create a new client thread
			Socket clientSocket = serverSocket.accept();
			ClientServiceThread clientThread = new ClientServiceThread(clientSocket, id++);
			clientThread.start();
		}
	}
}