package application.manhinh.control;

import application.manhinh.LinkScense;
import dccan.remote.Client;
import dccan.remote.Remote;
import dccan.suport.Mail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Regis {

	@FXML
	private Label note;

	@FXML
	private PasswordField pass;

	@FXML
	private PasswordField epass;

	@FXML
	private TextField name;

	@FXML
	private TextField user;

	@FXML
	private TextField email;

	@FXML
	private AnchorPane ap;

	@FXML
	void send(ActionEvent event) {
		note.setText("");
		String ur = user.getText().trim();
		if (ur.length() < 8) {
			note.setText("T\u00EAn \u0111\u0103ng nh\u1EADp ph\u1EA3i l\u1EDBn h\u01A1n 8");
			return;
		}
		String em = email.getText().trim();
		if (em.length() == 0) {
			note.setText("Email kh\u00F4ng \u0111\u01B0\u1EE3c tr\u1ED1ng");
			return;
		}
		if (!Mail.emailValidator(em)) {
			note.setText("Email kh\u00F4ng h\u1EE3p l\u1EC7");
			return;
		}
		String nd = name.getText().trim();
		if (nd.length() == 0) {
			note.setText("H\u1ECD t\u00EAn kh\u00F4ng th\u1EC3 \u0111\u1EC3 tr\u1ED1ng ");
			return;
		}
		String mk = pass.getText().trim();
		if (mk.length() < 8) {
			note.setText("M\u1EADt kh\u1EA9u ph\u1EA3i l\u1EDBn h\u01A1n 8 ");
			return;
		}
		String lp = epass.getText().trim();
		if (!lp.equals(mk)) {
			note.setText("M\u1EADt kh\u1EA9u kh\u00F4ng kh\u1EDBp");
			return;
		}

		Remote rmi = Client.getRmi();
		try {
			rmi.register(ur, mk, nd, em);
			Stage primaryStage = (Stage) ap.getScene().getWindow();
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setController(new ConfirmRegis());
			Parent root = fxmlLoader.load(getClass().getResource(LinkScense.nhap).openStream());
			Scene sen = new Scene(root);
			primaryStage.setTitle("xac nhan thay doi");
			primaryStage.setScene(sen);
			primaryStage.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
