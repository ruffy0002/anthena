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

public class SettingsInterface implements GameScene {

	private Scene _scene;
	private int _selector;
	private ScreenInformation _screenInformation;
	private boolean isActive;
	private BorderPane _mainComponent;
	private static final int TOTAL_ITEMS_SELECTABLE = 3;
	private static final int FONT_SIZE = 32;
	private static final Font FONT_LABEL = new Font(PrimaryInterface.FONT_TITLE_LABLES, FONT_SIZE);
	private Label[] lables = new Label[3];

	public SettingsInterface(ScreenInformation _screenInformation) {
		isActive = false;
		init(_screenInformation);
	}

	public void init(ScreenInformation screenBounds) {
		this._screenInformation = screenBounds;
		_mainComponent = new BorderPane();
		_mainComponent.setPrefSize(_screenInformation.get_width(), _screenInformation.get_height());
		_mainComponent.setStyle("-fx-background-color:rgba(127,127,0,0.3)");

		lables[0] = new Label("800x600");
		lables[0].setStyle("-fx-background-color:rgba(255,255,255,0.3); -fx-text-fill:rgba(255,255,255,1.0);");
		lables[0].setMinWidth(600);
		lables[0].setFont(FONT_LABEL);

		lables[1] = new Label("1200x900");
		lables[1].setStyle("-fx-text-fill:rgba(255,255,255,1.0);");
		lables[1].setMinWidth(600);
		lables[1].setFont(FONT_LABEL);
		
		lables[2] = new Label("fullScreen");
		lables[2].setStyle("-fx-text-fill:rgba(255,255,255,1.0);");
		lables[2].setMinWidth(600);
		lables[2].setFont(FONT_LABEL);

		VBox vbox = new VBox();
		vbox.getChildren().add(lables[0]);
		vbox.getChildren().add(lables[1]);
		vbox.getChildren().add(lables[2]);
		_mainComponent.setCenter(vbox);

		_scene = new Scene(_mainComponent);
	}

	public void initControls(EventHandler<KeyEvent> event) {
		_scene.setOnKeyReleased(event);
	}

	public Scene getScene() {
		return _scene;
	}

	public int updateControl(KeyCode key) {
		if (key.compareTo(KeyCode.UP) == 0) {
			if (_selector > 0) {
				deHighLight(_selector--);
				highlight(_selector);
			}
		} else if (key.compareTo(KeyCode.DOWN) == 0) {
			if (_selector + 1 < TOTAL_ITEMS_SELECTABLE) {
				deHighLight(_selector++);
				highlight(_selector);
			}
		} else if (key.compareTo(KeyCode.ENTER) == 0) {
			if (_selector == 0) {
				return 1;
			} else if (_selector == 1) {
				return 2;
			} else if (_selector == 2) {
				return 3;
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

	public void updateSceneSize(ScreenInformation screenBounds) {
		_mainComponent.setMinWidth(screenBounds.get_width());
		_mainComponent.setMaxWidth(screenBounds.get_width());
		_mainComponent.setMinHeight(screenBounds.get_height());
		_mainComponent.setMaxHeight(screenBounds.get_height());
		_mainComponent.setPrefSize(screenBounds.get_width(), screenBounds.get_height());
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
