package application.manhinh.control;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JTextField;

import application.autofill.Popup;
import dccan.remote.Client;
import dccan.remote.Remote;
import dccan.suport.Comment;
import dccan.suport.Friend;
import dccan.suport.GetList;
import dccan.suport.Group;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

public class Chat implements Initializable {
	long odate = -1, ndate = -1;
	Remote rmi = Client.getRmi();
	static final boolean lold = false;
	static final boolean lnew = true;
	EventHandler<Event> enter, exit;
	private String id = null;

	@FXML
	private MenuItem fmanager;

	@FXML
	private ScrollPane schat;

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
		// new Thread(this).start();
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

		try {
			schat.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
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
			e.printStackTrace();
		}
		// TODO
		// khungChat.maxHeightProperty().bind(khungChat.heightProperty());
		Timeline get = new Timeline(new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				newComment();
			}
		}));
		get.setCycleCount(Timeline.INDEFINITE);
		get.play();
		System.out.println("show chat");
	}

	@FXML
	void sendComment(ActionEvent event) {

		if (id != null) {
			String nDung = text.getText().trim();
			// System.out.println(nDung);
			if (nDung.length() > 0) {
				// Communication rmi = Client.getRmi();
				try {
					rmi.upComment(id, nDung);
				} catch (RemoteException e) {
					e.printStackTrace();
				}

			}
			text.clear();
		}
	}

	private void newComment() {
		try {
			// TODO
			if (id != null) {

				String cmt = rmi.getCommentList(id, ndate, lnew);
				ObservableList<Node> ls = khungChat.getChildren();
				double p = schat.getVvalue();

				if (cmt != null) {
					List<Comment> lp = GetList.cmts(cmt);
					for (Comment cp : lp) {
						addComment(cp, lnew, ls);
					}

					if (lp.size() > 0) {
						ndate = lp.get(lp.size() - 1).lgetNgayGui();
						if (p > 0.8) {
							khungChat.applyCss();
							schat.layout();
							schat.setVvalue(1);
						}

					}
				}

			}
		} catch (RemoteException e) {

			e.printStackTrace();
		}

	}
	private void addComment(Comment cp, boolean vt, ObservableList<Node> ls) {
		Label user = new Label("@" + cp.getIdGui());
		user.getStyleClass().add("user");
		if (vt)
			ls.add(user);
		else
			ls.add(0, user);
		if (cp.getIdFile() == null) {
			Text tx = new Text(cp.getNoiDung());
			tx.getStyleClass().add("cmt");
			TextFlow tf = new TextFlow(tx);
			if (vt)
				ls.add(tf);
			else
				ls.add(1, tf);

		} else {
			Label file = new Label(cp.getNoiDung());
			file.getStyleClass().add("cmt");
			file.setOnMouseEntered(enter);
			file.setOnMouseExited(exit);
			if (vt)
				ls.add(file);
			else
				ls.add(1, file);

		}
	}

	private void setGroup(String gp) {
		//TODO
		id = gp;
		try {
			List<Friend> lf = GetList.member(rmi.getMember(gp));
			List<String> lp = new LinkedList<String>();
			for (int i = 0; i < lf.size(); i++) {
				String d = lf.get(i).getNguoiDung();
				lp.add(d);
			}
			Popup pop = new Popup(lp, "@");
			text.setOnKeyReleased(pop);
			ObservableList<Node> ls = khungChat.getChildren();
			ls.clear();
			String cmt = rmi.getCommentList(id, -1, lold);

			if (cmt != null && cmt.trim().length() > 0) {
				List<Comment> cm = GetList.cmts(cmt);
				if (cm.size() > 0) {
					odate = cm.get(cm.size() - 1).lgetNgayGui();
					ndate = cm.get(0).lgetNgayGui();
				} else {
					ndate = 0;
					odate = Long.MAX_VALUE;
				}
				for (Comment cp : cm) {
					addComment(cp, lold, ls);
				}

			}
			khungChat.applyCss();
			schat.layout();
			schat.setVvalue(1);
		} catch (RemoteException e) {

			e.printStackTrace();
		}
	}

	@FXML
	private void checkEnter(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			sendComment(null);
		}
	}
}
