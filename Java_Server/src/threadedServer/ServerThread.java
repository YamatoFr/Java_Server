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

			// lock the list
			synchronized (sentList) {

				// check if list is empty
				// if empty, disconnect client
				if (sentList.isEmpty()) {

					System.out.println("List is empty. Disconnecting client.");
					this.client.close();
					this.interrupt();
				} else {

					Object obj = sentList.get(sentList.size() - 1);
					sentList.remove(sentList.get(sentList.size() - 1));
					oos.writeObject(obj);
				}
			}

			// check if thread is interrupted
			if (!this.isInterrupted()) {
				synchronized (receiveList) {
					Object obj = ois.readObject();
					System.out.println("Adding object to list...\n" + obj);
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
