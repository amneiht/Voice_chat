package application.manhinh.control;

import java.io.IOException;
import java.rmi.RemoteException;

import application.manhinh.LinkScense;
import application.manhinh.user.Info;
import dccan.remote.Client;
import dccan.remote.NoToken;
import dccan.suport.Friend;
import dccan.suport.ShowAlert;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class FLpopup {
	private static Friend inf = null;
	private static ContextMenu pop = new ContextMenu();
	static {
		MenuItem im = new MenuItem("Them ban");
		MenuItem thong = new MenuItem("Thong tin");
		im.setOnAction(e -> {
			addFriend();
		});
		thong.setOnAction((e) -> {
			showInfo();
		});
		pop.getItems().addAll(im, thong);
	}

	static void show(Node e, Friend ls) {
		inf = ls;
		pop.show(e, Side.BOTTOM, 0.5, 0.5);
	}

	private static void addFriend() {
		String fl = inf.getTen();
		NoToken rmi = Client.getRmi();
		try {
			boolean t = rmi.addFriend(fl);
			if (t)
				ShowAlert.pr("them thanh cong");
			else
				ShowAlert.pr("That bai");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void showInfo() {
		FXMLLoader fxmlLoader = new FXMLLoader();
		// fxmlLoader.setResources(ResourceBundle.getBundle("app.lang.vn"));
		Parent root;
		try {
			
			fxmlLoader.setController(new Info(inf));
			root = fxmlLoader.load(FLpopup.class.getResource(LinkScense.show).openStream());
			Scene sen = new Scene(root);
			Stage pr = new Stage();
			pr.setScene(sen);
			pr.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
