package dccan.suport;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ShowAlert {
	public static void pr(String info) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("ket qua");

		// Header Text: null
		alert.setHeaderText(null);
		alert.setContentText(info);

		alert.showAndWait();
	}
}
