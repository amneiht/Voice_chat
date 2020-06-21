package application.manhinh.friend;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import application.radiocell.Chose;
import application.radiocell.CreateTable;
import application.radiocell.UserRq;
import dccan.remote.Client;
import dccan.remote.NoToken;
import dccan.suport.GetList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class RqFriend implements Initializable {
	@FXML
	private ScrollPane scp;

	@FXML
	void sendRq(ActionEvent event) {
		List<UserRq> lp = tab.getItems();
		List<String> del = new LinkedList<String>();
		List<String> act = new LinkedList<String>();
		for (UserRq p : lp) {
			if (p.getAction() == Chose.YES) {
				act.add(p.getName());
			} else if (p.getAction() == Chose.NO) {
				del.add(p.getName());
			}
		}
		try {
			rmi.acceptFriendRequest(act);
			rmi.deleteFriendRequest(del);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		((Stage) (scp.getScene().getWindow())).close();
	}

	TableView<UserRq> tab;
	NoToken rmi;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		rmi = Client.getRmi();
		tab = new TableView<UserRq>();
		try {
			String note = rmi.showFriendRq();
			if (note != null) {
				List<String> lp = GetList.listString(note);
				List<UserRq> up = new LinkedList<UserRq>();
				for (String s : lp) {
					up.add(new UserRq(s));

				}
				CreateTable.makeReQuestTable(tab);
				CreateTable.setUser(tab, up);
			}
			scp.setContent(tab);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
