package application.radiocell;

import java.io.IOException;

import application.manhinh.user.Info;
import dccan.remote.Client;
import dccan.suport.Friend;
import dccan.suport.GetList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.stage.Stage;

public class LabelCell<E, T> extends TableCell<E, T> {

	static void showF(String d) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		// fxmlLoader.setResources(ResourceBundle.getBundle("app.lang.vn"));
		Parent root;
		try {
			String lp = Client.getRmi().showFriendInfo(d);
			Friend fri = GetList.toFriend(lp);
			fxmlLoader.setController(new Info(fri));
			root = fxmlLoader.load(LabelCell.class.getResource("/application/manhinh/user/Info.fxml").openStream());
			Scene sen = new Scene(root);
			Stage pr = new Stage();
			pr.setScene(sen);
			pr.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void updateItem(T s, boolean d) {
		super.updateItem(s, d);
		if (!d) {
			String lop = s.toString();
			Button bt = new Button(lop);
			bt.setOnAction((e) -> {
				showF(lop);
			});
			setGraphic(bt);

		}
	}
}