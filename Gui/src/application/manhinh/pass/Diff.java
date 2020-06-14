package application.manhinh.pass;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Diff {
	@FXML
	AnchorPane ap;

	@FXML
	private void openChangePass() {
		try {
		
			((Stage) (ap.getScene().getWindow())).close();
			FXMLLoader fm = new FXMLLoader();
			fm.setController(new ChangePass());
			Parent root = fm.load(getClass().getResource("/application/manhinh/friend/Find.fxml").openStream());
			Scene sen = new Scene(root);
			Stage pr = new Stage();
			pr.setScene(sen);
			pr.setTitle("Doi mat khau");
			pr.show();

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@FXML
	private void openRegis() {
		((Stage) (ap.getScene().getWindow())).close();
		FXMLLoader fm = new FXMLLoader();
		try {
			Parent root = fm.load(getClass().getResource("/application/manhinh/Register.fxml").openStream());
			Scene sen = new Scene(root);
			Stage pr = new Stage();
			pr.setScene(sen);
			pr.setTitle("Dang ki");
			pr.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void openXacThuc() {
		try {
			((Stage) (ap.getScene().getWindow())).close();
			FXMLLoader fm = new FXMLLoader();
			fm.setController(new XacThuc());
			Parent root = fm.load(getClass().getResource("/application/manhinh/Login.fxml").openStream());
			Scene sen = new Scene(root);
			Stage pr = new Stage();
			pr.setScene(sen);
			pr.setTitle("Dang ki");
			pr.show();

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
