package application.autofill;

import com.sun.glass.events.KeyEvent;
import com.sun.glass.ui.Application;
import com.sun.glass.ui.Robot;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

@SuppressWarnings("restriction")
public class Robo {
	Robot robot;


	public Robo() {
		try {
			robot = Application.GetApplication().createRobot();;
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
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}
}
