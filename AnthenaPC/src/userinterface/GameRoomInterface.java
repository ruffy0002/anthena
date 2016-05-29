package userinterface;

import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class GameRoomInterface implements GameScene {

	private static Scene scene;

	public static GameRoomInterface getStartScene(Rectangle2D screenBounds) {
		GameRoomInterface gri = new GameRoomInterface();
		gri.init(screenBounds);
		return gri;
	}

	public void init(Rectangle2D screenBounds) {
		BorderPane border = new BorderPane();
		border.setPrefSize(screenBounds.getWidth(), screenBounds.getHeight());
		border.setStyle("-fx-background-color:black");
		
		Label l = new Label("press enter again, to start game, this suppose to show the players who have join and can choose team.");
		l.setStyle("-fx-text-fill:white");
		border.setCenter(l);
		
		scene = new Scene(border);
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
