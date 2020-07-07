package application;

import java.io.IOException;
import java.util.ResourceBundle;

import application.manhinh.LinkScense;
import application.manhinh.control.FriendChat;
import application.manhinh.control.Login;
import dccan.remote.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MyApplication extends Application {
	// @Override
	public void start1(Stage primaryStage) {
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

	public void start(Stage primaryStage) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		// fxmlLoader.setResources(ResourceBundle.getBundle("app.lang.vn"));
		Parent root;
		try {
			fxmlLoader.setController(new FriendChat());
			root = fxmlLoader.load(getClass().getResource(LinkScense.Friendchat).openStream());
			Scene sen = new Scene(root);
			Stage pr = new Stage();
			sen.getStylesheets().add(LinkScense.boderCSS);
			pr.setTitle("Chat với bạn bè");
			pr.setScene(sen);
			pr.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if (args.length > 0) {
			Client.init(args[0]);
		} else {
			Client.init("192.168.1.100");
			try {
				Client.getRmi().login("can", "1");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		launch(args);
	}
}