package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//pour mieux voir le r�sultat Lancer l'appli sur ligne de commande dans 2 fen�tres diff�rentes
public class MyServer {

	public static void main(String[] args) throws IOException {

		// I) Cr�ation du Serveur

		// 1) Cr�er un objet ServerSocket et sp�cifier le num�ro du port
		ServerSocket serverSocket = new ServerSocket(1234);
		System.out.println("J'attend la connexion...");

		// 2) Cr�er l'objet Socket qui est retourn� avec la m�thode accepte()
		Socket socket = serverSocket.accept();
		System.out.println("Connexion �tablie avec un client: " + socket.getRemoteSocketAddress());

		// 3) g�n�rer les objets InputStream et OutputStream
		InputStream isServer = socket.getInputStream();
		OutputStream osServer = socket.getOutputStream();

		System.out.println("J'attend Que le client envoie un octet...");

		// 4) Je lis le nombre envoyer par le client puis je fait un calcule
		int nb = isServer.read();
		System.out.println("J'ai re�u un nombre nb= " + nb);
		int rst = nb * 5;

		// 5) j'envoie la r�ponse
		osServer.write(rst);
		System.out.println("J'envoi la r�ponse r�sultat = " + rst);

		// 6) Fermer la connexion
		socket.close();

	}

}
