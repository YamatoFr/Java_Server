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
		Server serverZero = new Server("Server 0", "192.168.1.1", 8080, "HTTP");

		serverZero.displayInfo();
		serverZero.listen();
	}

	// Attributes
	protected int serverPort = 8080;
	protected ServerSocket serverSocket = null;
	protected boolean stopped = false;
	protected Thread runningThread = null;

	private String Name;
	private String Ip;
	private int Port;
	private String protocole;

	// Constructor
	public Server(String name, String ip, int port, String protocole) {
		this.Name = name;
		this.Ip = ip;
		this.Port = port;
		this.protocole = protocole;
	}

	// Methods
	public void displayInfo() {
		// Displaying server information
		System.out.println("Name: " + this.Name);
		System.out.println("IP: " + this.Ip);
		System.out.println("Port: " + this.Port);
		System.out.println("Protocole: " + this.protocole);
	}

	private void openServerSocket() {
		try {
			this.serverSocket = new ServerSocket(this.serverPort);
		} catch (IOException e) {
			throw new RuntimeException("Cannot open port " + this.serverPort, e);
		}
	}

	private synchronized boolean isStopped() {
		return this.stopped;
	}

	public void listen() {
		// listening to port, synchronized
		synchronized (this) {
			this.runningThread = Thread.currentThread();
		}
		// open server socket
		openServerSocket();
		while (!isStopped()) {
			Socket clientSocket = null;
			try {
				clientSocket = this.serverSocket.accept();
			} catch (IOException e) {
				if (isStopped()) {
					System.out.println("Server Stopped.");
					return;
				}
				throw new RuntimeException("Error accepting client connection", e);
			}
			new Thread(new ClientHandler(clientSocket, "Multithreaded Server")).start();
		}
		System.out.println("Server Stopped.");
	}

	// ClientHandler class
	public class ClientHandler implements Runnable {

		protected Socket clientSocket = null;
		protected String serverText = null;

		// Construtor
		public ClientHandler(Socket socket, String serverText) {
			this.clientSocket = socket;
			this.serverText = serverText;
		}

		public void run() {
			try {
				// get input and objet input stream
				InputStream input = clientSocket.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(input);

				// read object
				String message = (String) ois.readObject();
				System.out.println(
						"Message Received: " + message + ". From " + clientSocket.getInetAddress().getHostAddress());

				// confirm message received
				OutputStream output = clientSocket.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(output);

				// send confirmation
				oos.writeObject("Message Received");
				oos.flush();

				// close streams
				ois.close();
				oos.close();

				// close socket
				clientSocket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}