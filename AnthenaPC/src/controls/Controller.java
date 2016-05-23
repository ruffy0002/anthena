package controls;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Controller {

	private ArrayList<KeyCode> inputCode = new ArrayList<KeyCode>();

	public Controller() {
	}

	public void initController(Scene theScene) {
		EventHandler<KeyEvent> keyDown = new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode keyCode = e.getCode();
				if (!inputCode.contains(keyCode)) {
					inputCode.add(keyCode);
				}
			}
		};

		EventHandler<KeyEvent> keyUp = new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode keyCode = e.getCode();
				if (inputCode.contains(keyCode)) {
					inputCode.remove(keyCode);
				}
			}
		};

		theScene.setOnKeyPressed(keyDown);
		theScene.setOnKeyReleased(keyUp);
	}

	public ArrayList<KeyCode> getKeyCodes() {
		return inputCode;
	}
}
