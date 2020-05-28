package application.manhinh.friend;

import java.net.URL;
import java.util.ResourceBundle;

import application.autofill.Robo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class RqId implements Initializable {
	@FXML
	private TextField text;
	@FXML
	private Button btn;
	@FXML
	private AnchorPane ap;

	String id;

	public RqId(String lp) {
		id = lp;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		text.setEditable(false);
		text.setText(id);
		btn.setText("copy");
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				send();
			}
		});
	}

	private void send() {
		Robo.copy(id);
	}
}
