/**
 * @author Théo FIGINI
 */

package threadedServer;

import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.reflect.Field;

// Client Class
public class Client {

	// attributes
	private String name;
	private String ip;
	private int port;
	private String protocole;

	// drive code
	public static void main(String[] args) {

		Client clientZero = new Client("Client 0", "localhost", 8080, "HTTP");

		clientZero.displayInfo();

		clientZero.send();
	}

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

	public void writing(Object obj) {
		try (Scanner sc = new Scanner(System.in)) {
			ArrayList<String> primTypes = new ArrayList<>(
					Arrays.asList("int", "double", "float", "long", "short", "byte",
							"boolean", "char", "String"));

			Object value = null;
			String msg = "Invalid input";

			for (Field f : obj.getClass().getFields()) {
				System.out.println(f.getType().getSimpleName() + " : " + f.getName() + "\n");

				if (primTypes.contains(f.getType().getSimpleName())) {

					System.out.println("Enter the " + f.getName());
					Boolean invalid;

					switch (f.getType().getSimpleName()) {
						case "String":
						case "char":
							do {
								invalid = false;
								try {
									value = sc.nextLine();
								} catch (InputMismatchException e) {
									invalid = true;
									sc.nextLine();
									System.out.println(msg);
								}
							} while (Boolean.TRUE.equals(invalid));
							break;
						case "int":
						case "Integer":
							do {
								invalid = false;
								try {
									value = sc.nextInt();
								} catch (InputMismatchException e) {
									invalid = true;
									sc.nextLine();
									System.out.println(msg);
								}
							} while (invalid);
							break;
						case "double":
						case "Double":
							do {
								invalid = false;
								try {
									value = sc.nextDouble();
								} catch (InputMismatchException e) {
									invalid = true;
									sc.nextLine();
									System.out.println(msg);
								}
							} while (Boolean.TRUE.equals(invalid));
							break;
						case "float":
						case "Float":
							do {
								invalid = false;
								try {
									value = sc.nextFloat();
								} catch (InputMismatchException e) {
									invalid = true;
									sc.nextLine();
									System.out.println(msg);
								}
							} while (Boolean.TRUE.equals(invalid));
							break;
						case "long":
						case "Long":
							do {
								invalid = false;
								try {
									value = sc.nextLong();
								} catch (InputMismatchException e) {
									invalid = true;
									sc.nextLine();
									System.out.println(msg);
								}
							} while (Boolean.TRUE.equals(invalid));
							break;
						case "short":
						case "Short":
							do {
								invalid = false;
								try {
									value = sc.nextShort();
								} catch (InputMismatchException e) {
									invalid = true;
									sc.nextLine();
									System.out.println(msg);
								}
							} while (Boolean.TRUE.equals(invalid));
							break;
						case "byte":
						case "Byte":
							do {
								invalid = false;
								try {
									value = sc.nextByte();
								} catch (InputMismatchException e) {
									invalid = true;
									sc.nextLine();
									System.out.println(msg);
								}
							} while (Boolean.TRUE.equals(invalid));
							break;
						case "boolean":
						case "Boolean":
							do {
								invalid = false;
								try {
									value = sc.nextBoolean();
								} catch (InputMismatchException e) {
									invalid = true;
									sc.nextLine();
									System.out.println(msg);
								}
							} while (Boolean.TRUE.equals(invalid));
							break;
						default:
							System.out.println("Error");
					}
					try {
						f.set(obj, value);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				} else {
					try {
						writing(f.get(obj));
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	public void send() {
		Socket sock = null;

		try {
			System.out.println("Awaiting connection...");

			sock = new Socket("localhost", 8080);

			// Retrieve streams
			OutputStream out = sock.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(out);
			InputStream in = sock.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(in);

			// Read object
			System.out.println("Reading object...");
			Object obj = ois.readObject();

			if (obj instanceof String && obj.equals("exit")) {
				System.out.println("No object remaining. Disconnecting...");
				sock.close();
			} else {
				// Write values
				System.out.println("Object's class: " + obj.getClass().getSimpleName());
				System.out.println("Attributes: ");

				writing(obj);

				// Send object
				System.out.println("Sending object...");
				oos.writeObject(obj);

				// Close streams
				sock.close();
			}
		} catch (IOException | ClassNotFoundException | IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

}