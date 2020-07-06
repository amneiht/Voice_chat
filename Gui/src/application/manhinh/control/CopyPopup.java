package application.manhinh.control;

import application.autofill.Robo;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

public class CopyPopup {
	private static ContextMenu pop = new ContextMenu();
	static String noidung;
	static {
		MenuItem im = new MenuItem("Copy");
		im.setOnAction(e -> {
			copy();
		});
		pop.getItems().addAll(im);
	}

	public static void openPop(Node e, String np) {
		noidung = np;
		pop.show(e, Side.BOTTOM, 0.5, 0.5);
	}

	private static void copy() {
		Robo.copy(noidung);
	}

	public static void openPop(MouseEvent e, String np) {
		noidung = np;
		pop.show((Node) e.getSource(), e.getScreenX(), e.getScreenY());
	}
}
