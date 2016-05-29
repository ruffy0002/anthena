package userinterface;

import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public interface GameScene {

	public void init(Rectangle2D screenBounds);

	public void initControls(EventHandler<KeyEvent> event);

	public Scene getScene();

	public int updateControl(KeyCode key);

}
