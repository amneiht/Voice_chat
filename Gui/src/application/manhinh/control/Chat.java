package application.manhinh.control;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JTextField;

import application.autofill.Popup;
import dccan.remote.Client;
import dccan.remote.Communication;
import dccan.suport.Comment;
import dccan.suport.Friend;
import dccan.suport.GetList;
import dccan.suport.Group;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class Chat implements Initializable {
	static final boolean lold = false;
	static final boolean lnew = true;
	EventHandler<Event> enter, exit;
	private String id = null;
	@FXML
	private MenuItem fmanager;

	@FXML
	private Button delete;

	@FXML
	private AnchorPane ap;

	@FXML
	private Button setting;

	@FXML
	private Button call;

	@FXML
	private MenuItem crtGroup;

	@FXML
	private TextField vilgax;

	@FXML
	private MenuItem fadd;

	@FXML
	private VBox listGroup;
	@FXML
	private VBox khungChat;
	@FXML
	private VBox member;

	@FXML
	private TextField text;

	@FXML
	private MenuItem Jon_group;

	@FXML
	private Button File;

	@FXML
	private Button send;

	@FXML
	private Button rq;

	public Chat() {
		enter = new EventHandler<Event>() {

			@Override
			public void handle(Event e) {
				
				Label lb = (Label) e.getSource();
				lb.setUnderline(true);
				lb.setTextFill(Color.BLUE);
				lb.setCursor(Cursor.HAND);
			}
		};
		exit = new EventHandler<Event>() {

			@Override
			public void handle(Event e) {
				Label lb = (Label) e.getSource();
				lb.setUnderline(false);
				lb.setTextFill(Color.BLACK);
			}
		};
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		@SuppressWarnings("unused")
		JTextField textp = new JTextField();
		khungChat.getStyleClass().add("box");

		Communication rmi = Client.getRmi();
		try {
			String group = rmi.getGroup();
			if (group != null) {
				List<Group> lg = GetList.groups(group);
				for (Group p : lg) {
					Button bt = new Button(p.getTenNhom());
					bt.setOnAction(e -> {
						setGroup(p.getIdNhom());
					});
					listGroup.getChildren().add(bt);
				}
				if (lg.size() > 0)
					setGroup(lg.get(0).getIdNhom());
			}

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("show chat");
	}

	@FXML
	void sendComment(ActionEvent event) {

		if (id != null) {
			String nDung = text.getText().trim();
			System.out.println(nDung);
			if (nDung.length() > 0) {
				Communication rmi = Client.getRmi();
				try {
					rmi.upComment(id, nDung);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			text.clear();
		}
	}

	private void setGroup(String gp) {
		id = gp;

		Communication rmi = Client.getRmi();
		try {
			List<Friend> lf = GetList.member(rmi.getMember(gp));
			List<String> lp = new LinkedList<String>();
			for(int i=0;i<lf.size();i++)
			{
				String d = lf.get(i).getNguoiDung();
				lp.add(d);
			}
			Popup pop = new Popup(lp, "@");
			text.setOnKeyReleased(pop);
			ObservableList<Node> ls = khungChat.getChildren();
			ls.clear();
			String cmt = rmi.getCommentList(id, "now", lold);

			if (cmt != null && cmt.trim().length() > 0) {
				List<Comment> cm = GetList.cmts(cmt);
				for (Comment cp : cm) {
					Label user = new Label("@" + cp.getIdGui());
					user.getStyleClass().add("user");
					ls.add(user);
					if (cp.getIdFile()==null) {
						Text tx = new Text(cp.getNoiDung());
						TextFlow tf = new TextFlow(tx);
						tf.getStyleClass().add("cmt");
						ls.add(tf);
					} else {
						Label file = new Label(cp.getNoiDung());
						file.getStyleClass().add("cmt");
						file.setOnMouseEntered(enter);
						file.setOnMouseExited(exit);
						ls.add(file);
					}
				}

			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
