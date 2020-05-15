package application.manhinh.control;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

import dccan.remote.Client;
import dccan.remote.Remote;
import dccan.suport.Friend;
import dccan.suport.GetList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Info implements Initializable{

    @FXML
    private Label User;

    @FXML
    private ImageView im;

    @FXML
    private Label name;

    Remote rmi ;
    int size =100;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		rmi=Client.getRmi();
		try {
			String fp = rmi.getInfo();
			if(fp!=null)
			{
				List<Friend> ls= GetList.friend(fp);
				User.setText(ls.get(0).getTen());
				name .setText(ls.get(0).getNguoiDung());
				User.setTextFill(Color.RED);
				name.setTextFill(Color.RED);
				if(ls.get(0).getIdAnh()!=null)
				{
					byte[] data = rmi.dowload(ls.get(0).getIdAnh());
					ByteArrayInputStream bis = new ByteArrayInputStream(data);
					Image ims = new Image(bis);
					double h = ims.getHeight();
					double v = ims.getWidth();
					double z = Math.max(h, v);
					h = h / z * size;
					v = v / z * size;
					im.setImage(ims);
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
