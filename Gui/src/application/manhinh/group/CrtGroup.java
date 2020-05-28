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
import dccan.remote.Remote;
import dccan.suport.Friend;
import dccan.suport.GetList;
import dccan.suport.ShowAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CrtGroup implements Initializable {
	@FXML
	private TableView<GFriend> tab;

	@FXML
	private TextField group;

	@FXML
	private BorderPane ap;

	@FXML
	void makeGp(ActionEvent event) {
		List<String> mem = new LinkedList<String>();
		List<GFriend> user = tab.getItems();
		for (GFriend lp : user) {
			if (lp.getAction() == AddF.YES) {
				mem.add(lp.getId());
			}
		}
		System.out.println("mem "+mem.size());
		String gp = group.getText().trim();
		if (gp.length() > 0) {
			try {
				boolean test = rmi.createGroup(gp, mem);
				Stage lp = (Stage) (ap.getScene().getWindow());
				lp.close();
				if (test)
					ShowAlert.pr("thanh cong");
				else
					ShowAlert.pr("that bai");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	Remote rmi;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rmi = Client.getRmi();
		// TODO Auto-generated method stub
		try {
			String fl = rmi.getFriendList();
			if (fl != null) {
				List<Friend> lf = GetList.friend(fl);
				CreateTable.makeChoiseFrendTable(tab);
				List<GFriend> sh = new LinkedList<GFriend>();
				for (Friend p : lf) {
					sh.add(new GFriend(p));
				}
				CreateTable.setGFriend(tab, sh);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
