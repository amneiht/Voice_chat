package application.manhinh.control;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import application.radiocell.Chose;
import application.radiocell.CreateTable;
import application.radiocell.UserRq;
import dccan.remote.NoToken;
import dccan.remote.Client;
import dccan.suport.GetList;
import javafx.collections.ObservableList;
//import dccan.remote.Communication;
//import dccan.remote.GetRmi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ReQuest implements Initializable {
	String group;

	public ReQuest(String gp) {
		group = gp;
		
	}
	@FXML
	private TableView<UserRq> tab;

	@FXML
	private Button btn;

	@FXML
	private AnchorPane rq;

	@FXML
	void runAct(ActionEvent event) {

		ObservableList<UserRq> data = tab.getItems();
		List<String> add = new LinkedList<String>();
		List<String> del = new LinkedList<String>();
		for (UserRq up : data) {
			if (up.getAction() == Chose.YES) {
				add.add(up.getName());
				// System.out.println(up.getName());
			} else if (up.getAction() == Chose.NO) {
				del.add(up.getName());
				// System.out.println(up.getName());
			}
		}
		NoToken rmi = Client.getRmi();
		try {
			rmi.acceptRequest(group, add);
			rmi.deleteRequest(group, del);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Stage sc = (Stage) rq.getScene().getWindow();
		sc.close();

	}

	public static List<UserRq> initList(List<String> up) {
		List<UserRq> ip = new LinkedList<UserRq>();
		for (String us : up) {
			ip.add(new UserRq(us));
		}
		return ip;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tab.setEditable(true);
		CreateTable.makeReQuestTable(tab);
		NoToken rmi = Client.getRmi();
		List<String> il;
		il = new LinkedList<String>();

		try {
			String lp = rmi.showRequest(group);
			if (lp != null) {
				il = GetList.listString(lp);
				CreateTable.setUser(tab, initList(il));
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
