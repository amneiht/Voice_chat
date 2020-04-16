package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MyApplication extends Application {
	Scene sen;

	@Override
	public void start(Stage primaryStage) {
		try {
			// Đọc file fxml và vẽ giao diện.
			Parent root = FXMLLoader.load(getClass().getResource("/application/manhinh/Work.fxml"));
			sen = new Scene(root);
			sen.getStylesheets().add("/application/manhinh/boder.css");
			primaryStage.setTitle("My Application");
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