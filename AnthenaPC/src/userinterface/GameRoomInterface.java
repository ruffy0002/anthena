package userinterface;

import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GameRoomInterface implements GameScene {

	private static Scene scene;

	public static GameRoomInterface getStartScene(Rectangle2D screenBounds) {
		GameRoomInterface gri = new GameRoomInterface();
		gri.init(screenBounds);
		return gri;
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

	@Override
	public int updateControl(KeyCode key) {
		if (key.compareTo(KeyCode.UP) == 0) {

		} else if (key.compareTo(KeyCode.DOWN) == 0) {

		} else if (key.compareTo(KeyCode.ENTER) == 0) {
			return 1;
		}
		return -1;
	}

}
