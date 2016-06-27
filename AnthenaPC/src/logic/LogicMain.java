package logic;


import connection.PlayerThread;
import connection.SocketInterface;
import connection.hostRoomThread;
import controls.Control;
import controls.Controller;
import entity.Player;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import resource.Resources;
import userinterface.GameInterface;
import userinterface.GameRoomInterface;
import userinterface.ScreenInformation;

public class LogicMain {

	private GameLoop gameLoop;
	private Resources resources;
	private GameRoomInterface hostRoomInterface;
	private hostRoomThread masterRoomThread;

	public static final double GAME_WIDTH = 800;
	public static final double GAME_HEIGHT = 600;

	private int controlsIndex = 0;

	public LogicMain(Resources resources) {
		this.resources = resources;
	}

	public void initGameLoop(GameInterface gi, Controller controller, Resources resources) {
		gameLoop = new GameLoop(gi, controller, resources);
	}

	public void startGameLoop() {
		gameLoop.initGameLoop();
		gameLoop.start();
	}

	public void hostGame() {
		masterRoomThread = SocketInterface.broadcastRoom(this);
	}

	public void addAttack(float x, float y, Player player) {
		gameLoop.createAttack(x, y, player);
	}

	public Image getDefautCharacterProfile() {
		return Resources.CHARACTER_ATTACK_PROFILE[0];
	}

	public String getNextName() {
		return Resources.getNextName();
	}

	public Color getNextColor() {
		return Resources.getNextColor();
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
			Player player = new Player(control, getNextName(), getNextColor(), null,this);
			gameLoop.addRunner(player);
			return player;
		}
		return null;
	}

	public Player addNewAttacker(PlayerThread pt) {
		Player player = new Player(null, getNextName(), null, pt, this);
		gameLoop.addAttackers(player);
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
