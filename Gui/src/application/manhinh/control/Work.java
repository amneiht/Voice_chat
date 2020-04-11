package application.manhinh.control;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class Work implements Initializable {

	@FXML
	private ScrollPane chatview;
	@FXML
	private VBox mainBox ;
	@FXML
	private Button testBtn;
	
	public void downloadFile(String idFile)
	{
		
	}
	public void chooseGroup(String group)
	{
		
	}
	
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		System.out.println("test");
		mainBox.setSpacing(10);
		for(int i=0;i<60;i++)
		{
			Label lb = new Label("test :"+i);
			mainBox.getChildren().add(lb);
		}
		mainBox.getChildren().clear();
	}

}
