package application.manhinh.control;

import java.io.IOException;

import application.manhinh.friend.OutGroup;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class OutPopup {
	private static ContextMenu pop = new ContextMenu();
	static String id =""; 
	static {
		MenuItem im = new MenuItem("Thoat nhom");
		im.setOnAction(e -> {
			outGroup();
		});
		pop.getItems().addAll(im);
	}
	public static void show(Node e,String tid )
	{
		id=tid ;
		pop.show(e, Side.BOTTOM, 0.5, 0.5);
	}
	private static void outGroup() {
		if (id != null) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			// fxmlLoader.setResources(ResourceBundle.getBundle("app.lang.vn"));
			Parent root;
			try {
				fxmlLoader.setController(new OutGroup(id));
				root = fxmlLoader.load(OutPopup.class.getResource("/application/manhinh/friend/Find.fxml").openStream());
				Scene sen = new Scene(root);
				Stage pr = new Stage();
				pr.setTitle("nhap \"yes\" de thoat");
				pr.setScene(sen);
				pr.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
