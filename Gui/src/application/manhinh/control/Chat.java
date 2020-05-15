package application.manhinh.control;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JTextField;

import application.autofill.Popup;
import dccan.remote.Client;
import dccan.remote.Remote;
import dccan.suport.CheckImage;
import dccan.suport.Comment;
import dccan.suport.FileVsByte;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
	private MenuItem vn;
	@FXML
	private MenuItem en;
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

	FileChooser fileChooser = new FileChooser();

	@FXML
	private void openFile() {
		List<File> files = fileChooser.showOpenMultipleDialog(ap.getScene().getWindow());
		for (File p : files) {
			try {
				if (p.isFile()) {
					if (p.length() < FileVsByte.max) {
						// System.out.println("lll");
						byte[] data = FileVsByte.toByte(p);
						rmi.upload(p.getName(), data, id);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	@FXML
	private void openFriendList() {
		// System.out.println("ss");
		FXMLLoader fxmlLoader = new FXMLLoader();
		// fxmlLoader.setResources(ResourceBundle.getBundle("app.lang.vn"));
		Parent root;
		try {
			root = fxmlLoader.load(getClass().getResource("/application/manhinh/friend/ShowFriend.fxml").openStream());
			Scene sen = new Scene(root);
			Stage pr = new Stage();
			pr.setScene(sen);
			pr.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void openAddFriend() {
		// System.out.println("ss");
		FXMLLoader fxmlLoader = new FXMLLoader();
		// fxmlLoader.setResources(ResourceBundle.getBundle("app.lang.vn"));
		Parent root;
		try {
			root = fxmlLoader.load(getClass().getResource("/application/manhinh/friend/AddFriend.fxml").openStream());
			Scene sen = new Scene(root);
			Stage pr = new Stage();
			pr.setScene(sen);
			pr.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO
		@SuppressWarnings("unused")
		JTextField textp = new JTextField();

		try {
			khungChat.setOnDragOver(new EventHandler<DragEvent>() {
				@Override
				public void handle(DragEvent event) {
					Dragboard db = event.getDragboard();
					if (db.hasFiles()) {
						event.acceptTransferModes(TransferMode.COPY);
					} else {
						event.consume();
					}
				}
			});
			khungChat.setOnDragDropped(new EventHandler<DragEvent>() {
				@Override
				public void handle(DragEvent event) {
					Dragboard db = event.getDragboard();
					boolean success = false;
					if (db.hasFiles()) {
						success = true;
						for (File p : db.getFiles()) {
							try {
								if (p.isFile()) {
									if (p.length() < FileVsByte.max) {
										// System.out.println("lll");
										byte[] data = FileVsByte.toByte(p);
										rmi.upload(p.getName(), data, id);
									}
								}

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					event.setDropCompleted(success);
					event.consume();
				}
			});
			schat.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
			getGroupList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		Timeline get = new Timeline(new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				newComment();
			}
		}));
		get.setCycleCount(Timeline.INDEFINITE);
		get.play();
		System.out.println("show chat");
	}

	private void getGroupList() {
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
			e.printStackTrace();
		}
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

	DirectoryChooser dirChooser = new DirectoryChooser();

	private void download(String name, String id) {

		File chosenDir = dirChooser.showDialog(ap.getScene().getWindow());
		if (chosenDir != null)
			if (chosenDir.isDirectory()) {
				byte[] data;
				try {
					data = rmi.dowload(id);
					FileVsByte.toFile(data, chosenDir.getAbsolutePath() + "/" + name);
				} catch (RemoteException e) {
					e.printStackTrace();
				}

			}
	}

	@FXML
	void showInfo() {
		FXMLLoader fxmlLoader = new FXMLLoader();
		// fxmlLoader.setResources(ResourceBundle.getBundle("app.lang.vn"));
		Parent root;
		try {
			root = fxmlLoader.load(getClass().getResource("/application/manhinh/user/Info.fxml").openStream());
			Scene sen = new Scene(root);
			Stage pr = new Stage();
			pr.setScene(sen);
			;
			pr.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	void newInfo() {
		FXMLLoader fxmlLoader = new FXMLLoader();
		// fxmlLoader.setResources(ResourceBundle.getBundle("app.lang.vn"));
		Parent root;
		try {
			root = fxmlLoader.load(getClass().getResource("/application/manhinh/user/NewInfo.fxml").openStream());
			Scene sen = new Scene(root);
			Stage pr = new Stage();
			pr.setScene(sen);
			;
			pr.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void addComment(Comment cp, boolean vt, ObservableList<Node> ls) {
		int size = 100;
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
			String name = cp.getNoiDung();
			boolean check = CheckImage.cmp(name);
			if (check) {
				try {
					byte[] data = rmi.dowload(cp.getIdFile());
					ByteArrayInputStream bis = new ByteArrayInputStream(data);
					Image im = new Image(bis);
					double h = im.getHeight();
					double v = im.getWidth();
					double z = Math.max(h, v);
					h = h / z * size;
					v = v / z * size;
					ImageView iv = new ImageView(im);
					iv.setFitWidth(v);
					iv.setFitHeight(h);
					iv.setOnMouseClicked(e -> {
						download(cp.getNoiDung(), cp.getIdFile());
					});
					iv.setOnMouseEntered(e -> {
						iv.setCursor(Cursor.HAND);
					});
					if (vt)
						ls.add(iv);
					else
						ls.add(1, iv);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				Label file = new Label(cp.getNoiDung());
				file.getStyleClass().add("cmt");
				file.setOnMouseEntered(enter);
				file.setOnMouseExited(exit);
				file.setOnMouseClicked(e -> {
					download(cp.getNoiDung(), cp.getIdFile());
				});
				if (vt)
					ls.add(file);
				else
					ls.add(1, file);
			}
		}
	}

	private void setGroup(String gp) {

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
