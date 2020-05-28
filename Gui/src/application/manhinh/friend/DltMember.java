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
import dccan.remote.Remote;
import dccan.suport.Friend;
import dccan.suport.GetList;
import dccan.suport.ShowAlert;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class DltMember implements Initializable {
	@FXML
	private ScrollPane scp;
	TableView<GFriend> view = new TableView<GFriend>();
	Remote rmi;
	String gp;

	public DltMember(String d) {
		gp = d;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		scp.setContent(view);
		CreateTable.makeChoiseFrendTable(view);
		rmi = Client.getRmi();
		try {
			String s = rmi.getMember(gp);

			if (s == null)
				return;
			List<Friend> Flist = GetList.friend(s);
			List<GFriend> lg = new LinkedList<GFriend>();
			for (Friend ss : Flist) {
				lg.add(new GFriend(ss));
			}

			CreateTable.setGFriend(view, lg);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	private void sendRq() {
		List<String> mem = new LinkedList<String>();
		List<GFriend> user = view.getItems();
		for (GFriend lp : user) {
			if (lp.getAction() == AddF.YES) {
				mem.add(lp.getId());
			}
		}
		System.out.println(mem.size());
		if (mem.size() > 0) {
			try {
				boolean test = rmi.deleteMember(gp, mem);
				if (test)
					ShowAlert.pr("thanh cong");
				else
					ShowAlert.pr("that bai");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Stage lp = (Stage) (scp.getScene().getWindow());
		lp.close();

	}
}
