package application.manhinh.user;

import java.net.URL;
import java.util.ResourceBundle;

import dccan.remote.Client;
import dccan.remote.NoToken;
import dccan.suport.ShowAlert;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ConfirmMail implements Initializable{
	@FXML
	private TextField text;

	@FXML
	private Button btn;

	@FXML
	private AnchorPane ap;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btn.setText("gui");
		NoToken rmi = Client.getRmi();
		btn.setOnAction(evt -> {
			String lp = text.getText();
			try {
				boolean d = rmi.confirmChangeMail(lp);
				if (d)
					ShowAlert.pr("Thanh cong");
				else
					ShowAlert.pr("That bai");
				Stage primaryStage = (Stage) (ap.getScene().getWindow());
				primaryStage.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});
	}
}
