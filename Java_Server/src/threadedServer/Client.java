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

	public static void main() throws IOException, ClassNotFoundException {
		try (
				// On se connecte au serveur
				Socket s = new Socket("localhost", 8080);) {

			// On récupère les flux d'entrée et de sortie
			InputStream in = s.getInputStream();
			OutputStream out = s.getOutputStream();
			ObjectInputStream ois = new ObjectInputStream(in);
			ObjectOutputStream oos = new ObjectOutputStream(out);
			Integer I = (Integer) ois.readObject();

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

}
