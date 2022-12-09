/**
 * @author Théo FIGINI
 */

package threadedServer;

import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import javax.swing.*;

// Client Class
public class Client {

	// attributes
	private String name;
	private String ip;
	private int port;
	private String protocole;

	// drive code
	public static void main(String[] args) {

		Client clientZero = new Client("Client 0", "localhost", 50262, "HTTP");

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

	public static void input(Object obj) {

		Scanner sc = new Scanner(System.in);

		ArrayList<String> primTypes = new ArrayList<>(
				Arrays.asList("String", "char", "int", "Integer", "Double", "double", "Boolean", "boolean", "float",
						"Float", "Short", "short", "Long", "long", "Byte", "byte"));

		Object value = null;

		String errorMsg = "Input error.";

		for (Field f : obj.getClass().getFields()) {

			System.out.println(f.getType().getSimpleName() + " : " + f.getName() + "\n");
			if (primTypes.contains(f.getType().getSimpleName())) {

				System.out.println("Enter " + f.getName() + "\n");
				boolean inputError;
				switch (f.getType().getSimpleName()) {
					case "String":
					case "char":
						do {
							inputError = false;
							try {

								value = sc.nextLine();

							} catch (InputMismatchException e) {
								inputError = true;
								sc.nextLine();
								System.out.println(errorMsg);
							}
						} while (inputError);

						break;

					case "int":
					case "Integer":
						do {
							inputError = false;
							try {

								value = sc.nextInt();
							} catch (InputMismatchException e) {
								inputError = true;
								sc.nextLine();
								System.out.println(errorMsg);
							}
						} while (inputError);

						break;
					case "double":
					case "Double":
						do {
							// System.out.println("coucou");
							inputError = false;
							try {

								value = sc.nextDouble();
							} catch (InputMismatchException e) {
								inputError = true;
								sc.nextLine();
								System.out.println(errorMsg);
							}
						} while (inputError);
						break;
					case "float":
					case "Float":
						do {
							inputError = false;
							try {

								value = sc.nextFloat();
							} catch (InputMismatchException e) {
								inputError = true;
								sc.nextLine();
								System.out.println(errorMsg);
							}
						} while (inputError);
						break;
					case "short":
					case "Short":
						do {
							inputError = false;
							try {

								value = sc.nextShort();
							} catch (InputMismatchException e) {
								inputError = true;
								sc.nextLine();
								System.out.println(errorMsg);
							}
						} while (inputError);
						break;
					case "byte":
					case "Byte":
						do {
							inputError = false;
							try {

								value = sc.nextByte();
							} catch (InputMismatchException e) {
								inputError = true;
								sc.nextLine();
								System.out.println(errorMsg);
							}
						} while (inputError);
						break;
					case "boolean":
					case "Boolean":
						do {
							inputError = false;
							try {

								value = sc.nextBoolean();
							} catch (InputMismatchException e) {
								inputError = true;
								sc.nextLine();
								System.out.println(errorMsg);
							}
						} while (inputError);
						break;
					case "long":
					case "Long":
						do {
							inputError = false;
							try {

								value = sc.nextLong();
							} catch (InputMismatchException e) {
								inputError = true;
								sc.nextLine();
								System.out.println(errorMsg);
							}
						} while (inputError);
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
					input(f.get(obj));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void guiInput(Object obj, GUI gui) {

		ArrayList<String> primTypes = new ArrayList<>(
				Arrays.asList("String", "char", "Character", "int", "Integer", "Double", "double", "Boolean", "boolean",
						"float", "Float", "Short", "short", "Long", "long", "Byte", "byte"));

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setLayout(new FlowLayout());

		for (Field f : obj.getClass().getFields()) {

			if (primTypes.contains(f.getType().getSimpleName())) {

				gui.showField(f.getType().getSimpleName(), f.getName(), panel);

			} else {
				try {
					JLabel label = new JLabel(f.getType().getSimpleName() + " " + f.getName());
					label.setForeground(Color.RED);
					gui.contentPane.add(label);
					guiInput(f.get(obj), gui);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void fixValues(GUI gui, Object obj, ArrayList<JPanel> jpanel, int i, ObjectOutputStream oos,
			Socket sock) {
		gui.validate.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {

				ArrayList<String> primTypes = new ArrayList<>(
						Arrays.asList("String", "char", "Character", "int", "Integer", "Double", "double", "Boolean",
								"boolean", "float", "Float", "Short", "short", "Long", "long", "Byte", "byte"));

				Object value = null;
				int j = 0;

				ArrayList<Component> field = new ArrayList<>();
				for (Component c : jpanel.get(i).getComponents()) {
					if (!c.getClass().getSimpleName().equals("JLabel")) {
						field.add(c);
					}
				}

				for (Field f : obj.getClass().getFields()) {
					if (primTypes.contains(f.getType().getSimpleName())) {
						switch (f.getType().getSimpleName()) {
							case "String":
								value = ((JTextArea) field.get(j)).getText();
								break;
							case "char":
							case "Character":
								value = ((JTextField) field.get(j)).getText();
								break;

							case "int":
							case "Integer":
								value = ((JFormattedTextField) field.get(j)).getValue();
								break;
							case "double":
							case "Double":
								value = ((JFormattedTextField) field.get(j)).getValue();
								break;
							case "float":
							case "Float":
								value = Float.parseFloat(((JFormattedTextField) field.get(j)).getText());
								break;
							case "short":
							case "Short":
								value = ((JFormattedTextField) field.get(j)).getValue();
								break;
							case "byte":
							case "Byte":
								value = ((JFormattedTextField) field.get(j)).getValue();
								break;
							case "boolean":
							case "Boolean":
								value = ((JComboBox<Boolean>) field.get(j)).getSelectedItem();
								break;
							case "long":
							case "Long":
								value = ((JFormattedTextField) field.get(j)).getValue();
								break;
							default:
								System.out.println("Erreur");
						}
						try {
							f.set(obj, value);
							j++;
						} catch (IllegalArgumentException | IllegalAccessException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					} else {
						try {
							fixValues(gui, f.get(obj), jpanel, i + 1, oos, sock);
						} catch (IllegalArgumentException | IllegalAccessException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				sendObject(obj, oos, sock);
				gui.frame.setVisible(false);
				gui.frame.dispose();
			}
		});
	}

	public static void sendObject(Object obj, ObjectOutputStream oos, Socket sock) {
		// On renvoie l'objet
		System.out.println("Sending object \n" + obj);
		try {
			oos.writeObject(obj);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			sock.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void send() {
		Socket sock = null;

		try {

			System.out.println("Requesting connection");
			sock = new Socket("localhost", 50262);

			OutputStream out = sock.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(out);

			InputStream in = sock.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(in);

			System.out.println("Reading object");
			Object obj = ois.readObject();

			if (obj instanceof String && obj.equals("disconnect")) {
				System.out.println("No more objects. Disconnecting");
				sock.close();
			} else {
				System.out.println("Object's class : " + obj.getClass().getSimpleName());
				System.out.println("Attributes :");

				GUI gui = new GUI();
				gui.init();
				gui.frame.setTitle(obj.getClass().getSimpleName());

				guiInput(obj, gui);
				gui.contentPane.add(gui.validate);
				gui.frame.pack();

				ArrayList<JPanel> jpanel = new ArrayList<JPanel>();
				for (Component c : gui.contentPane.getComponents()) {
					if (c.getClass().getSimpleName().equals("JPanel")) {
						jpanel.add((JPanel) c);
					}
				}

				fixValues(gui, obj, jpanel, 0, oos, sock);
			}

		} catch (ClassNotFoundException | IllegalArgumentException | IOException e) {
			e.printStackTrace();
		}
	}
}