/**
 * @author Théo FIGINI
 */

package threadedServer;

import java.io.*;
import java.net.*;
import java.util.*;

// Client Class
public class Client {

	// drive code
	public static void main(String[] args) {
		Client clientZero = new Client("Client 0", "127.0.0.1", 8080, "HTTP");

		clientZero.displayInfo();
		clientZero.sendMessage();
	}

	// attributes
	private String name;
	private String ip;
	private int port;
	private String protocole;

	// constructor
	public Client(String name, String ip, int port, String protocole) {
		this.name = name;
		this.ip = ip;
		this.port = port;
		this.protocole = protocole;
	}

	// methods
	public void displayInfo() {
		System.out.println("Name: " + this.name);
		System.out.println("IP: " + this.ip);
		System.out.println("Port: " + this.port);
		System.out.println("Protocole: " + this.protocole);
	}

	public void send(String msg) {
		// send message to server
		try {
			Socket socket = new Socket(this.ip, this.port);

			// output stream
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);

			// send message
			oos.writeObject(msg);
			oos.flush();

			// input stream
			InputStream is = socket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);

			// message from server
			String message = (String) ois.readObject();
			System.out.println("Server: " + message);

			// close streams
			ois.close();
			is.close();
			oos.close();
			os.close();
			socket.close();

		} catch (Exception e) {
			System.out.println("Erreur : " + e.getMessage());
		}
	}

	public boolean sendMessage() {
		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("Enter message: ");
			String msg = sc.nextLine();

			// exit
			if (msg.equalsIgnoreCase("exit")) {
				System.out.println("Exiting...");
				return true;
			} else {
				this.send(msg);
				return false;
			}
		}
	}
}