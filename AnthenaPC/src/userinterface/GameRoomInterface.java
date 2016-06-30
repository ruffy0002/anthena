package userinterface;

import java.util.TreeMap;

import entity.Player;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import logic.LogicMain;

public class GameRoomInterface implements GameScene {

	private static Scene scene;
	private LogicMain logic;
	private StackPane _mainComponent;
	private StackPane _masterStackPane;
	private StackPane _fixedStackPane;
	private StackPane statusMessageFrame;
	private VBox leftVBox;
	private VBox rightVBox;
	private ScreenInformation _screenInformation;
	private TreeMap<Integer, StackPane> playersFace = new TreeMap<Integer, StackPane>();

	public static final Font FONT_LABEL_READY = new Font(PrimaryInterface.FONT_TITLE_LABLES, 40);
	public static final Font FONT_LABEL_MSG = new Font(PrimaryInterface.FONT_TITLE_LABLES, 50);

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
		_masterStackPane = new StackPane();
		_fixedStackPane = new StackPane();
		_fixedStackPane.setPrefSize(_screenInformation.get_width(), _screenInformation.get_height());
		_fixedStackPane.setMaxSize(_screenInformation.get_width(), _screenInformation.get_height());
		_fixedStackPane.setMinSize(_screenInformation.get_width(), _screenInformation.get_height());

		leftVBox = new VBox();
		leftVBox.setPrefWidth(240);
		rightVBox = new VBox();
		rightVBox.setPrefWidth(240);

		StackPane leftTeamInfo = new StackPane();
		leftTeamInfo.setPrefSize(240, 40);
		leftTeamInfo.setMaxSize(240, 40);
		leftTeamInfo.setMinSize(240, 40);
		leftTeamInfo.setStyle("-fx-background-color:rgba(50,200,50,0.8)");

		Label leftBoxDesc = new Label("Defense Team");
		leftBoxDesc.setFont(PrimaryInterface.FONT_LABEL);
		leftBoxDesc.setTextFill(Color.WHITE);
		leftTeamInfo.getChildren().add(leftBoxDesc);

		leftVBox.getChildren().add(leftTeamInfo);

		StackPane rightTeamInfo = new StackPane();
		rightTeamInfo.setPrefSize(240, 40);
		rightTeamInfo.setMaxSize(240, 40);
		rightTeamInfo.setMinSize(240, 40);
		rightTeamInfo.setStyle("-fx-background-color:rgba(200,50,50,0.8)");

		Label rightBoxDesc = new Label("Attack Team");
		rightBoxDesc.setFont(PrimaryInterface.FONT_LABEL);
		rightBoxDesc.setTextFill(Color.WHITE);
		rightTeamInfo.getChildren().add(rightBoxDesc);

		rightVBox.getChildren().add(rightTeamInfo);

		_mainComponent = new StackPane();
		_mainComponent.setPrefSize(_screenInformation.get_width(), _screenInformation.get_height());

		statusMessageFrame = new StackPane();
		statusMessageFrame.setPrefSize(_screenInformation.get_width(), _screenInformation.get_height());
		statusMessageFrame.setOpacity(0);

		Label msg = new Label("Not All Players Are Ready");
		msg.setFont(FONT_LABEL_MSG);
		msg.setStyle("-fx-text-fill:rgba(255,255,255,1); -fx-background-color:rgba(0,0,0,1)");
		statusMessageFrame.getChildren().add(msg);

		BorderPane _mainBroderPane = new BorderPane();
		_mainBroderPane.setPrefSize(_screenInformation.get_width(), _screenInformation.get_height());
		_mainBroderPane.setStyle("-fx-background-color:black");

		Label l = new Label("Choose Your Team");
		l.setStyle("-fx-text-fill:white");

		_mainBroderPane.setLeft(leftVBox);
		_mainBroderPane.setRight(rightVBox);

		_mainBroderPane.setCenter(l);

		_mainComponent.getChildren().add(_mainBroderPane);
		_mainComponent.getChildren().add(statusMessageFrame);

