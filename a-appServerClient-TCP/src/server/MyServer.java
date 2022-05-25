package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//pour mieux voir le résultat Lancer l'appli sur ligne de commande dans 2 fenêtres différentes
public class MyServer {

	public static void main(String[] args) throws IOException {

		// I) Création du Serveur

		// 1) Créer un objet ServerSocket et spécifier le numéro du port
		ServerSocket serverSocket = new ServerSocket(1234);
		System.out.println("J'attend la connexion...");

		// 2) Créer l'objet Socket qui est retourné avec la méthode accepte()
		Socket socket = serverSocket.accept();
		System.out.println("Connexion établie avec un client: " + socket.getRemoteSocketAddress());

		// 3) générer les objets InputStream et OutputStream
		InputStream isServer = socket.getInputStream();
		OutputStream osServer = socket.getOutputStream();

		System.out.println("J'attend Que le client envoie un octet...");

		// 4) Je lis le nombre envoyer par le client puis je fait un calcule
		int nb = isServer.read();
		System.out.println("J'ai reçu un nombre nb= " + nb);
		int rst = nb * 5;

		// 5) j'envoie la réponse
		osServer.write(rst);
		System.out.println("J'envoi la réponse résultat = " + rst);

		// 6) Fermer la connexion
		socket.close();

	}

}
