package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

//1) Pour utiliser l'interface graphique il faut Hériter de la class "Application"
public class ClientChat extends Application{
	
	PrintWriter pw;

	public static void main(String[] args) {
		//3) appeler la méthode launch qui va éxécuté la méthode start
		launch(args);
	}

	//2) redéfinir la méthode start
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		// Donner un nom à l'interface graphique	
		primaryStage.setTitle("Client Chat");
		
		BorderPane borderPane = new BorderPane();
		
/*
 * --------------------------------------------------------------------------------------------------------
 * 										1) Créer Le Top (Etablir la connexion)
 * --------------------------------------------------------------------------------------------------------
 */		
		
		//ajouter un label pour le HOST
		Label labelHost = new Label("Host");
		//ajouter une zone de text pour le HOST
		TextField textFieldHost = new TextField("localhost");
		//ajouter un label pour le PORT
		Label labelPort = new Label("Port");
		//ajouter une zone de text pour le PORT
		TextField textFieldPort = new TextField("1234");
		//ajouter un Boutton
		Button buttonConnect = new Button("Connexion");
		

		HBox hBox = new HBox();
		//Ajouter de l'éspace entre les éléments 10px
		hBox.setSpacing(10);
		//Ajouter un padding pour chaques éléments
		hBox.setPadding(new Insets(10));
		//Donner une couleur à ma box
		hBox.setBackground(new Background(new BackgroundFill(Color.ROYALBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		//Mettre mes élément dans une box 
		hBox.getChildren().addAll(labelHost, textFieldHost, labelPort, textFieldPort, buttonConnect);		
		//Placer mes éléments en haut du borderPane
		borderPane.setTop(hBox);

/*
 * --------------------------------------------------------------------------------------------------------
 * 										2) Créer Le Body (afficher les message)
 * --------------------------------------------------------------------------------------------------------
 */	
		
		//Créer une box 
		VBox vBox = new VBox();
		vBox.setSpacing(10);
		vBox.setPadding(new Insets(10));
		
		//Créer une ObservableList pour pouvoir ajouter les réponses
		ObservableList<String> listModel = FXCollections.observableArrayList();
		
		//Créer une listeView pour afficher les réponses
		ListView<String> listView = new ListView<String>(listModel);
		//Mettre mes élément dans une box 
		vBox.getChildren().add(listView);
		//Placer la ListView au centre
		borderPane.setCenter(vBox);

/*
 * --------------------------------------------------------------------------------------------------------
 * 									  3) Créer Le Bottom (Ecrir et envoyer les messages)
 * --------------------------------------------------------------------------------------------------------
 */	
		//ajouter un label pour le Message
		Label labelMessage = new Label("Message");
		//ajouter une zone de text pour le HOST
		TextField textFieldMessage = new TextField();
		//Configurer la largeur et la hauteur
		textFieldMessage.setPrefSize(600, 30);
		//ajouter un Boutton
		Button buttonSend = new Button("Envoyer");		
		//Créer une box 
		HBox hBox2 = new HBox();
		//Ajouter de l'éspace entre les éléments 10px
		hBox2.setSpacing(10);
		//Ajouter un padding pour chaques éléments
		hBox2.setPadding(new Insets(10));		
		//Mettre mes élément dans une box 
		hBox2.getChildren().addAll(labelMessage, textFieldMessage, buttonSend);		
		//Placer mes éléments en haut du borderPane
		borderPane.setBottom(hBox2);		
		
/*
 * --------------------------------------------------------------------------------------------------------
 * 										Ajouter les 3 Parties à ma Scene
 * --------------------------------------------------------------------------------------------------------
 */		
		
		//Ajouter le borderPane à ma scène et lui donner une largeur et une hauteur
		Scene scene = new Scene(borderPane,800, 600);
		//Utiliser la nouvelle scène que j'ai créé
		primaryStage.setScene(scene);
		// Afficher une interface graphique
		primaryStage.show();

/*
 * --------------------------------------------------------------------------------------------------------
 * 										Gérer les évenement connect et send
 * --------------------------------------------------------------------------------------------------------
 */	
		
		//Gérer les évenements buttonConnect
		buttonConnect.setOnAction((evt)->{
			
			//Récupérer l'Host
			String host = textFieldHost.getText();
			//Récupérer le Port
			int port = Integer.parseInt(textFieldPort.getText());
			
			try {
				//Etablir une connexion avec la création de l'objet socket
				Socket socket = new Socket(host, port);
				//Créer un Objet  InputStream (récéption des données) et OutputStream (envoie des données)
				InputStream inputStream = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(inputStream);	
				BufferedReader br = new BufferedReader(isr);
				
				pw = new PrintWriter(socket.getOutputStream(), true);
				
				//Créer un thread qui exécute une fonction (pour attendre la réponse) ==> expression lambda
				new Thread(() -> {

					while (true) {
						try {
							String respons = br.readLine();
							
							Platform.runLater(()->{
								// Ajouter la réponse a listModel
								listModel.add(respons);								
							});

						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}).start();

			} catch (IOException e) {
				e.printStackTrace();
			}
			
		});		
		
		//Gérer les évenements buttonSend
		buttonSend.setOnAction((evt)->{
			String message = textFieldMessage.getText();
			pw.println(message);
		});		
				
		
	}
}
