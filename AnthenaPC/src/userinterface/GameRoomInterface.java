package userinterface;

import entity.Player;
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
	private VBox leftVBox;
	private VBox rightVBox;

	public static GameRoomInterface getStartScene(LogicMain logic, Rectangle2D screenBounds) {
		GameRoomInterface gri = new GameRoomInterface(logic, screenBounds);
		return gri;
	}

	private GameRoomInterface(LogicMain logic, Rectangle2D screenBounds) {
		this.logic = logic;
		init(screenBounds);
	}

	public void init(Rectangle2D screenBounds) {

		leftVBox = new VBox();
		rightVBox = new VBox();

		BorderPane border = new BorderPane();
		border.setPrefSize(screenBounds.getWidth(), screenBounds.getHeight());
		border.setStyle("-fx-background-color:black");

		Label l = new Label(
				"press enter again, to start game, this suppose to show the players who have join and can choose team.");
		l.setStyle("-fx-text-fill:white");

		border.setLeft(leftVBox);
		border.setRight(rightVBox);

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
		if (key.compareTo(KeyCode.SPACE) == 0) {
			Player p = logic.addNewRunner();
			leftVBox.getChildren().add(createNewRunnnerInterface(p));
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
		leftVBox.getChildren().add(createNewAttackerInterface(player));
	}

}
