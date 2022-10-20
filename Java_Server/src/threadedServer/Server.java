/**
 * @author Théo FIGINI
 * @version 1.0
 */

package threadedServer;

import java.io.*;
import java.net.*;

// Server class
class Server {
	public static void main(String[] args) {
		ServerSocket server = null;

		try {

			// server is listening on port "8080"
			server = new ServerSocket(8080);
			server.setReuseAddress(true);

			// running infinite loop for geting client request
			while (true) {

				// returns new socket
				Socket client = server.accept();
				// returns remote IP address to which the socket is connected or returns null is
				// the socket is not connected
				InetAddress addy = client.getInetAddress();
				// returns raw IP address in a string format
				String remoteIp = addy.getHostAddress();

				// Displaying that new client is connected to server
				System.out.println("New client connected" + addy + remoteIp);

				// create a new thread object
				ClientHandler clientSock = new ClientHandler(client);

				// This thread will handle the client seperately
				new Thread(clientSock).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (server != null) {
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// ClientHandler class
	private static class ClientHandler implements Runnable {

		private final Socket clientSocket;

		// Construtor
		public ClientHandler(Socket socket) {
			this.clientSocket = socket;
		}

		public void run() {
			PrintWriter out = null;
			BufferedReader in = null;

			try {

				// get the outputstream of client
				out = new PrintWriter(clientSocket.getOutputStream(), true);

				// get the inputStream of client
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

				String line;
				while ((line = in.readLine()) != null) {

					// Writing the recieved message from client
					System.out.printf(" Sent from the client: %s\n", line);
					out.println(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (out != null) {
						out.close();
					}
					if (in != null) {
						in.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}