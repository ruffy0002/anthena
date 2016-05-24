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
		Canvas mainCanvas = new Canvas(500, 500);
		Canvas backgroundCanvas = new Canvas(500, 500);
		root.getChildren().add(backgroundCanvas);
		root.getChildren().add(mainCanvas);
		stage.show();

		Resources resources = initResources();
		initControls(theScene);
		initGameLoop(mainCanvas, backgroundCanvas, resources);
	}

	private void initControls(Scene theScene) {
		controller = new Controller();
		controller.initController(theScene);
	}

	private void initGameLoop(Canvas mainCanvas, Canvas backgroundCanvas, Resources resource) {
		GameLoop gameLoop = new GameLoop(mainCanvas, backgroundCanvas, controller, resource);
		gameLoop.start();
	}

	private Resources initResources() {
		Resources resource = new Resources();
		resource.loadMap();
		resource.loadCharacterSprites();
		resource.loadColors();
		return resource;
	}

}
