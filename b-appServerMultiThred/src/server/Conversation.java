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
	
	public Conversation(Socket s, int n) {
		this.socket = s;
		this.numeroClient = n;
	}

	@Override
	public void run() {
		

		try {
			//d) Créer un Objet  InputStream (récéption des données) et OutputStream (envoie des données)
			InputStream is = socket.getInputStream();
			//InputStreamReader ==> Pour lire un seul caractère ( 1 caractère = 4 octé = 4*8 bit )
			InputStreamReader isr = new InputStreamReader(is);
			//BufferedReader ==> Pour lire une chaîne de caractère			
			BufferedReader br = new BufferedReader(isr);
			
			
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os, true);
			String ip = socket.getRemoteSocketAddress().toString();
			System.out.println("Connexion du client numéro " + numeroClient + " / IP-Adresse: "+ ip.substring(1, ip.length()) );
			pw.println("Bienvenue! Vous êtes le Client numéro " +numeroClient);
			pw.println("Veuillez saisir quelque chose ?");
			
			while (true) {
				String req = br.readLine();
				System.out.println("Le client numéro " + numeroClient + " a envoyé la requete: " + req);
				pw.println(req.length());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}