package userinterface;

import controls.Controller;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import logic.GameLoop;
import logic.LogicMain;
import resource.Resources;

public class PrimaryInterface extends Application {

	private Controller controller;
	private Resources resources;
	public static Rectangle2D _screenBounds;

	private Stage stage;
	private Scene scenes[] = new Scene[3];
	private LogicMain logic;

	public PrimaryInterface() {
		resources = initResources();
		logic = new LogicMain();

		_screenBounds = Screen.getPrimary().getVisualBounds();
		scenes[0] = initStartScene();
		scenes[1] = initGameRoomScene();
		scenes[2] = initGameScene();

	}

	@Override
	public void start(Stage stage) throws Exception {

		this.stage = stage;
		stage.setTitle("Title of Game");

		stage.setX(0);
		stage.setY(0);

		stage.setWidth(_screenBounds.getWidth());
		stage.setHeight(_screenBounds.getHeight());

		stage.setScene(scenes[0]);

		stage.show();
	}

	private Scene initStartScene() {
		StartMenuInterface startMenu = StartMenuInterface.getStartScene(_screenBounds);
		startMenu.initControls(initControlsForStartScene(startMenu));
		return startMenu.getScene();
	}

	private EventHandler<KeyEvent> initControlsForStartScene(GameScene gs) {
		EventHandler<KeyEvent> keyUp = new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode keyCode = e.getCode();
				int result = gs.updateControl(keyCode);
				if (gs instanceof StartMenuInterface) {
					if (result == 1) {
						logic.hostGame();
						stage.setScene(scenes[1]);
					}
				} else if (gs instanceof GameRoomInterface) {
					if (result == 1) {
						stage.setScene(scenes[2]);
						logic.startGameLoop();
					}
				}
			}
		};
		return keyUp;
	}

	private Scene initGameRoomScene() {
		GameRoomInterface hostRoomInterface = GameRoomInterface.getStartScene(_screenBounds);
		hostRoomInterface.initControls(initControlsForStartScene(hostRoomInterface));
		return hostRoomInterface.getScene();
	}

	private Scene initGameScene() {
		Group root = new Group();
		Scene theScene = new Scene(root);
		theScene.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

			}
		});

		Canvas mainCanvas = new Canvas(_screenBounds.getWidth(), _screenBounds.getHeight());
		Canvas backgroundCanvas = new Canvas(_screenBounds.getWidth(), _screenBounds.getHeight());
		root.getChildren().add(backgroundCanvas);
		root.getChildren().add(mainCanvas);

		initControlsForGameScene(theScene);
		logic.initGameLoop(mainCanvas, backgroundCanvas, controller, resources);

		return theScene;
	}

	private void initControlsForGameScene(Scene theScene) {
		controller = new Controller();
		controller.initController(theScene);
	}

	private Resources initResources() {
		Resources resource = new Resources();
		resource.loadMap();
		resource.loadCharacterSprites();
		resource.loadColors();
		return resource;
	}

}
