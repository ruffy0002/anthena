package userinterface;

import controls.Controller;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
	private static final int FONT_SIZE = 32;
	public static final Font FONT_LABEL = new Font(PrimaryInterface.FONT_TITLE_LABLES, FONT_SIZE);
	private GameScene scenes[] = new GameScene[4];
	private SettingsInterface settingsInterface;
	private GameRoomInterface hostRoomInterface;
	private Scene prevScene;
	private boolean fullScreen = false;

	public PrimaryInterface() {
		initScreenInformation();
		resources = initResources();
		logic = new LogicMain(resources,this);
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
		stage.setResizable(false);
		stage.centerOnScreen();
		stage.setScene(scenes[0].getScene());
		stage.sizeToScene();
		stage.show();
		stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
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
					}

				} else {
					int result = gs.updateControl(keyCode);
					if (gs instanceof StartMenuInterface) {
						if (result == 1) {
							logic.hostGame();
							stage.setScene(scenes[2].getScene());
							if(fullScreen){
								stage.setFullScreen(true);
							}
						}
					} else if (gs instanceof GameRoomInterface) {
						if (result == 1) {
							if (logic.isAllPlayerReady()) {
								stage.setScene(scenes[3].getScene());
								stage.sizeToScene();
								if(fullScreen){
									stage.setFullScreen(true);
								}
								logic.initGameLoop();
								logic.startGameLoop();
							} else {
								hostRoomInterface.showNotReady();
							}
						}
					} else if (gs instanceof SettingsInterface) {
						if (result != -1) {
							if (result == 1) {
								stage.setFullScreen(false);
								_screenInformation.set_width(400);
								_screenInformation.set_height(300);
							} else if (result == 2) {
								stage.setFullScreen(false);
								_screenInformation.set_width(800);
								_screenInformation.set_height(600);
							} else if (result == 3) {
								stage.setFullScreen(false);
								_screenInformation.set_width(1200);
								_screenInformation.set_height(900);
							} else if (result == 4) {
								Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
								_screenInformation.set_width(bounds.getWidth());
								_screenInformation.set_height(bounds.getHeight());
								stage.setFullScreen(true);
								fullScreen = true;
							} else if (result == 5) {
								if (settingsInterface.isActive()) {
									settingsInterface.setActive(false);
									stage.setScene(prevScene);
									stage.sizeToScene();
									if(fullScreen){
										stage.setFullScreen(true);
									}
									prevScene = null;
								}
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
		logic.setGameRoomInterface(hostRoomInterface);
		hostRoomInterface.initControls(initControlsForStartScene(hostRoomInterface));
		return hostRoomInterface;
	}

	public GameScene initGameScene() {
		GameInterface gi = GameInterface.getInstance(logic, _screenInformation);
		initControlsForGameScene(gi.getScene());
		logic.init(gi, controller);
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

	public void goToLobby() {
		scenes[3] = initGameScene();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				stage.setScene(scenes[2].getScene());
				if(fullScreen){
					stage.setFullScreen(true);
				}
			}
		});
	}

}
