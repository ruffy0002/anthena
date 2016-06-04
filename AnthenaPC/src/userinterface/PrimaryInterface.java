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
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import logic.GameLoop;
import logic.LogicMain;
import resource.Resources;

public class PrimaryInterface extends Application {

	private Controller controller;
	private Resources resources;
	private LogicMain logic;

	static final double ASPECT_RATIO_W_TO_H = 10.0 / 16.0;

	private Stage stage;
	public static ScreenInformation _screenInformation;

	public static final String FONT_TITLE_LABLES = "lucida sans";
	private GameScene scenes[] = new GameScene[4];
	private SettingsInterface settingsInterface;
	private GameRoomInterface hostRoomInterface;
	private Scene prevScene;

	public PrimaryInterface() {

		initScreenInformation();
		resources = initResources();
		logic = new LogicMain(resources);

		scenes[0] = initStartScene();
		scenes[1] = initSettingScene();
		scenes[2] = initGameRoomScene(logic);
		scenes[3] = initGameScene();
	}

	private GameScene initSettingScene() {
		settingsInterface = new SettingsInterface(_screenInformation);
		settingsInterface.initControls(initControlsForStartScene(settingsInterface));
		return settingsInterface;
	}

	private void initScreenInformation() {
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		// compare here to get the size
		_screenInformation = new ScreenInformation(800, 600, false);
	}

	@Override
	public void start(Stage stage) throws Exception {

		this.stage = stage;
		stage.setTitle("Title of Game");

		stage.setX(0);
		stage.setY(0);

		stage.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				/*
				 * double newWidth = newValue.doubleValue(); double newHeight =
				 * newWidth * ASPECT_RATIO_W_TO_H; Rectangle2D rec = new
				 * Rectangle2D(0, 0, newWidth, newHeight);
				 * stage.setWidth(newWidth); stage.setHeight(newHeight);
				 */
			}
		});

		stage.setResizable(false);
		stage.centerOnScreen();

		stage.setScene(scenes[0].getScene());
		stage.sizeToScene();
		stage.show();
		stage.setFullScreenExitKeyCombination(KeyCombination.keyCombination("Ctrl+k"));
	}

	private GameScene initStartScene() {
		StartMenuInterface startMenu = StartMenuInterface.getStartScene(_screenInformation);
		startMenu.initControls(initControlsForStartScene(startMenu));
		return startMenu;
	}

	private EventHandler<KeyEvent> initControlsForStartScene(GameScene gs) {
		EventHandler<KeyEvent> keyUp = new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode keyCode = e.getCode();
				if (keyCode.compareTo(KeyCode.ESCAPE) == 0) {

					if (!settingsInterface.isActive()) {
						settingsInterface.setActive(true);
						prevScene = stage.getScene();
						stage.setScene(settingsInterface.getScene());
						stage.sizeToScene();
					} else {
						settingsInterface.setActive(false);
						stage.setScene(prevScene);
						stage.sizeToScene();
						prevScene = null;
					}

				} else {
					int result = gs.updateControl(keyCode);
					if (gs instanceof StartMenuInterface) {
						if (result == 1) {
							logic.hostGame();
							stage.setScene(scenes[2].getScene());
						}
					} else if (gs instanceof GameRoomInterface) {
						if (result == 1) {
							stage.setScene(scenes[3].getScene());
							logic.startGameLoop();
						}
					} else if (gs instanceof SettingsInterface) {
						if (result != -1) {
							if (result == 1) {
								_screenInformation.set_width(800);
								_screenInformation.set_height(600);
							} else if (result == 2) {
								_screenInformation.set_width(1200);
								_screenInformation.set_height(900);
							} else if (result == 3) {
								Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
								_screenInformation.set_width(bounds.getWidth());
								_screenInformation.set_height(bounds.getHeight());
								stage.setFullScreen(true);
							}

							for (int i = 0; i < scenes.length; i++) {
								scenes[i].updateSceneSize(_screenInformation);
							}
							stage.sizeToScene();
							stage.centerOnScreen();
						}
					}
				}
			}
		};
		return keyUp;
	}

	private GameScene initGameRoomScene(LogicMain logic) {
		hostRoomInterface = GameRoomInterface.getStartScene(logic, _screenInformation);
		logic.setHostRoomInterface(hostRoomInterface);
		hostRoomInterface.initControls(initControlsForStartScene(hostRoomInterface));
		return hostRoomInterface;
	}

	private GameScene initGameScene() {
		GameInterface gi = GameInterface.getInstance(logic, _screenInformation);
		initControlsForGameScene(gi.getScene());
		logic.initGameLoop(gi, controller, resources);
		return gi;
	}

	private void initControlsForGameScene(Scene theScene) {
		controller = new Controller();
		controller.initController(theScene);
	}

	private Resources initResources() {
		Resources resource = new Resources();
		return resource;
	}

}
