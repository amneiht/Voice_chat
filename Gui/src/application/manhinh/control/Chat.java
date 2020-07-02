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

import app.dccan.voice.RtpSystem;
import application.autofill.Popup;
import application.manhinh.LinkScense;
import application.manhinh.friend.AddF;
import application.manhinh.friend.DelGroup;
import application.manhinh.friend.DltMember;
import application.manhinh.friend.RqFriend;
import application.manhinh.friend.RqGroup;
import application.manhinh.friend.RqId;
import application.manhinh.friend.SetAd;
import application.manhinh.friend.ShowFiend;
import application.manhinh.group.Addmember;
import application.manhinh.group.ChangeName;
import application.manhinh.user.Info;
import application.manhinh.voice.Talk;
import dccan.remote.Client;
import dccan.remote.NoToken;
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
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
	NoToken rmi = Client.getRmi();
	static final boolean lold = false;
	static final boolean lnew = true;
	EventHandler<Event> enter, exit;
	private String id = null;

	@FXML
	private ScrollPane schat;
	@FXML
	VBox admin;
	@FXML
	private Button delete;

	@FXML
	private AnchorPane ap;

	@FXML
	private Button setting;

	@FXML
	private Button call;

	@FXML
	private Label vilgax;
	@FXML
	private BorderPane amneiht;
	@FXML
	private ListView<Node> listGroup;
	@FXML
	private VBox khungChat;

	@FXML
	private ListView<Label> member;

	@FXML
	private TextField text;

	String infoG = "";

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

	@FXML
	private void openFriendRq() {
		FXMLLoader fxmlLoader = new FXMLLoader();
		// fxmlLoader.setResources(ResourceBundle.getBundle("app.lang.vn"));
		Parent root;
		try {
			fxmlLoader.setController(new RqFriend());
			// root = fxmlLoader.load(getClass().getResource(LinkScense.show).openStream());
			root = fxmlLoader.load(getClass().getResource(LinkScense.show).openStream());
			Scene sen = new Scene(root);
			Stage pr = new Stage();
			pr.setScene(sen);
			pr.setTitle("Chon ban");
			pr.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void openLogout() {
		try {
			rmi.logout();
			Stage primaryStage = (Stage) (ap.getScene().getWindow());
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setResources(ResourceBundle.getBundle("app.lang.vn"));
			fxmlLoader.setController(new Login());
			Parent root = fxmlLoader.load(getClass().getResource(LinkScense.login).openStream());
			Scene sen = new Scene(root);
			primaryStage.setTitle("Chat App");
			primaryStage.setScene(sen);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void openDelAdmin() {
		FXMLLoader fxmlLoader = new FXMLLoader();
		// fxmlLoader.setResources(ResourceBundle.getBundle("app.lang.vn"));
		Parent root;
		try {
			fxmlLoader.setController(new SetAd(id, true));
			root = fxmlLoader.load(getClass().getResource(LinkScense.show).openStream());
			Scene sen = new Scene(root);
			Stage pr = new Stage();
			pr.setScene(sen);
			pr.setTitle("Delete admin");
			pr.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void openSetAdmin() {
		FXMLLoader fxmlLoader = new FXMLLoader();
		// fxmlLoader.setResources(ResourceBundle.getBundle("app.lang.vn"));
		Parent root;
		try {
			fxmlLoader.setController(new SetAd(id, false));
			root = fxmlLoader.load(getClass().getResource(LinkScense.show).openStream());
			Scene sen = new Scene(root);
			Stage pr = new Stage();
			pr.setScene(sen);
			pr.setTitle("Set admin");
			pr.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void openVoiceCall() {
		FXMLLoader fxmlLoader = new FXMLLoader();
		// fxmlLoader.setResources(ResourceBundle.getBundle("app.lang.vn"));
		Parent root;
		try {
			// RtpSystem.end();
			fxmlLoader.setController(new Talk(id));
			root = fxmlLoader.load(getClass().getResource(LinkScense.talk).openStream());
			Scene sen = new Scene(root);
			Stage pr = new Stage();
			pr.setScene(sen);
			pr.setOnCloseRequest(e -> {
				RtpSystem.end();
			});
			pr.setTitle("Delete member");
			pr.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void openDeleteMember() {
		FXMLLoader fxmlLoader = new FXMLLoader();
		// fxmlLoader.setResources(ResourceBundle.getBundle("app.lang.vn"));
		Parent root;
		try {
			fxmlLoader.setController(new DltMember(id));
			root = fxmlLoader.load(getClass().getResource(LinkScense.show).openStream());
			Scene sen = new Scene(root);
			Stage pr = new Stage();
			pr.setScene(sen);
			pr.setTitle("Delete member");
			pr.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

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
	private void openDelGroup() {
		if (id != null) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			// fxmlLoader.setResources(ResourceBundle.getBundle("app.lang.vn"));
			Parent root;
			try {
				fxmlLoader.setController(new DelGroup(id));
				root = fxmlLoader.load(getClass().getResource(LinkScense.nhap).openStream());
				Scene sen = new Scene(root);
				Stage pr = new Stage();
				pr.setTitle("nhap \"yes\" de xoa");
				pr.setScene(sen);
				pr.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	private void getIdGroup() {
		if (id != null) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			// fxmlLoader.setResources(ResourceBundle.getBundle("app.lang.vn"));
			Parent root;
			try {
				fxmlLoader.setController(new RqId(id));
				root = fxmlLoader.load(getClass().getResource(LinkScense.nhap).openStream());
				Scene sen = new Scene(root);
				Stage pr = new Stage();
				pr.setScene(sen);
				pr.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	private void showRequestList() {
		if (id != null) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			// fxmlLoader.setResources(ResourceBundle.getBundle("app.lang.vn"));
			Parent root;
			try {
				fxmlLoader.setController(new ReQuest(id));
				root = fxmlLoader.load(getClass().getResource(LinkScense.request).openStream());
				Scene sen = new Scene(root);
				Stage pr = new Stage();
				pr.setTitle("Tham gia nhom");
				pr.setScene(sen);
				pr.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	private void openJoinGroup() {
		FXMLLoader fxmlLoader = new FXMLLoader();
		// fxmlLoader.setResources(ResourceBundle.getBundle("app.lang.vn"));
		Parent root;
		try {
			fxmlLoader.setController(new RqGroup());
			root = fxmlLoader.load(getClass().getResource(LinkScense.nhap).openStream());
			Scene sen = new Scene(root);
			Stage pr = new Stage();
			pr.setScene(sen);
			pr.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void openFriendList() {
		// System.out.println("ss");
		FXMLLoader fxmlLoader = new FXMLLoader();
		// fxmlLoader.setResources(ResourceBundle.getBundle("app.lang.vn"));
		Parent root;
		try {
			fxmlLoader.setController(new ShowFiend());
			root = fxmlLoader.load(getClass().getResource(LinkScense.show).openStream());
			Scene sen = new Scene(root);
			Stage pr = new Stage();
			pr.setScene(sen);
			pr.setTitle("Xoa ban");
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
			fxmlLoader.setController(new AddF());
			root = fxmlLoader.load(getClass().getResource(LinkScense.nhap).openStream());
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
			schat.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

			// member.setSpacing(10);

		} catch (Exception e) {
			e.printStackTrace();
		}
		Timeline get = new Timeline(new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				newComment();
			}
		}));
		admin.setMinSize(0, 0);
		get.setCycleCount(Timeline.INDEFINITE);
		get.play();
		Timeline grp = new Timeline(new KeyFrame(Duration.millis(1500), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				// TODO
				getGroupList();
				// System.out.println("sss");
			}
		}));
		grp.setCycleCount(Timeline.INDEFINITE);
		grp.play();
	}

	private void getGroupList() {
		try {
			String group = rmi.getGroup();
			if (group != null) {
				if (group != infoG) {
					
					infoG = group;
					listGroup.getItems().clear();
					List<Group> lg = GetList.groups(group);
					//System.out.println(c);
					amneiht.setVisible(lg.size()>0);
					List<String> test = new LinkedList<String>();
					for (Group p : lg) {
						test.add(p.getIdNhom());
						Label bt = new Label(p.getTenNhom());
						bt.setOnMouseClicked(e -> {
							if (e.getButton() == MouseButton.PRIMARY)
								setGroup(p.getIdNhom(), p.getTenNhom());
							else if (e.getButton() == MouseButton.SECONDARY) {
								OutPopup.show(bt, id);
							}
						});
						listGroup.getItems().add(bt);
					}
					if (lg.size() == 0)
						return ;
					if (id == null) {
						if (lg.size() > 0) {
							setGroup(lg.get(0).getIdNhom(), lg.get(0).getTenNhom());
						}
					} else {
						int h = test.indexOf(id);
						if (h < 0)
							h = 0;
						setGroup(lg.get(h).getIdNhom(), lg.get(h).getTenNhom());

					}
				}
			}
			else
			{
				amneiht.setVisible(false);
				
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void getOldComment(ScrollEvent evt) {
		try {
			if (id != null) {

				if (evt.getDeltaY() < 0)
					return;
				double d = khungChat.getHeight();
				ObservableList<Node> ls = khungChat.getChildren();
				String cmt = rmi.getCommentList(id, odate, lold);
				if (cmt != null) {
					List<Comment> lp = GetList.cmts(cmt);
					for (Comment cp : lp) {
						addComment(cp, lold, ls);
					}
					if (lp.size() > 0) {
						odate = lp.get(lp.size() - 1).lgetNgayGui();
						khungChat.applyCss();
						schat.layout();
						double sp = 1.0 - d / khungChat.getHeight();
						schat.setVvalue(sp);

					}
				}

			}
		} catch (RemoteException e) {

			e.printStackTrace();
		}
	}

	@FXML
	private void onCreateGroup() {
		FXMLLoader fxmlLoader = new FXMLLoader();
		// fxmlLoader.setResources(ResourceBundle.getBundle("app.lang.vn"));
		Parent root;
		try {
			// fxmlLoader.setController(new Info());
			root = fxmlLoader.load(getClass().getResource(LinkScense.createG).openStream());
			Scene sen = new Scene(root);
			Stage pr = new Stage();
			pr.setTitle("Tao nhom");
			pr.setScene(sen);
			pr.show();
		} catch (IOException e) {
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
			String lp = rmi.getInfo();
			Friend ls = GetList.toFriend(lp);
			fxmlLoader.setController(new Info(ls));
			root = fxmlLoader.load(getClass().getResource(LinkScense.info).openStream());
			Scene sen = new Scene(root);
			Stage pr = new Stage();
			pr.setScene(sen);
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
			// fxmlLoader.setController(new Ninfo());
			root = fxmlLoader.load(getClass().getResource(LinkScense.ninfo).openStream());
			Scene sen = new Scene(root);
			Stage pr = new Stage();
			pr.setScene(sen);
			pr.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	Comment cold = new Comment(lold);
	Comment cnew = new Comment(lnew);
	static long time = 5 * 1000;// 5s

	private void addComment(Comment cp, boolean vt, ObservableList<Node> ls) {
		int size = 100;
		Label user = new Label("@" + cp.getIdGui());
		user.getStyleClass().add("user");
		if (vt) {
			if (!cp.getIdGui().equals(cnew.getIdGui())) {

				ls.add(user);

			} else {
				if (cp.lgetNgayGui() - cnew.lgetNgayGui() > time) {
					ls.add(user);
				}
			}
			cnew = cp;
		} else {
			if (!cp.getIdGui().equals(cold.getIdGui())) {

				ls.add(0, user);

			} else {
				if (cold.lgetNgayGui() - cp.lgetNgayGui() > time) {
					ls.add(0, user);
				}
			}
			cold = cp;
		}
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

	String mem = "";

	private void refreshGroup(String gp) {

		try {
			boolean check = rmi.isAdmin(gp);
			admin.setVisible(check);
			String mem2 = rmi.getMember(gp);
			if (mem2 != mem) {
				mem = mem2;
				List<Friend> lf = GetList.member(mem);
				List<String> lp = new LinkedList<String>();
				if (lf == null)
					return;
				for (int i = 0; i < lf.size(); i++) {
					String d = lf.get(i).getNguoiDung();
					lp.add(d);
				}

				// member.getChildren().clear();
				member.getItems().clear();

				ObservableList<Label> mb = member.getItems();
				for (Friend ff : lf) {
					Label lbm = new Label(ff.getNguoiDung());
					lbm.setOnMouseClicked((e) -> {
						if (e.getButton() == MouseButton.SECONDARY) {
							// System.out.println();
							FLpopup.show(lbm, ff);
						}
					});
					mb.add(lbm);
				}
				Popup pop = new Popup(lp, "@");
				text.setOnKeyReleased(pop);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	private void setGroup(String gp, String ten) {

		id = gp;
		try {
			// TODO
			refreshGroup(id);
			ObservableList<Node> ls = khungChat.getChildren();
			ls.clear();
			String cmt = rmi.getCommentList(id, -1, lold);
			vilgax.setText("Nhóm:" + ten);
			if (cmt != null && cmt.trim().length() > 0) {
				List<Comment> cm = GetList.cmts(cmt);
				if (cm.size() > 0) {
					odate = cm.get(cm.size() - 1).lgetNgayGui();
					ndate = cm.get(0).lgetNgayGui();
					cold = new Comment(lold);
					cnew = cm.get(0);
				} else {
					ndate = 0;
					odate = Long.MAX_VALUE;
					cold = new Comment(lold);
					cnew = new Comment(lnew);
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
	private void logOut() {
		Stage pr = (Stage) (ap.getScene().getWindow());
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setResources(ResourceBundle.getBundle("app.lang.vn"));
		fxmlLoader.setController(new Login());
		Parent root;
		try {
			root = fxmlLoader.load(getClass().getResource(LinkScense.login).openStream());
			Scene sen = new Scene(root);
			sen.getStylesheets().add("/application/manhinh/boder.css");
			pr.setScene(sen);
			pr.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void openChangeGroupName() {

		FXMLLoader fxmlLoader = new FXMLLoader();
		// fxmlLoader.setResources(ResourceBundle.getBundle("app.lang.vn"));
		Parent root;
		try {
			fxmlLoader.setController(new ChangeName(id));
			root = fxmlLoader.load(getClass().getResource(LinkScense.nhap).openStream());
			Scene sen = new Scene(root);
			Stage pr = new Stage();
			pr.setTitle("Doi ten nhom");
			pr.setScene(sen);
			pr.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void openAddMember() {
		FXMLLoader fxmlLoader = new FXMLLoader();
		// fxmlLoader.setResources(ResourceBundle.getBundle("app.lang.vn"));
		Parent root;
		try {
			fxmlLoader.setController(new Addmember(id));
			root = fxmlLoader.load(getClass().getResource(LinkScense.show).openStream());
			Scene sen = new Scene(root);
			Stage pr = new Stage();
			pr.setTitle("Them than vien nhom");
			pr.setScene(sen);
			pr.show();
		} catch (IOException e) {
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
