package application.manhinh.pass;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

import application.autofill.AutoFill;
import application.autofill.InfoList;
import application.manhinh.LinkScense;
import dccan.remote.Client;
import dccan.remote.Remote;
import dccan.suport.GetList;
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
		Remote rmi = Client.getRmi();
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
		InfoList ip = new InfoList() {

			@Override
			public List<String> getList(String tx) {
				List<String> lp = null;
				try {
					String s = rmi.getUserOnLimit(tx);
					lp = GetList.listString(s);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return lp;
			}
		};
		new AutoFill(ip, text);
	}

}
