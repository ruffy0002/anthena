package userinterface;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class StartMenuInterface implements GameScene {

	private static Scene scene;
	private static StackPane _masterStackPane;
	private static StackPane _fixStackPane;
	private static int selector;
	private static final int TOTAL_ITEMS_SELECTABLE = 3;
	private static final int FONT_SIZE = 32;
	private static final Font FONT_LABEL = new Font(PrimaryInterface.FONT_TITLE_LABLES, FONT_SIZE);
	private Label[] lables = new Label[TOTAL_ITEMS_SELECTABLE];

	private ScreenInformation _screenInformation;
	private BorderPane border;

	public static StartMenuInterface getStartScene(ScreenInformation _screenInformation) {
		selector = 0;
		StartMenuInterface smi = new StartMenuInterface();
		smi.init(_screenInformation);
		return smi;
	}

	public void updateSceneSize(ScreenInformation _screenInformation) {
		this._screenInformation = _screenInformation;
		_masterStackPane.setPrefSize(_screenInformation.get_width(), _screenInformation.get_height());
		_masterStackPane.setMinSize(_screenInformation.get_width(), _screenInformation.get_height());
		_masterStackPane.setMaxSize(_screenInformation.get_width(), _screenInformation.get_height());
		double sx = _screenInformation.get_width() / 800;
		double sy = _screenInformation.get_height() / 600;
		border.setScaleX(sx);
		border.setScaleY(sy);
	}

	public void init(ScreenInformation _screenInformation) {
		this._screenInformation = _screenInformation;
		_masterStackPane = new StackPane();
		_fixStackPane = new StackPane();
		_fixStackPane.setPrefSize(_screenInformation.get_width(), _screenInformation.get_height());
		_fixStackPane.setMinSize(_screenInformation.get_width(), _screenInformation.get_height());
		_fixStackPane.setMaxSize(_screenInformation.get_width(), _screenInformation.get_height());
		
		border = new BorderPane();
		border.setPrefSize(_screenInformation.get_width(), _screenInformation.get_height());
		border.setStyle("-fx-background-color:black");

		lables[0] = new Label("Host Room");
		lables[0].setAlignment(Pos.CENTER);
		lables[0].setStyle("-fx-background-color:rgba(255,255,255,0.3); -fx-text-fill:rgba(255,255,255,1.0);");
		lables[0].setMinWidth(600);
		lables[0].setFont(FONT_LABEL);

		lables[1] = new Label("Settings");
		lables[1].setAlignment(Pos.CENTER);
		lables[1].setMinWidth(600);
		lables[1].setFont(FONT_LABEL);

		lables[2] = new Label("Exit");
		lables[2].setAlignment(Pos.CENTER);
		lables[2].setMinWidth(600);
		lables[2].setFont(FONT_LABEL);

		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);

		vbox.setMaxHeight(500);
		vbox.setMaxWidth(800);
		vbox.setMinWidth(800);
		vbox.setMinHeight(500);

		vbox.getChildren().add(lables[0]);
		vbox.getChildren().add(lables[1]);
		vbox.getChildren().add(lables[2]);

		border.setCenter(vbox);

		_fixStackPane.getChildren().add(border);
		_masterStackPane.getChildren().add(_fixStackPane);
		scene = new Scene(_masterStackPane);
	}

	public void initControls(EventHandler<KeyEvent> event) {
		scene.setOnKeyReleased(event);
	}

	public Scene getScene() {
		return scene;
	}

	public int updateControl(KeyCode key) {
		if (key.compareTo(KeyCode.UP) == 0) {
			if (selector > 0) {
				deHighLight(selector--);
				highlight(selector);
			}
		} else if (key.compareTo(KeyCode.DOWN) == 0) {
			if (selector + 1 < TOTAL_ITEMS_SELECTABLE) {
				deHighLight(selector++);
				highlight(selector);
			}
		} else if (key.compareTo(KeyCode.ENTER) == 0) {
			if (selector == 0) {
				return 1;
			} else if (selector == 1) {
				return 2;
			} else if (selector == 2) {

			}
		}
		return -1;
	}

	public void highlight(int index) {
		lables[index].setStyle("-fx-background-color:rgba(255,255,255,0.3); -fx-text-fill:rgba(255,255,255,1.0);");
	}

	public void deHighLight(int index) {
		lables[index].setStyle("-fx-text-fill:rgba(255,255,255,1.0);");
	}
}
