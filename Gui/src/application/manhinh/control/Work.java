package application.manhinh.control;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import np.com.ngopal.control.AutoFillTextBox;

public class Work implements Initializable {

	@FXML
	private ScrollPane chatview;
	@FXML
	private VBox mainBox;
	@FXML
	private Button testBtn;
	@FXML
	private VBox group;
	@FXML
	private HBox cbar ;
	public void downloadFile(String idFile) {

	}

	public void chooseGroup(String group) {

	}

	public void changeGroup(String id) {
		System.out.println(id);
	}

	EventHandler<Event> enter, exit;

	public Work() {
		enter = new EventHandler<Event>() {

			@Override
			public void handle(Event e) {
				// TODO Auto-generated method stub
				Label lb = (Label) e.getSource();
				lb.setUnderline(true);
				lb.setTextFill(Color.BLUE);
				lb.setCursor(Cursor.HAND);
			}
		};
		exit = new EventHandler<Event>() {

			@Override
			public void handle(Event e) {
				// TODO Auto-generated method stub
				Label lb = (Label) e.getSource();
				lb.setUnderline(false);
				lb.setTextFill(Color.BLACK);
			}
		};
	}

	public void initialize(URL location, ResourceBundle resources) {
	       ObservableList<String> data = FXCollections.observableArrayList();
	        String[] s = new String[]{"apple","ball","cat","doll","elephant",
	            "fight","georgeous","height","ice","jug",
	             "aplogize","bank","call","done","ego",
	             "finger","giant","hollow","internet","jumbo",
	             "kilo","lion","for","length","primary","stage",
	             "scene","zoo","jumble","auto","text",
	            "root","box","items","hip-hop","himalaya","nepal",
	            "kathmandu","kirtipur","everest","buddha","epic","hotel"};

	            for(int j=0; j<s.length; j++){
	                data.add(s[j]);
	            }

	        //Layout
	        AutoFillTextBox<String> box = new AutoFillTextBox<String>(data);
	        cbar.getChildren().add(box);
	}

}
