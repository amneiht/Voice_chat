package application;

import java.util.ResourceBundle;

import application.manhinh.LinkScense;
import application.manhinh.control.Login;
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
			fxmlLoader.setResources(ResourceBundle.getBundle("app.lang.vn"));
			fxmlLoader.setController(new Login());
			Parent root = fxmlLoader.load(getClass().getResource(LinkScense.login).openStream());
			Scene sen = new Scene(root);
			sen.getStylesheets().add("/application/manhinh/boder.css");
			primaryStage.setTitle("Chat App");
			primaryStage.setScene(sen);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}