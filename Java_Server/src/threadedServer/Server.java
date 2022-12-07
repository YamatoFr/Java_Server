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

	// Attributes
	protected int serverPort = 8080;
	protected ServerSocket serverSocket = null;

	private String name;
	private String ip;
	private int port;
	private String protocole;

	private static ArrayList<Object> sentList = new ArrayList<>();
	private static ArrayList<Object> receivedList = new ArrayList<>();

	public static void main(String[] args) {
		Server serverZero = new Server("Server 0", "127.0.0.1", 8080, "HTTP");

		serverZero.displayInfo();
		serverZero.listen();
	}

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

	public void listen() {
		ServerSocket sock;

		for (int i = 0; i < 6; i++) {
			Form form = new Form();

			sentList.add(form);
		}

		try {
			sock = new ServerSocket(this.port);
			System.out.println("Server is listening on port " + this.port);

			while (true) {
				// accept client connection
				System.out.println("Awaiting for client connection...");
				Socket client = sock.accept();
				System.out.println("Client connected.");

				// create new thread
				ServerThread st = new ServerThread(client, sentList, receivedList);
				System.out.println("New thread created. Launching thread...");
				st.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}