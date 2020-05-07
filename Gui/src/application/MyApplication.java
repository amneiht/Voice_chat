package application;

import java.rmi.RemoteException;

import dccan.remote.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MyApplication extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			
			
			FXMLLoader fxmlLoader = new FXMLLoader();
			Parent root = fxmlLoader.load(getClass().getResource("/application/manhinh/Chat.fxml").openStream());
			Scene sen = new Scene(root);
			sen.getStylesheets().add("/application/manhinh/boder.css");
			primaryStage.setScene(sen);
			primaryStage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		//Client.init("localhost");
		try {
			System.out.println(Client.getRmi().login("can", "1"));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		launch(args);
	}

}