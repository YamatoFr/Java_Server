package threadedServer;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServerThread extends Thread {

	// attributes
	private Socket client;
	ArrayList<Object> listSend;
	ArrayList<Object> listReceive;

	// constructor
	public ServerThread(Socket c, ArrayList<Object> ls, ArrayList<Object> lr) {
		this.client = c;
		this.listSend = ls;
		this.listReceive = lr;
	}

	// methods
	@Override
	public void run() {
		try {
			OutputStream os = this.client.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);

			InputStream is = this.client.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);

			// lock the list
			synchronized (listSend) {
				// check if list is empty
				// if empty, disconnect client
				if (listSend.isEmpty()) {
					System.out.println("List is empty. Disconnecting client.");
					this.client.close();
					this.interrupt();
				} else {
					Object obj = listSend.get(listSend.size() - 1);
					listSend.remove(listSend.get(listSend.size() - 1));
					oos.writeObject(obj);
				}
			}

			// check if thread is interrupted
			if (!this.isInterrupted()) {
				// receive message from client
				Object obj = ois.readObject();
				listReceive.add(obj);
			}

			// close
			client.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
