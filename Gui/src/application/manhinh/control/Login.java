package application.manhinh.control;

import java.net.URL;
import java.util.ResourceBundle;

import application.StaticStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Login implements Initializable {
	@FXML
	private TextField ten;

	@FXML
	private Button btn;
	@FXML
	private Button regis;
	@FXML
	private TextField mk;

	@FXML
	void click(ActionEvent event) {
		System.out.println(ten.getText());
		System.out.println(mk.getText());
		Stage st = StaticStage.getpri();
		st.hide();
		FXMLLoader fxmlLoader = new FXMLLoader();
		try {
			Parent root = fxmlLoader.load(getClass().getResource("/application/manhinh/Work.fxml").openStream());
			Scene sen = new Scene(root);
			sen.getStylesheets().add("/application/manhinh/boder.css");
			st.setScene(sen);
			st.show();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@FXML
	void register(ActionEvent event) {

	}

	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
