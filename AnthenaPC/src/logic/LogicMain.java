package logic;

import connection.SocketInterface;
import controls.Controller;
import javafx.scene.canvas.Canvas;
import resource.Resources;

public class LogicMain {

	private GameLoop gameLoop;

	public LogicMain() {
	}

	public void initGameLoop(Canvas mainCanvas, Canvas backgroundCanvas, Controller controller, Resources resources) {
		gameLoop = new GameLoop(mainCanvas, backgroundCanvas, controller, resources);
	}

	public void startGameLoop() {
		gameLoop.start();
	}
	
	public void hostGame() {
		SocketInterface.broadcastRoom(this);
	}

	public void addAttack(float x, float y) {
		gameLoop.createAttack(x, y);
	}

	
}
