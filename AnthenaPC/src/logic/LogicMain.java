package logic;

import java.util.Random;

import connection.SocketInterface;
import controls.Control;
import controls.Controller;
import entity.Player;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import resource.Resources;
import userinterface.GameRoomInterface;

public class LogicMain {

	private GameLoop gameLoop;
	private boolean[] nameAssigned;
	private Resources resources;
	private GameRoomInterface hostRoomInterface;

	private int controlsIndex = 0;

	public LogicMain(Resources resources, GameRoomInterface hostRoomInterface) {
		this.resources = resources;
		this.hostRoomInterface = hostRoomInterface;

		nameAssigned = new boolean[resources.getNames().length];
		for (int i = 0; i < resources.getNames().length; i++) {
			nameAssigned[i] = false;
		}
	}

	public void initGameLoop(Canvas mainCanvas, Canvas backgroundCanvas, Controller controller, Resources resources) {
		gameLoop = new GameLoop(mainCanvas, backgroundCanvas, controller, resources);
	}

	public void startGameLoop() {
		gameLoop.initGameLoop();
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

	public Color getRandomColor() {
		Random random = new Random();
		return Resources.COLORS[random.nextInt(Resources.COLORS.length)];
	}

	public Control getNextControl() {
		if (controlsIndex < Resources.controls.size()) {
			return Resources.controls.get(controlsIndex++);
		}
		return null;
	}

	public Player addNewRunner() {

		Control control = getNextControl();
		if (control != null) {
			Player player = new Player(control, getRandomName(), getRandomColor());
			gameLoop.addRunner(player);
			return player;
		}
		return null;
	}

	public Player addNewAttacker() {
		Player player = new Player(null, getRandomName(), getRandomColor());
		gameLoop.addRunner(player);
		hostRoomInterface.addAttacker(player);
		return player;
	}

}
