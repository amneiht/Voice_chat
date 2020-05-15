package application.autofill;

import java.util.LinkedList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class AutoFill implements EventHandler<Event> {
	InfoList inf;
	ContextMenu contextMenu = new ContextMenu();
	LinkedList<MenuItem> mItem;
	TextField id;
	EventHandler<ActionEvent> ep;
	String past = "";

	public AutoFill(InfoList ip, TextField txp) {
		inf = ip;
		id = txp;
		ep = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				MenuItem s = (MenuItem) event.getSource();
				String lp = s.getText();
				id.setText(lp);
			}
		};
		id.setOnKeyReleased(this);
	}

	@Override
	public void handle(Event event) {
		String lp = id.getText().trim();
		if (lp.equals(past))
			return;
		else
			past = lp;
		List<String> ls = inf.getList(lp);
		contextMenu.getItems().clear();
		if (lp != null)
			for (String op : ls) {
				MenuItem mi = new MenuItem(op);
				mi.setOnAction(ep);
				contextMenu.getItems().add(mi);
			}
		contextMenu.show(id, Side.BOTTOM, 0.5, 0.5);
	}

}
