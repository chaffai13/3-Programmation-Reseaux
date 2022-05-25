package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
/*
 * --------------------------------------------------------------------------------------------------------
 * 		Pour Tester le serveur utiliser (telnet localhost 1234) dans le terminal aprés l'avoir lancé
 * --------------------------------------------------------------------------------------------------------
 */

//1) Pour Créer un serveur Multi-thread il existe deux méthodes:
//a) ==> Hériter de la class "Thread"
//b) ==> Implémenter l'interface runnable
public class ServerChat extends Thread {
	private boolean isActive = true;
	protected static int nombreClients = 0;
	protected static List<Conversation> clients = new ArrayList<Conversation>();

	public static void main(String[] args) {

		// 2) Créer l'objet ServeurMT (pour créer un thread)
		// 3) appeler la méthode "start()" qui vas lancer la méthode "run()"
		new ServerChat().start();

	}

	@Override
	public void run() {

		try {
			// a) Créer un Objet ServerSocket en indiquant le numéro du port
			ServerSocket serverSocket = new ServerSocket(1234);
			System.out.println("Démarage du Serveur ...");
			while (isActive == true) {
				// b) Exécuter la méthode "accept()" pour attendre la connexion avec le Client
				Socket socket = serverSocket.accept(); 
				++nombreClients;
				// c-1) Créer l'objet Conversation (pour créer un nouveau thread) et lui
				// transmettre la socket"
				Conversation conversation = new Conversation(socket, nombreClients);
				// c-2) Ajouter a la liste de conversation
				clients.add(conversation);
				// c-3) appeler la méthode "start()" qui vas lancer la méthode "run()" de la
				// class "Conversation"
				conversation.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
