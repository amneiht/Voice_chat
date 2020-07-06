package application.manhinh.control;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import dccan.remote.Client;
import dccan.remote.NoToken;
import dccan.suport.CheckImage;
import dccan.suport.Comment;
import dccan.suport.FileVsByte;
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
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class FriendChat implements Initializable {

	EventHandler<Event> enter, exit;
	String id;
	@FXML
	private ListView<Label> listGroup;
	@FXML
	private ScrollPane schat;
	@FXML
	private VBox khungChat;

	@FXML
	private TextField text;

	long odate = -1, ndate = -1;
	final boolean lold = false;
	final boolean lnew = true;

	public FriendChat() {
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
		List<File> files = fileChooser.showOpenMultipleDialog(schat.getScene().getWindow());
		for (File p : files) {
			try {
				if (p.isFile()) {
					if (p.length() < FileVsByte.max) {
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

	NoToken rmi;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rmi = Client.getRmi();
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
			Timeline get = new Timeline(new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					newComment();
				}
			}));
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
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@FXML
	private void checkEnter(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			sendComment(null);
		}
	}

	String infoG = "";

	private void getGroupList() {
		try {
			String group = rmi.getChatFriendList();
			if (group != null) {
				if (!group.equals(infoG)) {
					List<Group> lg = GetList.groups(group);
					infoG = group;
					listGroup.getItems().clear();
					List<String> test = new LinkedList<String>();
					for (Group p : lg) {
						test.add(p.getIdNhom().trim());
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
						return;
					if (id == null) {
						if (lg.size() > 0) {
							setGroup(lg.get(0).getIdNhom(), lg.get(0).getTenNhom());
						}
					} else {
						int h = test.indexOf(id.trim());
						if (h < 0)
							setGroup(lg.get(0).getIdNhom(), lg.get(0).getTenNhom());
					}
				} 

			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	private void setGroup(String gp, String ten) {
		id = gp;
		try {
			ObservableList<Node> ls = khungChat.getChildren();
			ls.clear();
			String cmt = rmi.getCommentList(id, -1, lold);
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

	Comment cold = new Comment(lold);
	Comment cnew = new Comment(lnew);
	static long time = 30 * 1000;// 5s

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
			tf.setOnMouseClicked(e -> {
				if (e.getButton() == MouseButton.SECONDARY)
					CopyPopup.openPop(e, cp.getNoiDung());
			});
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
					if (e.getButton() == MouseButton.SECONDARY)
						CopyPopup.openPop(file, cp.getNoiDung());
					else if (e.getButton() == MouseButton.PRIMARY)
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

	DirectoryChooser dirChooser = new DirectoryChooser();

	private void download(String name, String id) {

		File chosenDir = dirChooser.showDialog(schat.getScene().getWindow());
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
}
