package application.autofill;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.robot.Robot;

public class Robo {
	Robot robot;

	public Robo() {
		try {
			robot = new Robot();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void paste(String ps) {
		Clipboard clipboard = Clipboard.getSystemClipboard();
		ClipboardContent content = new ClipboardContent();
		content.putString(ps);
		clipboard.setContent(content);
		robot.keyPress(KeyCode.CONTROL);
		robot.keyPress(KeyCode.V);
		robot.keyRelease(KeyCode.V);
		robot.keyRelease(KeyCode.CONTROL);
	}

	public static void copy(String ps) {
		Clipboard clipboard = Clipboard.getSystemClipboard();
		ClipboardContent content = new ClipboardContent();
		content.putString(ps);
		clipboard.setContent(content);
	}
}
