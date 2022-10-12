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

public class Server {

	public static void main() throws IOException, ClassNotFoundException {
		try (
				// écoute sur le port 8080
				ServerSocket listen = new ServerSocket(8080)) {

			// Accepte les demandes de connexion
			Socket s = listen.accept();

			// On récupère les flux d'entrée et de sortie
			InputStream in = s.getInputStream();
			OutputStream out = s.getOutputStream();
			ObjectInputStream ois = new ObjectInputStream(in);
			ObjectOutputStream oos = new ObjectOutputStream(out);

			// On envoie un objet
			oos.writeObject("Hello World");

			// On ferme la connexion
			s.close();

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
