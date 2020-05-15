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
			if (d!=null) {
				((Stage) ap.getScene().getWindow()).close();
				FXMLLoader fxmlLoader = new FXMLLoader();
				Parent root = fxmlLoader.load(getClass().getResource("/application/manhinh/Work.fxml").openStream());
				Scene sen = new Scene(root);
				sen.getStylesheets().add("/application/manhinh/boder.css");
				Stage primaryStage = new Stage();
				primaryStage.setScene(sen);
				primaryStage.show();
			} else {
				note.setText("khong thanh cong");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	@FXML
	void register(ActionEvent event) {

		((Stage) ap.getScene().getWindow()).close();
		FXMLLoader fxmlLoader = new FXMLLoader();
		Parent root;
		try {
			root = fxmlLoader.load(getClass().getResource("/application/manhinh/Register.fxml").openStream());
			Scene sen = new Scene(root);
			Stage primaryStage = new Stage();
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
