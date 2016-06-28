package logic;

import java.util.ArrayList;

import connection.PlayerThread;
import connection.SocketInterface;
import connection.hostRoomThread;
import controls.Control;
import controls.Controller;
import entity.Player;
import javafx.scene.effect.ColorAdjust;
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
	private ArrayList<Player> playerList = new ArrayList<Player>();

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
		for (int i = 0; i < Player.getAll_players_list().size(); i++) {
			if (Player.getAll_players_list().get(i).getPlayerType() == Player.TYPE_RUNNER) {
				gameLoop.addRunner(Player.getAll_players_list().get(i));
			} else if (Player.getAll_players_list().get(i).getPlayerType() == Player.TYPE_STOMPPER) {
				gameLoop.addAttackers(Player.getAll_players_list().get(i));
			}
		}
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
	
	public Image getDefautAttackProfile() {
		return Resources.CHARACTER_ATTACK_PROFILE[1];
	}

	public String getNextName() {
		return Resources.getNextName();
	}
	
	private ColorAdjust getNextColorAdjust() {
		return Resources.getNextColorAdjust();
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
			Player player = new Player(control, getNextName(), getNextColor(),getNextColorAdjust(), null, this,false);
			gameLoop.addRunner(player);
			return player;
		}
		return null;
	}

	public Player addNewPlayer(PlayerThread pt) {
		Player player = new Player(null, getNextName(), getNextColor(), getNextColorAdjust(), pt, this,true);
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

	public void updatePlayerType(int player_id, int type) {
		if (Player.getAll_players_list().get(player_id).getPlayerType() != type) {
			Player.getAll_players_list().get(player_id).setPlayerType(type);
			hostRoomInterface.addPlayer(Player.getAll_players_list().get(player_id));
		}
	}
	
	public void updatePlayerPosition(Player player, float x, float y){
		player.getCharacter().setPositionXFinal(x);
		player.getCharacter().setPositionYFinal(y);
	}

}
