package threadedServer;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServerThread extends Thread {

	// attributes
	public Socket client;
	ArrayList<Object> sentList;
	ArrayList<Object> receiveList;

	// constructor
	public ServerThread(Socket c, ArrayList<Object> sl, ArrayList<Object> rl) {
		this.client = c;
		this.sentList = sl;
		this.receiveList = rl;
	}

	// methods
	@Override
	public void run() {
		try {
			OutputStream os = this.client.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);

			InputStream is = this.client.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);

			Object obj = null;

			// lock the list
			synchronized (sentList) {

				// check if list is empty
				// if empty, disconnect client
				if (sentList.isEmpty()) {

					System.out.println("List is empty. Disconnecting client.");
					this.client.close();
					this.interrupt();
				} else {

					obj = sentList.get(sentList.size() - 1);
					sentList.remove(sentList.get(sentList.size() - 1));
				}
			}
			if (obj == null) {
				System.out.println("Sending disconnect message to client.");
				String disco = "please disconnect";
				oos.writeObject(disco);
				client.close();
			} else {
				System.out.println("Sending object and list.");
				oos.writeObject(obj);
			}

			// check if thread is interrupted
			if (!this.isInterrupted()) {
				System.out.println("Recieving object from client...");
				obj = ois.readObject();
				System.out.println("Object recieved.");

				synchronized (receiveList) {
					System.out.println("Adding objectto list" + obj);
					receiveList.add(obj);
				}
			}
			// close
			client.close();

		} catch (IOException | ClassNotFoundException e) {

			e.printStackTrace();
		}
	}
}
