package logic;

import java.util.Random;

import connection.SocketInterface;
import controls.Controller;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import resource.Resources;

public class LogicMain {

	private GameLoop gameLoop;
	private boolean[] nameAssigned;
	private Resources resources;

	public LogicMain(Resources resources) {
		this.resources = resources;

		nameAssigned = new boolean[resources.getNames().length];
		for (int i = 0; i < resources.getNames().length; i++) {
			nameAssigned[i] = false;
		}
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

	public Image getDefautCharacterProfile() {
		return Resources.CHARACTER_ATTACK_PROFILE[0];
	}

	public String getRandomName() {
		int length = Resources.NAMES.length;
		Random r = new Random();
		int index = r.nextInt(length);
		return Resources.NAMES[index];
	}

}
