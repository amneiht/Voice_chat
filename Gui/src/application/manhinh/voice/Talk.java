package application.manhinh.voice;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import app.dccan.voice.Record;
import app.dccan.voice.RtpSystem;
import dccan.remote.Client;
import dccan.remote.NoToken;
import dccan.suport.FileVsByte;
import dccan.suport.Friend;
import dccan.suport.GetList;
import dccan.suport.Member;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;

public class Talk implements Initializable {
	static String no_img = Client.workPath + "/Img/no_img.png";
	String id;

	public Talk(String gp) {
		id = gp;
	}

	@FXML
	private Button rc;

	@FXML
	private Button mute;

	@FXML
	private FlowPane fp;
	List<Friend> mem;

	NoToken rmi;
	Map<String, ImageView> mp = new HashMap<String, ImageView>();
	List<String> user = new ArrayList<String>();
	boolean sound = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		rmi = Client.getRmi();
		try {
			String lt = rmi.getMember(id);
			if (lt != null) {
				mem = GetList.friendList(lt);
				for (Friend p : mem) {
					if (p.getIdAnh() != null) {
						mp.put(p.getTen(), create(p.getIdAnh()));
					} else {
						mp.put(p.getTen(), createback());
					}
				}

			}
			Timeline get = new Timeline(new KeyFrame(Duration.millis(1500), new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					update();
				}

			}));
			get.setCycleCount(Timeline.INDEFINITE);
			get.play();
			mute.setOnAction(e -> {
				if (sound) {
					RtpSystem.mute();
					mute.setText("UnMute");
				} else {
					RtpSystem.talk();
					mute.setText("mute");
				}
				sound = !sound;
			});
			RtpSystem.Connect(id);

		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	private void update() {
		try {
			String lls = rmi.chatMember(id);
			if (lls != null) {
				// List<String> lnew = GetList.listString(lls);
				List<Member> input = Record.getMemList(id);
				List<String> lnew = new LinkedList<String>();
				for (Member d : input) {
					lnew.add(d.getUser());
				}
				ObservableList<Node> text = fp.getChildren();
				List<String> oldList = getNoList(user, lnew);
				for (String p : oldList) {
					int h = user.indexOf(p);
					user.remove(h);
					text.remove(h);
				}
				// loai bo ngu dung ko ton tai
				List<String> lop = getNewList(user, lnew);
				for (String p : lop) {
					ImageView ip = mp.get(p);
					if (ip != null) {
						TitledPane tp = new TitledPane(p, ip);
						user.add(p);
						text.add(tp);
					} else {
						ImageView tp = getById(p);
						mp.put(p, tp);
						user.add(p);
						text.add(tp);
					}
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private ImageView getById(String p) {
		for (Friend s : mem) {
			if (s.getTen().equals(p)) {
				if (s.getIdAnh() != null)
					return create(s.getIdAnh());
				else
					return createback();
			}

		}
		return createback();
	}

	private static List<String> getNoList(List<String> lold, List<String> lnew) {
		List<String> res = new LinkedList<String>();
		for (String p : lold) {
			int h = lnew.indexOf(p);
			if (h < 0)
				res.add(p);
		}

		return res;
	}

	private static List<String> getNewList(List<String> lold, List<String> lnew) {
		List<String> res = new LinkedList<String>();
		for (String p : lnew) {
			int h = lold.indexOf(p);
			if (h < 0)
				res.add(p);
		}

		return res;
	}

	public static void end() {
		System.out.println("sss");
		RtpSystem.end();
	}

	static int size = 70;

	public static ImageView createback() {
		try {
			byte[] data = FileVsByte.toByte(no_img);
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
			bis.close();
			return iv;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ImageView create(String flie) {
		try {
			byte[] data = rmi.dowload(flie);
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
			bis.close();
			return iv;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
