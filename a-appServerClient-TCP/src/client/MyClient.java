package client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

//pour mieux voir le r�sultat Lancer l'appli sur ligne de commande dans 2 fen�tres diff�rentes
public class MyClient {

	public static void main(String[] args) throws Exception {
		
		// II) Connexion au serveur cot� Client

		// 1) Cr�er l'objet Socket et lui donner l'adresse IP et le port
		System.out.println("Je me connect au serveur...");
		Socket socket = new Socket("localhost", 1234);

		// 2) g�n�rer les objets InputStream et OutputStream
		InputStream isClient = socket.getInputStream();
		OutputStream osClient = socket.getOutputStream();

		// 3) Saisir un nombre
		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez saisir un nombre au clavier");
		int nb = sc.nextInt();

		// 4) j'envoie la r�ponse
		osClient.write(nb);
		System.out.println("J'envoi le nombre nb...");

		// 4) Je lis le r�sultat envoyer par serveur
		System.out.println("J'attend la r�ponse du serveur...");
		int nbResponse = isClient.read();

		System.out.println("La r�ponse du serveur est: " + nbResponse);
	}

}
