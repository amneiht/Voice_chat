package application.manhinh.group;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import application.radiocell.AddF;
import application.radiocell.CreateTable;
import application.radiocell.GFriend;
import dccan.remote.Client;
import dccan.remote.NoToken;
import dccan.suport.Friend;
import dccan.suport.GetList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class Addmember implements Initializable {

	private TableView<GFriend> tab = new TableView<GFriend>();
	String gp;

	public Addmember(String ll) {
		gp = ll;
	}

	@FXML
	private ScrollPane scp;

	@FXML
	void sendRq(ActionEvent event) {
		List<GFriend> ld = tab.getItems();
		List<String> lp = new LinkedList<String>();
		for (GFriend ls : ld) {
			if (ls.getAction() == AddF.YES) {
				lp.add(ls.getId());
			}
		}
		try {
			rmi.addMember(gp, lp);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		((Stage) (scp.getScene().getWindow())).close();
	}

	NoToken rmi = Client.getRmi();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			String mem = rmi.getMember(gp);
			String fri = rmi.getFriendList();

			List<Friend> lf = GetList.friendList(fri);
			List<Friend> lm = GetList.friendList(mem);
			List<GFriend> ld = new LinkedList<GFriend>();
			for (Friend ls : lf) {
				if (lm.indexOf(ls) < 0) {
					ld.add(new GFriend(ls));
				}
			}
			CreateTable.makeChoiseFrendTable(tab);
			CreateTable.setGFriend(tab, ld);
			scp.setContent(tab);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
