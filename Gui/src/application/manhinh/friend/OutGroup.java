package application.manhinh.friend;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import dccan.remote.Client;
import dccan.remote.NoToken;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class OutGroup implements Initializable{
	@FXML
	private TextField text;

	@FXML
	private Button btn;

	@FXML
	private AnchorPane ap;

	String gp;

	public OutGroup(String id) {
		gp = id;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btn.setOnAction(e -> {
			String cf = text.getText();
			if (cf.equals("yes")) {
				NoToken rmi = Client.getRmi();
				try {
					rmi.outGroup(gp);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			Stage ss = (Stage) (ap.getScene().getWindow());
			ss.close();
		});
	}
}
