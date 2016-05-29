package userinterface;

import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class StartMenuInterface implements GameScene {

	private static Scene scene;

	public static StartMenuInterface getStartScene(Rectangle2D screenBounds) {
		StartMenuInterface smi = new StartMenuInterface();
		smi.init(screenBounds);
		return smi;
	}

	public void init(Rectangle2D screenBounds) {
		Group root = new Group();
		Canvas mainCanvas = new Canvas(screenBounds.getWidth(), screenBounds.getHeight());
		root.getChildren().add(mainCanvas);
		scene = new Scene(root);
	}

	public void initControls(EventHandler<KeyEvent> event) {
		scene.setOnKeyReleased(event);
	}

	public Scene getScene() {
		return scene;
	}

	public int updateControl(KeyCode key) {
		if (key.compareTo(KeyCode.UP) == 0) {

		} else if (key.compareTo(KeyCode.DOWN) == 0) {

		} else if (key.compareTo(KeyCode.ENTER) == 0) {
			return 1;
		}
		return -1;
	}
}