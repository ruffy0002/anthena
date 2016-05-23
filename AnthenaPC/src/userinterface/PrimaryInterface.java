package userinterface;

import controls.Controller;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import logic.GameLoop;
import resource.Resources;

public class PrimaryInterface extends Application {

	private Controller controller;

	@Override
	public void start(Stage stage) throws Exception {

		stage.setTitle("Anthena");

		Group root = new Group();
		Scene theScene = new Scene(root);
		stage.setScene(theScene);
		Canvas canvas = new Canvas(500, 500);
		root.getChildren().add(canvas);

		stage.show();

		initResources();
		initControls(theScene);
		initGameLoop(canvas);
	}

	private void initControls(Scene theScene) {
		controller = new Controller();
		controller.initController(theScene);
	}

	private void initGameLoop(Canvas canvas) {
		GameLoop gameLoop = new GameLoop(canvas, controller);
		gameLoop.start();
	}

	private void initResources() {
		Resources resource = new Resources();
	}

}
