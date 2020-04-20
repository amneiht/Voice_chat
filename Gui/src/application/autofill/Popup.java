package application.autofill;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class Popup implements EventHandler<Event> {
	private String dta = "";

	public void Text(Event e) {
	}

	ContextMenu contextMenu;
	String[] member;
	String gtext;
	int length = 5;

	private void createMenu(String text) {
		//gtext = text;
		contextMenu.getItems().clear();
		int d = 0;
		for (int i = 0; i < member.length; i++) {
			if (cmp(text, member[i])) {
				d++;
				contextMenu.getItems().add(mItem[i]);
			}
			if (d > length)
				break;
		}

	}

	private static String getText(String d, int h) {
		int lg = d.length();
		if (lg == 0)
			return "";
		int a = 0, b = d.length();

		for (int i = h - 1; i > 0; i--) {
			if (d.charAt(i) == ' ') {
				a = i;
				break;
			}
		}
		for (int i = h; i < lg; i++) {
			if (d.charAt(i) == ' ') {
				b = i;
				break;
			}
		}
		String text = d.substring(a, b).trim();
		return text;
	}

	// private void
	private boolean cmp(String a, String b) {
		if (a.length() >= b.length())
			return false;
		for (int i = 0; i < a.length(); i++) {
			if (a.charAt(i) != b.charAt(i)) {
				return false;
			}
		}
		return true;
	}

	private static int where(String lnew, String lold) {
		int l = Math.min(lnew.length(), lold.length());
		for (int i = 0; i < l; i++) {
			if (lnew.charAt(i) != lold.charAt(i)) {
				return i;
			}
		}

		return lnew.length();
	}

	private void paste(ActionEvent ect) {
		MenuItem im = (MenuItem) ect.getSource();
		String d = im.getText();
		int p = gtext.length();
		String ps = d.substring(p)+" ";
		rb.paste(ps);
	}

	EventHandler<ActionEvent> addtext;
	MenuItem[] mItem;
	Robo rb = new Robo();

	public Popup(String[] p) {
		contextMenu = new ContextMenu();
		member = p;
		mItem = new MenuItem[member.length];
		addtext = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				paste(event);

			}
		};
		for (int i = 0; i < member.length; i++) {
			mItem[i] = new MenuItem(member[i]);
			mItem[i].setOnAction(addtext);
		}

	}
	public Popup(String[] p,String note) {
		contextMenu = new ContextMenu();
		member = new String[p.length];
		mItem = new MenuItem[member.length];
		addtext = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				paste(event);

			}
		};
		for (int i = 0; i < member.length; i++) {
			member[i]=note+p[i];
			mItem[i] = new MenuItem(member[i]);
			mItem[i].setOnAction(addtext);
		}

	}
	@Override
	public void handle(Event e) {
		//
		TextField v = (TextField) e.getSource();
		String lg = v.getText();
		if (lg.equals(dta))
			return;
		int h = where(lg, dta);
		dta = lg;
		gtext = getText(lg, h);
		contextMenu.hide();
		if(gtext.length()<1) return ;
		createMenu(gtext);	
		contextMenu.show(v,Side.BOTTOM,0.5,0.5 );
	}
}
