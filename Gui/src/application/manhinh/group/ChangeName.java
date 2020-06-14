package application.manhinh.group;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import dccan.remote.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ChangeName implements Initializable {

	String group;

	public ChangeName(String gr) {
		group = gr;
	}

	@FXML
	private TextField text;

	@FXML
	private Button btn;

	@FXML
	private AnchorPane ap;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btn.setOnAction((e) -> {
			String name = text.getText().trim();
			if (name.length() > 0) {
				try {
					Client.getRmi().changeGroupName(group, name);
					((Stage) (ap.getScene().getWindow())).close();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

	}
}
