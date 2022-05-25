package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/*
 * --------------------------------------------------------------------------------------------------------
 * 		Pour Tester le serveur utiliser (telnet localhost 1234) dans le terminal aprés l'avoir lancé
 * --------------------------------------------------------------------------------------------------------
 */

//1) Pour Créer un serveur Multi-thread il existe deux méthodes:
		//a) ==> Hériter de la class "Thread"
		//b) ==> Implémenter l'interface runnable
public class MyServerMT extends Thread {

	private int nombreClient;
	
	public static void main(String[] args) {

		// 2) Créer l'objet ServeurMT (pour créer un thread)
		// 3) appeler la méthode "start()" qui vas lancer la méthode "run()"
		new MyServerMT().start();

	}

	// 4) Redéfinir la méthode run
	@Override
	public void run() {

		ServerSocket ss;
		try {
			// a) Créer un Objet ServerSocket en indiquant le numéro du port
			ss = new ServerSocket(1234);
			System.out.println("Démarrage du serveur...");

			while (true) {
				// b) Exécuter la méthode "accept()" pour attendre la connexion avec le Client
				Socket s = ss.accept();
				++nombreClient;				
				// c-1) Créer l'objet Conversation (pour créer un nouveau thread) et lui transmettre la socket "s"
				// c-2) appeler la méthode "start()" qui vas lancer la méthode "run()" de la class "Conversation"
				new Conversation(s, nombreClient).start();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
