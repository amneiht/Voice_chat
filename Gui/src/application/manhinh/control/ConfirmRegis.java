package application.manhinh.control;

import java.net.URL;
import java.util.ResourceBundle;

import application.manhinh.LinkScense;
import dccan.remote.Client;
import dccan.remote.NoToken;
import dccan.suport.ShowAlert;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ConfirmRegis implements Initializable {

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
				boolean d = rmi.confirmRegister(lp);
				if (d)
					ShowAlert.pr("Dang ki thanh cong");
				else
					ShowAlert.pr("That bai");
				if (d) {
					Stage primaryStage = (Stage) (ap.getScene().getWindow());
					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setResources(ResourceBundle.getBundle("app.lang.vn"));
					fxmlLoader.setController(new Login());
					Parent root = fxmlLoader.load(getClass().getResource(LinkScense.login).openStream());
					Scene sen = new Scene(root);
					sen.getStylesheets().add("/application/manhinh/boder.css");
					primaryStage.setScene(sen);
					primaryStage.show();
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});
	}
}
