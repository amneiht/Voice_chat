package application.manhinh.pass;

import java.net.URL;
import java.util.ResourceBundle;

import application.manhinh.LinkScense;
import dccan.remote.Client;
import dccan.remote.NoToken;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ChangePass implements Initializable {
	@FXML
	private TextField text;

	@FXML
	private Button btn;

	@FXML
	private AnchorPane ap;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//((Stage) (ap.getScene().getWindow())).setTitle("Thay doi mat Khau");
		NoToken rmi = Client.getRmi();
		btn.setOnAction((e) -> {
			String text = this.text.getText();
			try {
				rmi.resetPass(text);
				((Stage) (ap.getScene().getWindow())).close();
				FXMLLoader fm = new FXMLLoader();
				fm.setController(new XacThuc());
				Parent root = fm.load(getClass().getResource(LinkScense.login).openStream());
				Scene sen = new Scene(root);
				Stage pr = new Stage();
				pr.setScene(sen);
				pr.setTitle("Dang ki");
				pr.show();

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}

}
