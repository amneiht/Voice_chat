package application;

import java.rmi.RemoteException;
import java.util.ResourceBundle;

import application.manhinh.LinkScense;
import application.manhinh.control.Login;
import dccan.remote.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MyApplication extends Application {

	// @Override
	public void start(Stage primaryStage) {
		try {

			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setResources(ResourceBundle.getBundle("app.lang.vn"));
			fxmlLoader.setController(new Login());
			Parent root = fxmlLoader.load(getClass().getResource(LinkScense.login).openStream());
			Scene sen = new Scene(root);
			primaryStage.setTitle("Chat App");
			primaryStage.setScene(sen);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void start2(Stage primaryStage) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setResources(ResourceBundle.getBundle("app.lang.vn"));
			Parent root = fxmlLoader.load(getClass().getResource(LinkScense.chat).openStream());
			Scene sen = new Scene(root);
			sen.getStylesheets().add("/application/manhinh/boder.css");
			primaryStage.setScene(sen);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if (args.length > 0) {
			Client.init(args[0]);
		} else {
			// test mode
			Client.init("localhost");
			try {
				Client.getRmi().login("can", "1");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		launch(args);
	}

}