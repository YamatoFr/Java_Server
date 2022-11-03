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
		// established a connection by provided host and port number
		try (Socket socket = new Socket("localhost", 8080)) {

			// writing to server
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			// reading from server
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// object of scanner class
			Scanner sc = new Scanner(System.in);
			String line = null;

			while (!"exit".equalsIgnoreCase(line)) {

				// reading from user
				line = sc.nextLine();

				// sending the user input to server
				out.println(line);
				// clearing stream
				out.flush();

				// displaying server reply
				System.out.println("Server replied " + in.readLine());
			}

			// closing the scanner object
			sc.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// attributes
	private String name;
	private String IP;
	private int port;
	private String protocole;

	// constructor
	public Client(String name, String IP, int port, String protocole) {
		this.name = name;
		this.IP = IP;
		this.port = port;
		this.protocole = protocole;
	}

	// methods
	public void displayInfo() {
		System.out.println("Name: " + this.name);
		System.out.println("IP: " + this.IP);
		System.out.println("Port: " + this.port);
		System.out.println("Protocole: " + this.protocole);
	}

	public void send(String msg) {
		// send message to server
		try {
			Socket socket = new Socket(this.IP, this.port);

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
			e.printStackTrace();
		}
	}

	public boolean sendMessage() {
		Scanner sc = new Scanner(System.in);
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