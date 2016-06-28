package userinterface;

import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import logic.LogicMain;

public class GameInterface implements GameScene {

	private LogicMain logic;
	private ScreenInformation _screenInformation;
	private Canvas overlayCanvas;
	private Canvas mainCanvas;
	private Canvas backgroundCanvas;
	private Scene scene;
	private StackPane _masterStackPane;
	private StackPane _fixedStackPane;
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
		_masterStackPane = new StackPane();
		_fixedStackPane = new StackPane();
		_fixedStackPane.setPrefSize(screenBounds.get_width(), screenBounds.get_height());
		_fixedStackPane.setMinSize(screenBounds.get_width(), screenBounds.get_height());
		_fixedStackPane.setMaxSize(screenBounds.get_width(), screenBounds.get_height());
		
		root = new Group();
		scene = new Scene(_masterStackPane);
		overlayCanvas = new Canvas(screenBounds.get_width(), screenBounds.get_height());
		mainCanvas = new Canvas(screenBounds.get_width(), screenBounds.get_height());
		backgroundCanvas = new Canvas(screenBounds.get_width(), screenBounds.get_height());
		root.getChildren().add(backgroundCanvas);
		root.getChildren().add(mainCanvas);
		root.getChildren().add(overlayCanvas);
		_fixedStackPane.getChildren().add(root);
		_masterStackPane.getChildren().add(_fixedStackPane);
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
		this._screenInformation = screenInformation;
		_masterStackPane.setPrefSize(screenInformation.get_width(), screenInformation.get_height());
		_masterStackPane.setMinSize(screenInformation.get_width(), screenInformation.get_height());
		_masterStackPane.setMaxSize(screenInformation.get_width(), screenInformation.get_height());
		double sx = screenInformation.get_width() / 800;
		double sy = screenInformation.get_height() / 600;
		root.setScaleX(sx);
		root.setScaleY(sy);
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