		_fixedStackPane.getChildren().add(_mainComponent);
		_masterStackPane.getChildren().add(_fixedStackPane);
		scene = new Scene(_masterStackPane);
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
			logic.updatePlayerType(0, 2);
		} else if (key.compareTo(KeyCode.DOWN) == 0) {
			logic.updatePlayerType(0, 1);
		} else if (key.compareTo(KeyCode.ENTER) == 0) {
			return 1;
		}
		return -1;
	}

	public StackPane createNewRunnnerInterface(Player p) {
		double totalHeight = 80;
		double totalWidth = 240;

		StackPane masterSp = new StackPane();
		masterSp.setPrefSize(totalWidth, totalHeight);

		StackPane readyFrame = new StackPane();
		readyFrame.setPrefSize(totalWidth, totalHeight);
		readyFrame.setStyle("-fx-background-color:rgba(0,0,0,0.5)");

		Label readyLabel = new Label("READY");
		readyLabel.setStyle("-fx-text-fill:rgba(150,0,0,0.8)");
		readyLabel.setFont(FONT_LABEL_READY);
		readyLabel.setRotate(-20);
		readyFrame.getChildren().add(readyLabel);

		HBox mainBox = new HBox();
		mainBox.setStyle("-fx-background-color:white");
		mainBox.setPrefWidth(totalWidth);
		mainBox.setPrefHeight(totalHeight);

		StackPane imageStackPane = new StackPane();
		imageStackPane.setMinHeight(totalHeight);
		imageStackPane.setMaxHeight(totalHeight);
		imageStackPane.setMinWidth(totalHeight);
		imageStackPane.setMaxWidth(totalHeight);

		int imageMarginAround = 5;

		ImageView imageView = new ImageView();
		imageView.setImage(logic.getDefautCharacterProfile());
		imageView.setFitWidth(totalHeight - 2 * imageMarginAround);
		imageView.setFitHeight(totalHeight - 2 * imageMarginAround);
		imageStackPane.getChildren().add(imageView);

		VBox informationBox = new VBox();
		informationBox.setMinHeight(totalHeight);
		informationBox.setMaxHeight(totalHeight);
		informationBox.setMinWidth(totalHeight);
		informationBox.setMaxWidth(totalHeight);

		Label name = new Label(p.getName());
		name.setTextFill(p.getColor());
		informationBox.getChildren().add(name);

		Label type = new Label("SwordMan");
		informationBox.getChildren().add(type);

		mainBox.getChildren().add(imageStackPane);
		mainBox.getChildren().add(informationBox);

		masterSp.getChildren().add(mainBox);
		masterSp.getChildren().add(readyFrame);
		return masterSp;
	}

	public StackPane createNewAttackerInterface(Player p) {
		double totalHeight = 80;
		double totalWidth = 280;

		StackPane masterSp = new StackPane();
		masterSp.setPrefSize(totalWidth, totalHeight);

		StackPane readyFrame = new StackPane();
		readyFrame.setPrefSize(totalWidth, totalHeight);
		readyFrame.setStyle("-fx-background-color:rgba(0,0,0,0.5)");

		Label readyLabel = new Label("READY");
		readyLabel.setStyle("-fx-text-fill:rgba(150,0,0,0.8)");
		readyLabel.setFont(FONT_LABEL_READY);
		readyLabel.setRotate(-20);
		readyFrame.getChildren().add(readyLabel);

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
		imageView.setImage(logic.getDefautAttackProfile());
		imageView.setEffect(p.getPaintEffect());
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

		Label type = new Label("Stomper");
		informationBox.getChildren().add(type);

		mainBox.getChildren().add(sp);
		mainBox.getChildren().add(informationBox);

		masterSp.getChildren().add(mainBox);
		masterSp.getChildren().add(readyFrame);

		return masterSp;
	}

	public void addAttacker(Player player) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (playersFace.containsKey(player.getPlayer_id())) {
					StackPane temp = playersFace.get(player.getPlayer_id());
					leftVBox.getChildren().remove(temp);
					rightVBox.getChildren().remove(temp);
				}
				StackPane temp = createNewAttackerInterface(player);
				playersFace.put(player.getPlayer_id(), temp);
				rightVBox.getChildren().add(temp);
			}
		});
	}

	private void addRunner(Player player) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (playersFace.containsKey(player.getPlayer_id())) {
					StackPane temp = playersFace.get(player.getPlayer_id());
					leftVBox.getChildren().remove(temp);
					rightVBox.getChildren().remove(temp);
				}
				StackPane temp = createNewRunnnerInterface(player);
				playersFace.put(player.getPlayer_id(), temp);
				leftVBox.getChildren().add(temp);
			}
		});
	}

	public void addPlayer(Player player) {
		if (player.getPlayerType() == Player.TYPE_RUNNER) {
			addRunner(player);
		} else if (player.getPlayerType() == Player.TYPE_STOMPPER) {
			addAttacker(player);
		}
		updatePlayerStatus(player);
	}

	public void updatePlayerStatus(Player player) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (playersFace.containsKey(player.getPlayer_id())) {
					StackPane temp = playersFace.get(player.getPlayer_id());
					StackPane temp2 = (StackPane) temp.getChildren().get(1);
					if (player.getStatus() == Player.READY) {
						temp2.setOpacity(1);
					} else {
						temp2.setOpacity(0);
					}
				}
			}
		});
	}

	public void updateSceneSize(ScreenInformation _screenInformation) {
		this._screenInformation = _screenInformation;
		_masterStackPane.setPrefSize(_screenInformation.get_width(), _screenInformation.get_height());
		_masterStackPane.setMinSize(_screenInformation.get_width(), _screenInformation.get_height());
		_masterStackPane.setMaxSize(_screenInformation.get_width(), _screenInformation.get_height());
		double sx = _screenInformation.get_width() / 800;
		double sy = _screenInformation.get_height() / 600;
		_mainComponent.setScaleX(sx);
		_mainComponent.setScaleY(sy);
	}

	private double msgOpacity = 1;
	private Timeline timeline;

	public void showNotReady() {
		statusMessageFrame.setOpacity(1);
		msgOpacity = 1;
		if (timeline != null) {
			timeline.stop();
			timeline = null;
		}
		timeline = new Timeline(new KeyFrame(Duration.millis(14), ae -> doSomething()));
		timeline.setCycleCount(100);
		timeline.play();
	}

	private void doSomething() {
		msgOpacity = msgOpacity - 0.01;
		statusMessageFrame.setOpacity(msgOpacity);
	}

}
