package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

class Conversation extends Thread {

	private Socket socket;
	private int numeroClient;
	private boolean end;
	private String winner;

	public Conversation(Socket s, int n) {
		this.socket = s;
		this.numeroClient = n;
	}

	@Override
	public void run() {

		try {
			// d) Créer un Objet InputStream (récéption des données) et OutputStream (envoie
			// des données)
			InputStream is = socket.getInputStream();
			// InputStreamReader ==> Pour lire un seul caractère ( 1 caractère = 4 octé =
			// 4*8 bit )
			InputStreamReader isr = new InputStreamReader(is);
			// BufferedReader ==> Pour lire une chaîne de caractère
			BufferedReader br = new BufferedReader(isr);

			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os, true);
			String ip = socket.getRemoteSocketAddress().toString();
			System.out.println(
					"Connexion du Joueur N°= " + numeroClient + " / IP-Adresse: " + ip.substring(1, ip.length()));
			pw.println("Bienvenue! Vous êtes le Joueur N°= " + numeroClient);
			pw.println("Deviner le nombre Secret?");

			while (true) {
				String req = br.readLine();

				int number = 0;
				boolean correctFormatRequest = false;
				try {
					number = Integer.parseInt(req);
					correctFormatRequest = true;
				} catch (NumberFormatException e) {
					correctFormatRequest = false;
				}

				if (correctFormatRequest == true) {
					System.out.println(
							"Le Joueur N°:" + numeroClient + " a fait une tentative avec le numéro: " + number);
					if (end == false) {
						if (number > ServerGame.secretNumber) {
							pw.println("Le nombre est supérieur au nombre secret");
						} else if (number < ServerGame.secretNumber) {
							pw.println("Le nombre est inférieur au nombre secret");
						} else {
							pw.println("BRAVO!!! Vous avez gagné");
							winner = ip.substring(1, ip.length());
							System.out.println("Bravo au gagant, IP-Client: " + winner);
							end = true;
						}
					} else {
						pw.println("Le jeu est terminé, le gagant est: " + winner);
					}
				} else {
					pw.println("Le format du nombre est incorrect");
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}