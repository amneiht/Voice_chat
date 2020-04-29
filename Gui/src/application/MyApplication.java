package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MyApplication extends Application {
	

	@Override
	public void start(Stage primaryStage) {
		try {
			StaticStage.pri = primaryStage;
			FXMLLoader fxmlLoader = new FXMLLoader();
			Parent root = fxmlLoader.load(getClass().getResource("/application/manhinh/Login.fxml").openStream());
			Scene sen = new Scene(root);
			sen.getStylesheets().add("/application/manhinh/boder.css");
			primaryStage.setTitle("My Application");
			primaryStage.setScene(sen);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// GetRmi.init("localhost");
		launch(args);
	}

}