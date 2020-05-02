package application.manhinh.control;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Work implements Initializable {
	public int test =1 ;
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
	@FXML
	private TextField fill ;
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
		test ++;
		mainBox.setOnDragOver(new EventHandler<DragEvent>() {
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
		mainBox.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    String filePath = null;
                    for (File file:db.getFiles()) {
                        filePath = file.getAbsolutePath();
                        System.out.println(filePath);
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });
		
	}
	
	void showRq(String gp) {
		try {
			// StaticStage.pri = primaryStage;
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setControllerFactory(e -> {
				return new ReQuest(gp);
			});
			Parent root = fxmlLoader.load(getClass().getResource("/application/manhinh/ReQuest.fxml").openStream());
			Scene sen = new Scene(root);
			Stage ip = new Stage();
			ip.setScene(sen);
			ip.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
