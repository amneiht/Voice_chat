package application.manhinh.pass;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import dccan.remote.Client;
import dccan.suport.ShowAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class XacThuc implements Initializable {
	@FXML
	private Label note;

	@FXML
	private Label name;

	@FXML
	private Button regis;

	@FXML
	private TextField ten;

	@FXML
	private Button btn;

	@FXML
	private PasswordField mk;

	@FXML
	private AnchorPane ap;

	@FXML
	void click(ActionEvent event) {
		((Stage) (ap.getScene().getWindow())).close();
	}

	@FXML
	void register(ActionEvent event) {
		((Stage) (ap.getScene().getWindow())).close();
		String tokem = ten.getText();
		String pass = mk.getText();
		boolean t = false ;
		try {
			 t = Client.getRmi().newPassword(tokem, pass);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(t) ShowAlert.pr("thanh cong");
		else ShowAlert.pr("That Bai");
			
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		name.setText("Token");
		btn.setText("Thoát");
		regis.setText("Xác thực");
	}
}
