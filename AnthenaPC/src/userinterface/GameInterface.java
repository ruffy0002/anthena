package userinterface;

import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import logic.LogicMain;

public class GameInterface implements GameScene {

	private LogicMain logic;
	private ScreenInformation _screenInformation;
	private Canvas overlayCanvas;
	private Canvas mainCanvas;
	private Canvas backgroundCanvas;
	private Scene scene;
	private Group root;

	public static GameInterface getInstance(LogicMain logic, ScreenInformation _screenInformation) {
		GameInterface gameInstance = new GameInterface(logic, _screenInformation);
		return gameInstance;
	}

	private GameInterface(LogicMain logic, ScreenInformation _screenInformation) {
		this.logic = logic;
		this._screenInformation = _screenInformation;

		init(_screenInformation);
	}

	public void init(ScreenInformation screenBounds) {
		root = new Group();
		scene = new Scene(root);
		overlayCanvas = new Canvas(screenBounds.get_width(), screenBounds.get_height());
		mainCanvas = new Canvas(screenBounds.get_width(), screenBounds.get_height());
		backgroundCanvas = new Canvas(screenBounds.get_width(), screenBounds.get_height());
		root.getChildren().add(backgroundCanvas);
		root.getChildren().add(mainCanvas);
		root.getChildren().add(overlayCanvas);
	}

	public void initControls(EventHandler<KeyEvent> event) {
	}

	public Scene getScene() {
		return scene;
	}

	public int updateControl(KeyCode key) {
		return 0;
	}

	public void updateSceneSize(ScreenInformation screenInformation) {
		overlayCanvas.setWidth(screenInformation.get_width());
		overlayCanvas.setHeight(screenInformation.get_height());
		mainCanvas.setWidth(screenInformation.get_width());
		mainCanvas.setHeight(screenInformation.get_height());
		backgroundCanvas.setWidth(screenInformation.get_width());
		backgroundCanvas.setHeight(screenInformation.get_height());
		logic.updateScale(_screenInformation);
	}

	public Canvas getMainCanvas() {
		return mainCanvas;
	}

	public Canvas getBackgroundCanvas() {
		return backgroundCanvas;
	}

	public Canvas getOverlayCanvas() {
		return overlayCanvas;
	}

}
