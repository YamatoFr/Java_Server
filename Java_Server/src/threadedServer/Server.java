/**
 * @author Théo FIGINI
 * @version 1.0
 */

package threadedServer;

import java.io.*;
import java.net.*;
import java.util.*;

// Server class
class Server {
	public static void main(String[] args) {
		Server serverZero = new Server("Server 0", "127.0.0.1", 8080, "HTTP");

		serverZero.displayInfo();
		serverZero.listen();
	}

	// Attributes
	protected int serverPort = 8080;
	protected ServerSocket serverSocket = null;
	protected boolean stopped = false;
	protected Thread runningThread = null;

	private String name;
	private String ip;
	private int port;
	private String protocole;

	private static ArrayList<Object> sentList = new ArrayList<Object>();
	private static ArrayList<Object> receivedList = new ArrayList<Object>();

	// Constructor
	public Server(String name, String ip, int port, String protocole) {
		this.name = name;
		this.ip = ip;
		this.port = port;
		this.protocole = protocole;
	}

	// Methods
	public void displayInfo() {
		// Displaying server information
		System.out.println("Name: " + this.name);
		System.out.println("IP: " + this.ip);
		System.out.println("Port: " + this.port);
		System.out.println("Protocole: " + this.protocole);
	}

	private void openServerSocket() {
		try {
			this.serverSocket = new ServerSocket(this.serverPort);
		} catch (Exception e) {
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
			} catch (Exception e) {
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
				while (true) {
					// confirm message received
					OutputStream output = clientSocket.getOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(output);

					// get input and objet input stream
					InputStream input = clientSocket.getInputStream();
					ObjectInputStream ois = new ObjectInputStream(input);

					// read object
					String message = (String) ois.readObject();
					System.out.println(
							"Message Received: " + message + ". From "
									+ clientSocket.getInetAddress().getHostAddress());

					// send confirmation
					oos.writeObject("Message Received");
					oos.flush();

					// if message is exit, close connection
					if (message.equalsIgnoreCase("exit")) {
						ois.close();
						oos.close();
						clientSocket.close();
						System.out.println("Connection closed");
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}