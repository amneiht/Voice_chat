package application.manhinh.friend;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import dccan.remote.Client;
import dccan.remote.NoToken;
import dccan.suport.ShowAlert;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RqGroup implements Initializable {
	@FXML
	private TextField text;
	@FXML
	private Button btn;
	@FXML
	private AnchorPane ap;
	NoToken rmi;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				send();
			}
		});
		rmi = Client.getRmi();
	}

	private void send() {
		boolean b = false;
		try {
			b = rmi.requestGroup(text.getText().trim());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Stage ss = (Stage) (ap.getScene().getWindow());
		ss.close();
		if (b)
			ShowAlert.pr("yeu cau thanh cong");
		else
			ShowAlert.pr("that bai");

	}
}
