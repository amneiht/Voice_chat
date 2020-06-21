package application.manhinh.user;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import dccan.remote.Client;
import dccan.remote.NoToken;
import dccan.suport.Friend;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Info implements Initializable{

	Friend inf =null;
    @FXML
    private Label User;

    @FXML
    private ImageView im;

    @FXML
    private Label name;

    NoToken rmi ;
    public Info(Friend i)
    {
    	inf = i ;
    }
    int size =100;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		rmi=Client.getRmi();
		try {
			//String fp = rmi.getInfo();
			if(inf!=null)
			{
				
				User.setText(inf.getTen());
				name .setText(inf.getNguoiDung());
				User.setTextFill(Color.RED);
				name.setTextFill(Color.RED);
				if(inf.getIdAnh()!=null)
				{
					byte[] data = rmi.dowload(inf.getIdAnh());
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
