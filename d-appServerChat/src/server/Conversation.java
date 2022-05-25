package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Conversation extends Thread {
	protected Socket socketClient;
	protected int numero;

	public Conversation(Socket socketClient, int numero) {
		this.socketClient = socketClient;
		this.numero = numero;
	}

	public void broadCastMessage(String message, Socket socket, int numClient) {
		try {
			for (Conversation client : ServerChat.clients) {
				if (client.socketClient != socket) {
					if (client.numero == numClient || numClient == -1) {
						PrintWriter printWriter = new PrintWriter(client.socketClient.getOutputStream(), true);
						printWriter.println("Client N" + numero + ": " + message);
						printWriter.println("");
					}

				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			// d) Créer un Objet InputStream (récéption des données) et OutputStream (envoie
			// des données)
			InputStream inputStream = socketClient.getInputStream();
			// InputStreamReader ==> Pour lire un seul caractère ( 1 caractère = 4 octé =
			// 4*8 bit )
			InputStreamReader isr = new InputStreamReader(inputStream);
			// BufferedReader ==> Pour lire une chaîne de caractère
			BufferedReader br = new BufferedReader(isr);

			PrintWriter pw = new PrintWriter(socketClient.getOutputStream(), true);
			String ipClient = socketClient.getRemoteSocketAddress().toString();
			pw.println("Bienvenu, vous etes le client numéro: " + ServerChat.nombreClients);
			System.out.println("Connexion du Client Numéro: " + ServerChat.nombreClients + " IP= " + ipClient);

			while (true) {
				String req = br.readLine();
				if (req.contains("=>")) {
					String[] requestParams = req.split("=>");

					if (requestParams.length == 2) {
						String message = requestParams[1];
						int numeroClient = Integer.parseInt(requestParams[0]);
						// e) Créer une fonction qui donne le message a un seul client
						broadCastMessage(message, socketClient, numeroClient);
					}
				} else {
					// f) Créer une fonction qui donne le message a tout les clients
					broadCastMessage(req, socketClient, -1);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}