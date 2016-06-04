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
import userinterface.GameInterface;
import userinterface.GameRoomInterface;
import userinterface.ScreenInformation;

public class LogicMain {

	private GameLoop gameLoop;
	private boolean[] nameAssigned;
	private Resources resources;
	private GameRoomInterface hostRoomInterface;

	public static final double GAME_WIDTH = 800;
	public static final double GAME_HEIGHT = 600;

	private int controlsIndex = 0;

	public LogicMain(Resources resources) {
		this.resources = resources;

		nameAssigned = new boolean[resources.getNames().length];
		for (int i = 0; i < resources.getNames().length; i++) {
			nameAssigned[i] = false;
		}
	}

	public void initGameLoop(GameInterface gi, Controller controller, Resources resources) {
		gameLoop = new GameLoop(gi, controller, resources);
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
		//gameLoop.addRunner(player);
		hostRoomInterface.addAttacker(player);
		return player;
	}

	public void setHostRoomInterface(GameRoomInterface hostRoomInterface) {
		this.hostRoomInterface = hostRoomInterface;
	}

	public void updateScale(ScreenInformation screenInformation) {
		double xScale = screenInformation.get_width() / GAME_WIDTH;
		double yScale = screenInformation.get_height() / GAME_HEIGHT;
		gameLoop.updateScale(xScale, yScale);
	}

}
