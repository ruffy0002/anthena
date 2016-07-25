package logic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.main.anthenaandroid.GamePacket;

import connection.PlayerThread;
import connection.SocketInterface;
import connection.hostRoomThread;
import controls.Control;
import controls.Controller;
import entity.Player;
import javafx.application.Platform;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import resource.Resources;
import userinterface.GameInterface;
import userinterface.GameRoomInterface;
import userinterface.PrimaryInterface;
import userinterface.ScreenInformation;

public class LogicMain {

	private PrimaryInterface primaryUserInterface;
	private GameLoop gameLoop;
	private Resources resources;
	private GameInterface gameInterface;
	private Controller controller;
	private GameRoomInterface hostRoomInterface;
	private hostRoomThread masterRoomThread;
	private ArrayList<Player> playerList = new ArrayList<Player>();

	public static final double GAME_WIDTH = 800;
	public static final double GAME_HEIGHT = 600;

	private int controlsIndex = 0;

	public LogicMain(Resources resources, PrimaryInterface primaryUserInterface) {
		this.primaryUserInterface = primaryUserInterface;
		this.resources = resources;
	}

	public void init(GameInterface gi, Controller controller) {
		this.gameInterface = gi;
		this.controller = controller;
	}

	public void initGameLoop() {
		gameLoop = null;
		gameLoop = new GameLoop(gameInterface, controller, resources, this);
	}

	public void startGameLoop() {
		Queue<Player> tempQ = new LinkedList<Player>();
		for (int i = 0; i < Player.getAll_players_list().size(); i++) {
			if (Player.getAll_players_list().get(i).getPlayerType() == Player.TYPE_RUNNER) {
				gameLoop.addRunner(Player.getAll_players_list().get(i));
			} else if (Player.getAll_players_list().get(i).getPlayerType() == Player.TYPE_STOMPPER) {
				gameLoop.addAttackers(Player.getAll_players_list().get(i));
			}
			if (Player.getAll_players_list().get(i).isMobile) {
				tempQ.offer(Player.getAll_players_list().get(i));
			}
		}
		gameLoop.initGameLoop();
		masterRoomThread.sendStartGame();
		waitCheckForPlayers(tempQ);
	}

	private void waitCheckForPlayers(Queue<Player> tempQ) {
		Thread thread = new Thread() {
			public void run() {
				Queue<Player> workingQ = tempQ;
				Queue<Player> tempQ2 = new LinkedList<Player>();

				while (workingQ.size() > 0) {
					Player p = workingQ.poll();
					if (p.getStatus() != Player.READY_TO_GO) {
						tempQ2.offer(p);
						try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
						}
					}
					if (workingQ.size() <= 0) {
						if (tempQ2.size() > 0) {
							masterRoomThread.sendStartGame();
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
							}
							workingQ = tempQ2;
							tempQ2 = new LinkedList<Player>();
						}
					}
				}
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						gameInterface.getWaitInformationPane().setOpacity(0);
					}
				});
				gameLoop.start();
			}
		};
		thread.start();
	}

	public void hostGame() {
		masterRoomThread = SocketInterface.broadcastRoom(this);
	}

	public void executeAttack(Player player, float x, float y) {
		gameLoop.createAttack(x, y, player);
	}

	public void addTrapToField(Player player, double d, double e) {
		gameLoop.addTrapToField(d, e, player);
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
			Player player = new Player(control, getNextName(), getNextColor(), getNextColorAdjust(), null, this, false);
			return player;
		}
		return null;
	}

	public Player addNewPlayer(PlayerThread pt) {
		Player player = new Player(null, getNextName(), getNextColor(), getNextColorAdjust(), pt, this, true);
		return player;
	}

	public void setGameRoomInterface(GameRoomInterface hostRoomInterface) {
		this.hostRoomInterface = hostRoomInterface;
	}

	public void updateScale(ScreenInformation screenInformation) {
		double xScale = screenInformation.get_width() / GAME_WIDTH;
		double yScale = screenInformation.get_height() / GAME_HEIGHT;
		gameLoop.updateScale(xScale, yScale);
	}

	public void updatePlayerType(int player_id, int type) {
		Player.getAll_players_list().get(player_id).setPlayerType(type);
		hostRoomInterface.addPlayer(Player.getAll_players_list().get(player_id));
	}

	public void updatePlayerPosition(Player player, float x, float y) {
		double xPos = 800 * x;
		double yPos = 600 * y;
		player.getCharacter().setPositionXFinal(xPos);
		player.getCharacter().setPositionYFinal(yPos);
	}

	public void updatePlayerStatus(Player player) {
		hostRoomInterface.updatePlayerStatus(player);
	}

	public boolean isAllPlayerReady() {
		
		if(Player.getAll_players_list().size() == 0){
			return false;
		}
		
		for (int i = 0; i < Player.getAll_players_list().size(); i++) {
			if (Player.getAll_players_list().get(i).getStatus() == Player.NOT_READY) {
				return false;
			}
		}
		return true;
	}

	public void setAllPlayersUnready() {
		for (int i = 0; i < Player.getAll_players_list().size(); i++) {
			Player.getAll_players_list().get(i).setStatus(Player.NOT_READY);
		}
	}

	public void showGamePauseMenu() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				gameInterface.getGamePausePane().setOpacity(1);
			}
		});
	}

	public void hideGamePauseMenu() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				gameInterface.getGamePausePane().setOpacity(0);
			}
		});
	}

	public void endGame() {
		gameLoop.stop();
		gameLoop = null;
		gameInterface = null;
		reviewPlayerStatus();
		setAllPlayersUnready();
		hostRoomInterface.cleanUp();
		rebuildLobbyUsers();
		primaryUserInterface.goToLobby();
	}

	private void rebuildLobbyUsers() {
		for (int i = 0; i < Player.getAll_players_list().size(); i++) {
			hostRoomInterface.addPlayer(Player.getAll_players_list().get(i));
		}
	}

	private void reviewPlayerStatus() {
		for (int i = 0; i < Player.getAll_players_list().size(); i++) {
			if (!Player.getAll_players_list().get(i).isConnected()) {
				Player p = Player.getAll_players_list().get(i);
				Player.getAll_players_list().remove(i--);
				p = null;
			}
		}
	}

}
