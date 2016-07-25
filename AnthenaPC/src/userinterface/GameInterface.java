package userinterface;

import entity.Player;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import logic.LogicMain;

public class GameInterface implements GameScene {

	private static GameInterface gameInstance; 
	private LogicMain logic;
	private ScreenInformation _screenInformation;

	private HBox overlayBox;
	private static GridPane leftGrid;
	private static GridPane rightGrid;
	private static int panelFaceSize;
	private static int leftCounter = 0;
	private static int rightCounter = 0;
	private Canvas mainCanvas;
	private Canvas backgroundCanvas;

	private Scene scene;
	private StackPane _masterStackPane;
	private StackPane _fixedStackPane;
	private StackPane _waitInformation;
	private StackPane _gamePausePane;
	private Group root;

	public static GameInterface getInstance(LogicMain logic, ScreenInformation _screenInformation) {
		gameInstance = null;
		gameInstance = new GameInterface(logic, _screenInformation);
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
		_gamePausePane = new StackPane();

		_waitInformation.setPrefSize(screenBounds.get_width(), screenBounds.get_height());
		_waitInformation.setMinSize(screenBounds.get_width(), screenBounds.get_height());
		_waitInformation.setMaxSize(screenBounds.get_width(), screenBounds.get_height());
		_waitInformation.setStyle("-fx-background-color:rgba(0,0,0, 0.8)");

		Label labelWait = new Label("Waiting for all players....");
		labelWait.setStyle("-fx-text-fill:rgba(255,255,255,0.9)");
		_waitInformation.getChildren().add(labelWait);
		
		_gamePausePane.setPrefSize(screenBounds.get_width(), screenBounds.get_height());
		_gamePausePane.setMinSize(screenBounds.get_width(), screenBounds.get_height());
		_gamePausePane.setMaxSize(screenBounds.get_width(), screenBounds.get_height());
		_gamePausePane.setStyle("-fx-background-color:rgba(0,0,0, 0.8)");
		_gamePausePane.setOpacity(0);

		Label gamePause = new Label("Game Paused");
		gamePause.setStyle("-fx-text-fill:rgba(255,255,255,1)");
		_gamePausePane.getChildren().add(gamePause);

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
		root.getChildren().add(_gamePausePane);
		_fixedStackPane.getChildren().add(root);
		_masterStackPane.getChildren().add(_fixedStackPane);
	}

	private void buildOverlayBox(ScreenInformation screenBounds) {

		panelFaceSize = (int) (screenBounds.get_width() / 4);

		overlayBox = new HBox();
		overlayBox.setPrefSize(screenBounds.get_width(), screenBounds.get_height());
		leftGrid = new GridPane();
		leftGrid.setPrefWidth(screenBounds.get_width() / 2);
		leftGrid.setHgap(2);
		leftGrid.setVgap(2);

		rightGrid = new GridPane();
		rightGrid.setPrefWidth(screenBounds.get_width() / 2);
		rightGrid.setHgap(2);
		rightGrid.setVgap(2);

		overlayBox.getChildren().add(leftGrid);
		overlayBox.getChildren().add(rightGrid);
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

	public StackPane getWaitInformationPane() {
		return _waitInformation;
	}
	
	public StackPane getGamePausePane() {
		return _gamePausePane;
	}

	public void createFace(Player p) {
		HBox hbox = new HBox();
		hbox.setPrefSize(panelFaceSize, 20);
		
		hbox.setStyle("-fx-background-color:rgba(0,0,0,0.3)");
		Label label = new Label(p.getName());
		label.setTextFill(p.getColor());
		
		Label score = new Label(String.valueOf(p.getScore()));
		score.setTextFill(p.getColor());

		ImageView imageView = new ImageView();
		imageView.setFitWidth(20);
		imageView.setFitHeight(20);
		
		hbox.getChildren().add(imageView);
		hbox.getChildren().add(label);
		hbox.getChildren().add(score);
		HBox.setMargin(label, new Insets(0, 0, 0, 10));
		HBox.setMargin(score, new Insets(0, 0, 0, 10));

		if (p.getPlayerType() == Player.TYPE_RUNNER) {
			imageView.setImage(logic.getDefautCharacterProfile());
			int posX = leftCounter % 2;
			int posY = leftCounter / 2;
			leftGrid.add(hbox, posX, posY);
			leftCounter++;
		} else {
			imageView.setImage(logic.getDefautAttackProfile());
			imageView.setEffect(p.getPaintEffect());
			int posX = rightCounter % 2;
			int posY = rightCounter / 2;
			rightGrid.add(hbox, posX, posY);
			rightCounter++;
		}

		PanelSetFace psf = new PanelSetFace(hbox,label,score,imageView);
		p.setPanelSetFace(psf);
	}

	public void convertRunnerToStomper(Player player) {
		HBox hboxToMove = player.getPanelSet().getMasterBox();
		leftGrid.getChildren().remove(hboxToMove);
		createFace(player);
	}

}
