/**
 * 
 */
package threadedServer;

import java.io.*;
import java.net.*;

/**
 * @author Théo FIGINI
 * @version 1.0
 */

public class Server extends Thread {

	// attributes
	private String servName;
	private String ip;
	private int port;
	private String protocol;

	public static void main(String[] args) {
		Server server = new Server("Server", "localhost", 8080, "HTTP");

		server.display();
		server.listen();
	}

	public Server(String name, String ip, int port, String protocol) {
		this.setName(name);
		this.setIp(ip);
		this.setPort(port);
		this.setProtocol(protocol);
	}

	// getters and setters
	public String getServName() {
		return servName;
	}

	public void setServName(String name) {
		this.servName = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	// Methods

	// display the server's informations
	public void display() {
		System.out.println("Server's name: " + this.getName());
		System.out.println("Server's ip: " + this.getIp());
		System.out.println("Server's port: " + this.getPort());
		System.out.println("Server's protocol: " + this.getProtocol());
	}

	// listening to clients
	public void listen() {
		try {
			while (true) {
				ServerSocket serverSocket = new ServerSocket(this.port);
				System.out.println("Server is listening on port " + this.getPort());
				Socket socket = serverSocket.accept();
				System.out.println("Client connected");

				// create a new thread for each client
				Thread thread = new Thread();
				thread.start();

				InputStream input = socket.getInputStream();
				OutputStream output = socket.getOutputStream();

				// buffer
				byte[] buffer = new byte[1024];
				int octets = input.read(buffer);
				String message = new String(buffer, 0, octets);
				System.out.println("Message received: " + message);

				// send a message to the client
				String response = "Server : " + this.servName;
				output.write(response.getBytes());

				// close the socket
				socket.close();
				serverSocket.close();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
