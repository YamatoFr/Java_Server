/**
 * 
 */
package threadedServer;

import java.io.*;
import java.net.*;

/**
 * @author Théo FIGINI
 */
public class Client {

	// attributes
	private String name;
	private String ip;
	private int port;
	private String protocol;

	public static void main(String[] args) {
		Client client = new Client("Server", "localhost", 8080, "HTTP");

		client.display();
		client.send();
	}

	public Client(String name, String ip, int port, String protocol) {
		this.setName(name);
		this.setIp(ip);
		this.setPort(port);
		this.setProtocol(protocol);
	}

	// getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	// methods

	private void display() {
		System.out.println("Server's name: " + this.getName());
		System.out.println("Server's ip: " + this.getIp());
		System.out.println("Server's port: " + this.getPort());
		System.out.println("Server's protocol: " + this.getProtocol());
	}

	private void send() {
		try {
			Socket socket = new Socket(this.getIp(), this.port);

			OutputStream outStream = socket.getOutputStream();
			outStream.write("Hello".getBytes());

			outStream.close();
			socket.close();

		} catch (IOException e) {
			System.out.println(e);
		}
	}

}
