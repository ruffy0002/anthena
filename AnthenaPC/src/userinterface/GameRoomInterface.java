package userinterface;

import entity.Player;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import logic.LogicMain;

public class GameRoomInterface implements GameScene {

	private static Scene scene;
	private LogicMain logic;
	private BorderPane _mainComponent;
	private VBox leftVBox;
	private VBox rightVBox;
	private ScreenInformation _screenInformation;

	public static GameRoomInterface getStartScene(LogicMain logic, ScreenInformation _screenInformation) {
		GameRoomInterface gri = new GameRoomInterface(logic, _screenInformation);
		return gri;
	}

	private GameRoomInterface(LogicMain logic, ScreenInformation _screenInformation) {
		this.logic = logic;
		this._screenInformation = _screenInformation;
		init(_screenInformation);
	}

	public void init(ScreenInformation _screenInformation) {

		leftVBox = new VBox();
		rightVBox = new VBox();

		_mainComponent = new BorderPane();
		_mainComponent.setPrefSize(_screenInformation.get_width(), _screenInformation.get_height());
		_mainComponent.setStyle("-fx-background-color:black");

		Label l = new Label("lol");
		l.setStyle("-fx-text-fill:white");

		_mainComponent.setLeft(leftVBox);
		_mainComponent.setRight(rightVBox);

		_mainComponent.setCenter(l);

		scene = new Scene(_mainComponent);
	}

	public void initControls(EventHandler<KeyEvent> event) {
		scene.setOnKeyReleased(event);
	}

	public Scene getScene() {
		return scene;
	}

	@Override
	public int updateControl(KeyCode key) {
		if (key.compareTo(KeyCode.SPACE) == 0) {
			Player p = logic.addNewRunner();
			if (p != null) {
				leftVBox.getChildren().add(createNewRunnnerInterface(p));
			}
		} else if (key.compareTo(KeyCode.UP) == 0) {

		} else if (key.compareTo(KeyCode.DOWN) == 0) {

		} else if (key.compareTo(KeyCode.ENTER) == 0) {
			return 1;
		}
		return -1;
	}

	public HBox createNewRunnnerInterface(Player p) {
		double totalHeight = 80;
		double totalWidth = 240;

		HBox mainBox = new HBox();
		mainBox.setStyle("-fx-background-color:white");
		mainBox.setPrefWidth(totalWidth);
		mainBox.setPrefHeight(totalHeight);

		StackPane sp = new StackPane();
		sp.setMinHeight(totalHeight);
		sp.setMaxHeight(totalHeight);
		sp.setMinWidth(totalHeight);
		sp.setMaxWidth(totalHeight);

		int imageMarginAround = 5;

		ImageView imageView = new ImageView();
		imageView.setImage(logic.getDefautCharacterProfile());
		imageView.setFitWidth(totalHeight - 2 * imageMarginAround);
		imageView.setFitHeight(totalHeight - 2 * imageMarginAround);
		sp.getChildren().add(imageView);

		VBox informationBox = new VBox();
		informationBox.setMinHeight(totalHeight);
		informationBox.setMaxHeight(totalHeight);
		informationBox.setMinWidth(totalHeight);
		informationBox.setMaxWidth(totalHeight);

		Label name = new Label(p.getName());
		informationBox.getChildren().add(name);

		Label type = new Label("SwordMan");
		informationBox.getChildren().add(type);

		mainBox.getChildren().add(sp);
		mainBox.getChildren().add(informationBox);

		return mainBox;
	}

	public HBox createNewAttackerInterface(Player p) {
		double totalHeight = 80;
		double totalWidth = 240;

		HBox mainBox = new HBox();
		mainBox.setStyle("-fx-background-color:white");
		mainBox.setPrefWidth(totalWidth);
		mainBox.setPrefHeight(totalHeight);

		StackPane sp = new StackPane();
		sp.setMinHeight(totalHeight);
		sp.setMaxHeight(totalHeight);
		sp.setMinWidth(totalHeight);
		sp.setMaxWidth(totalHeight);

		int imageMarginAround = 5;

		ImageView imageView = new ImageView();
		imageView.setImage(logic.getDefautCharacterProfile());
		imageView.setFitWidth(totalHeight - 2 * imageMarginAround);
		imageView.setFitHeight(totalHeight - 2 * imageMarginAround);
		sp.getChildren().add(imageView);

		VBox informationBox = new VBox();
		informationBox.setMinHeight(totalHeight);
		informationBox.setMaxHeight(totalHeight);
		informationBox.setMinWidth(totalHeight);
		informationBox.setMaxWidth(totalHeight);

		Label name = new Label(p.getName());
		informationBox.getChildren().add(name);

		Label type = new Label("SwordMan");
		informationBox.getChildren().add(type);

		mainBox.getChildren().add(sp);
		mainBox.getChildren().add(informationBox);

		return mainBox;
	}

	public void addAttacker(Player player) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				rightVBox.getChildren().add(createNewAttackerInterface(player));
			}
		});
	}

	public void updateSceneSize(ScreenInformation _screenInformation) {
		this._screenInformation = _screenInformation;
		_mainComponent.setPrefSize(_screenInformation.get_width(), _screenInformation.get_height());
	}

}
