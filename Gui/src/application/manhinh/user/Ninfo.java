package application.manhinh.user;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.manhinh.LinkScense;
import dccan.remote.Client;
import dccan.remote.NoToken;
import dccan.suport.CheckImage;
import dccan.suport.FileVsByte;
import dccan.suport.Friend;
import dccan.suport.GetList;
import dccan.suport.ShowAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Ninfo implements Initializable {
	boolean chang = false;
	String file = null, tmail, tuser;
	byte[] data;
	@FXML
	private Button chose;

	@FXML
	private TextField name;

	@FXML
	private ImageView iv;

	@FXML
	private TextField email;

	@FXML
	private AnchorPane ap;
	NoToken rmi;

	@FXML
	void sendInfo(ActionEvent event) {
		try {
			rmi = Client.getRmi();
			boolean t = true;
			if (chang) {

				t = rmi.setImage(file, data);
				if (!t) {
					ShowAlert.pr("loi ket noi");
					return;
				}

			}
			String text = name.getText().trim();
			if (!text.equals(tuser)) {
				rmi.changeName(text);
			}
			text = email.getText();
			if (!text.equals(tmail)) {
				rmi.changeMail(text);
			}
			Stage lps = (Stage) (ap.getScene().getWindow());
			if (text.equals(tmail))
				lps.close();
			else {

				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setController(new ConfirmMail());
				Parent root = fxmlLoader.load(getClass().getResource(LinkScense.nhap).openStream());
				Scene sen = new Scene(root);
				lps.setTitle("xac thuc email");
				lps.setScene(sen);
				lps.show();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ShowAlert.pr("loi ket noi");
		}
	}

	int size = 150;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rmi = Client.getRmi();
		try {
			String fp = rmi.getInfo();
			Friend fv = GetList.toFriend(fp);
			tuser = fv.getNguoiDung();
			tmail = fv.getEmail();
			email.setText(tmail);
			name.setText(tuser);
			if (fv.getIdAnh() != null) {
				byte[] data = rmi.dowload(fv.getIdAnh());
				ByteArrayInputStream bis = new ByteArrayInputStream(data);
				Image ims = new Image(bis);
				double h = ims.getHeight();
				double v = ims.getWidth();
				double z = Math.max(h, v);
				h = h / z * size;
				v = v / z * size;
				iv.setImage(ims);
			}
		} catch (Exception e) {
		}

	}

	FileChooser fileChooser = new FileChooser();

	@FXML
	void openImg(ActionEvent event) {
		try {
			List<File> files = fileChooser.showOpenMultipleDialog(ap.getScene().getWindow());
			File d = files.get(0);
			file = d.getName();
			if (CheckImage.cmp(file)) {
				chang = true;
				data = FileVsByte.toByte(d);
				ByteArrayInputStream bis = new ByteArrayInputStream(data);
				Image im = new Image(bis);
				double h = im.getHeight();
				double v = im.getWidth();
				double z = Math.max(h, v);
				h = h / z * size;
				v = v / z * size;
				iv.setImage(im);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

}
