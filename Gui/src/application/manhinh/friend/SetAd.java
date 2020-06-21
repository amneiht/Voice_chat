package application.manhinh.friend;

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

public class SetAd implements Initializable {
	@FXML
	private ScrollPane scp;

	@FXML
	void sendRq(ActionEvent event) {
		List<String> mem = new LinkedList<String>();
		List<GFriend> user = tab.getItems();
		for (GFriend lp : user) {
			if (lp.getAction() == AddF.YES) {
				mem.add(lp.getId());
			}
		}
		if (mem.size() > 0) {

			try {
				if (mode)
					rmi.delAdmin(gp, mem);
				else
					rmi.setAdmin(gp, mem);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Stage lp = (Stage) (scp.getScene().getWindow());
		lp.close();

	}

	private TableView<GFriend> tab = new TableView<GFriend>();
	private String gp;
	private NoToken rmi;
	boolean mode;

	public SetAd(String id, boolean mode) {
		gp = id;
		this.mode = mode;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rmi = Client.getRmi();
		try {
			String lp = null;
			if (mode)
				lp = rmi.getAdminOnGroup(gp);
			else
				lp = rmi.getNonAdminOnGroup(gp);
			if (lp != null) {
				List<Friend> lf = GetList.friendList(lp);
				List<GFriend> data = new LinkedList<GFriend>();
				for (Friend op : lf) {
					data.add(new GFriend(op));
				}
				CreateTable.makeChoiseFrendTable(tab);
				CreateTable.setGFriend(tab, data);
				scp.setContent(tab);
			}

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
