package application.manhinh.control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dccan.remote.Client;
import dccan.remote.Remote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Login implements Initializable {
	@FXML
	private TextField ten;
	@FXML
	private AnchorPane ap;
	@FXML
	private Button btn;
	@FXML
	private Button regis;
	@FXML
	private TextField mk;
	@FXML
	private Label note;

	@FXML
	void click(ActionEvent event) {
		System.out.println(ten.getText());
		System.out.println(mk.getText());
		String user = ten.getText().trim();
		String pass = mk.getText();
		Remote rmi = Client.getRmi();
		try {

			String d = rmi.login(user, pass);
			if (d != null) {
				Stage primaryStage = ((Stage) ap.getScene().getWindow());

				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setResources(ResourceBundle.getBundle("app.lang.vn"));
				Parent root = fxmlLoader.load(getClass().getResource("/application/manhinh/Chat2.fxml").openStream());
				Scene sen = new Scene(root);
				sen.getStylesheets().add("/application/manhinh/boder.css");
				primaryStage.setScene(sen);
				primaryStage.show();

			} else {
				note.setText("khong thanh cong");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("ssss");
		}
	}

	@FXML
	void register(ActionEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		Parent root;
		try {
			fxmlLoader.setResources(ResourceBundle.getBundle("app.lang.vn"));
			root = fxmlLoader.load(getClass().getResource("/application/manhinh/pass/Dif.fxml").openStream());
			Scene sen = new Scene(root);
			Stage primaryStage = ((Stage) ap.getScene().getWindow());
			primaryStage.setScene(sen);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
