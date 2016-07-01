package userinterface;

import entity.Player;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import logic.LogicMain;

public class GameInterface implements GameScene {

	private LogicMain logic;
	private ScreenInformation _screenInformation;
	private HBox overlayBox;
	private Canvas mainCanvas;
	private Canvas backgroundCanvas;
	private Scene scene;
	private StackPane _masterStackPane;
	private StackPane _fixedStackPane;
	private StackPane _waitInformation;
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
		_waitInformation = new StackPane();
		
		_waitInformation.setPrefSize(screenBounds.get_width(), screenBounds.get_height());
		_waitInformation.setMinSize(screenBounds.get_width(), screenBounds.get_height());
		_waitInformation.setMaxSize(screenBounds.get_width(), screenBounds.get_height());
		_waitInformation.setStyle("-fx-background-color:rgba(0,0,0, 0.8)");
		
		Label labelWait = new Label("Waiting for all players....");
		labelWait.setStyle("-fx-text-fill:rgba(255,255,255,0.9)");
		_waitInformation.getChildren().add( labelWait);
		
		_fixedStackPane.setPrefSize(screenBounds.get_width(), screenBounds.get_height());
		_fixedStackPane.setMinSize(screenBounds.get_width(), screenBounds.get_height());
		_fixedStackPane.setMaxSize(screenBounds.get_width(), screenBounds.get_height());
		
		root = new Group();
		scene = new Scene(_masterStackPane);
		
		buildOverlayBox(screenBounds);
		
		mainCanvas = new Canvas(screenBounds.get_width(), screenBounds.get_height());
		backgroundCanvas = new Canvas(screenBounds.get_width(), screenBounds.get_height());
		root.getChildren().add(backgroundCanvas);
		root.getChildren().add(mainCanvas);
		root.getChildren().add(overlayBox);
		root.getChildren().add(_waitInformation);
		_fixedStackPane.getChildren().add(root);
		_masterStackPane.getChildren().add(_fixedStackPane);
	}

	private void buildOverlayBox(ScreenInformation screenBounds) {
		overlayBox = new HBox();
		overlayBox.setPrefSize(screenBounds.get_width(), screenBounds.get_height());
		
		
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

	public HBox getOverlayBox() {
		return overlayBox;
	}
	
	public StackPane getWaitInformationPane(){
		return _waitInformation;
	}
	
	public static HBox createFace(Player p){
		HBox h = new HBox();
		Label l = p.getNameLabel();
		h.getChildren().add(l);
		return h;
	}

}
