package logic;

import java.util.ArrayList;
import java.util.PriorityQueue;

import controls.Controller;
import entity.Ant;
import entity.Attack;
import entity.Character;
import entity.CharacterSwordMan;
import entity.Collectable;
import entity.Player;
import entity.Sprite;
import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import resource.Resources;
import userinterface.GameInterface;

public class GameLoop extends AnimationTimer {

	private ArrayList<Player> runners = new ArrayList<Player>();
	private ArrayList<Player> attackers = new ArrayList<Player>();

	private long startNanoTime;
	private int frameRate = 0;
	private int frameRateCounter = 0;
	private double deltaSum = 0;

	private double overlayRefreshRate = 2; // every 5 seconds;
	private double overlayElapsedTimeStore = 0;

	private GameInterface gameInterface;
	private Canvas mainCanvas;
	private Canvas backgroundCanvas;
	private HBox overlayGridPane;

	private GraphicsContext graphicContext;
	private Controller controller;
	private ArrayList<Character> character = new ArrayList<Character>();
	private ArrayList<Character> mobileCharacter = new ArrayList<Character>();
	private PriorityQueue<Sprite> spriteDrawPQ = new PriorityQueue<>();
	private ArrayList<Attack> attack = new ArrayList<Attack>();
	private boolean gamePause;
	private double gamePauseTimeStore = 0;
	private double gamePauseDelay = 0.3;

	private Resources resources;
	private LogicMain logic;
	private CollectableManager collectableManager;
	private AttackManager attackManager;
	private TrapManager trapManager;
	private Shape map_oundary;

	public GameLoop(GameInterface gi, Controller controller, Resources resources, LogicMain logic) {
		this.logic = logic;
		this.gameInterface = gi;
		this.resources = resources;
		this.controller = controller;
		this.mainCanvas = gi.getMainCanvas();
		this.backgroundCanvas = gi.getBackgroundCanvas();
		this.overlayGridPane = gi.getOverlayBox();

		gamePause = false;
		drawMap();
		graphicContext = mainCanvas.getGraphicsContext2D();

		double boundaryWidth = mainCanvas.getWidth();
		double boundaryHeight = mainCanvas.getHeight() - (mainCanvas.getHeight() * 0.23);
		double boundaryX = (mainCanvas.getWidth() - boundaryWidth) / 2;
		double boundaryY = (mainCanvas.getHeight() - boundaryHeight);
		map_oundary = new Rectangle(boundaryX, boundaryY, boundaryWidth, boundaryHeight);

		collectableManager = new CollectableManager(map_oundary);
		attackManager = new AttackManager(map_oundary);
		trapManager = new TrapManager(map_oundary);
	}

	public void initGameLoop() {
		rebuildOverlay();
		for (int i = 0; i < runners.size(); i++) {
			Character c = runners.get(i).createSprite();
			c.setGameBoundary(map_oundary);
			character.add(c);
			if (runners.get(i).getControl() == null) {
				mobileCharacter.add(c);
			}
		}
		startNanoTime = System.nanoTime();
	}

	@Override
	public void handle(long currentNanoTime) {

		double elapsedTime = (currentNanoTime - startNanoTime) / 1000000000.0;
		startNanoTime = currentNanoTime;

		process(elapsedTime);
		if (gamePause) {

		} else {
			updateOverlay(elapsedTime);
			update(elapsedTime);
			draw();
		}
	}

	private void updateOverlay(double elapsedTime) {
		overlayElapsedTimeStore += elapsedTime;
		if (overlayElapsedTimeStore > overlayRefreshRate) {
			checkConnectivity();
			overlayElapsedTimeStore = 0;
		}
	}

	private void process(double elapsedTime) {

		if (gamePauseTimeStore > 0) {
			gamePauseTimeStore -= elapsedTime;
		}
		// set all ants velocity to 0
		for (int k = 0; k < character.size(); k++) {
			character.get(k).setVelocityX(0);
			character.get(k).setVelocityY(0);
			character.get(k).setVelocityRotate(0);
		}

		// process user controls for keyboard
		ArrayList<KeyCode> temp = controller.getKeyCodes();
		for (int i = 0; i < temp.size(); i++) {
			KeyCode code = temp.get(i);

			if (code.compareTo(KeyCode.ESCAPE) == 0) {

				if (gamePauseTimeStore <= 0) {
					if (gamePause) {
						gamePause = false;
						logic.hideGamePauseMenu();
					} else {
						gamePause = true;
						logic.showGamePauseMenu();
					}
					gamePauseTimeStore = gamePauseDelay;
				}
			}

			if (gamePause) {
				if (code.compareTo(KeyCode.UP) == 0 || code.compareTo(KeyCode.DOWN) == 0
						|| code.compareTo(KeyCode.ENTER) == 0) {
					gamePauseTimeStore = gamePauseDelay;
					if (code.compareTo(KeyCode.ENTER) == 0) {
						logic.endGame();
					}
				}

			}

			if (!gamePause) {
				if (code.compareTo(KeyCode.DIGIT1) == 0) {
					createAttack(0.5f, 0.5f, null);
				}

				if (code.compareTo(KeyCode.DIGIT2) == 0) {
					addTrapToField(0.5f, 0.5f, null);
				}
				for (int k = 0; k < character.size(); k++) {
					if (character.get(k).getControl() != null) {
						character.get(k).update(code);
					}
				}
			}
		}

	}

	private void update(double elapsedTime) {

		updateFrameRate(elapsedTime);
		collectableManager.update(elapsedTime);
		attackManager.update(elapsedTime);
		trapManager.update(elapsedTime);

		for (int k = 0; k < character.size(); k++) {
			character.get(k).update(elapsedTime);
			spriteDrawPQ.offer(character.get(k));
		}

		// collision check
		for (int k = 0; k < attackManager.getAttacks().size(); k++) {
			if (attackManager.getAttacks().get(k).isReadyForCollide()) {
				for (int kk = 0; kk < character.size(); kk++) {
					if (character.get(kk).isAlive()) {
						if (!character.get(kk).isImmune()) {
							boolean hasCollided = attackManager.getAttacks().get(k).intersects(character.get(kk));
							if (hasCollided) {
								character.get(kk).takeDamage();
								if (!character.get(kk).isAlive()) {
									character.get(kk).getPlayer().changeToStomper();
									gameInterface.convertRunnerToStomper(character.get(kk).getPlayer());
									attackManager.getAttacks().get(k).addScore(100);
								}
							}
						}
					}
				}
			}
		}

		// check collison for trap
		for (int k = 0; k < trapManager.getTraps().size(); k++) {
			for (int kk = 0; kk < character.size(); kk++) {
				if (!trapManager.getTraps().get(k).belongsToMe(character.get(kk).getPlayer())) {
					System.out.println("collided on " + trapManager.getTraps().get(k).getPlayer().getName() + " trap");
				}
			}
		}

		// check colliosn for collectable if not add to draw q
		for (int k = 0; k < collectableManager.getCollectable().size(); k++) {
			for (int kk = 0; kk < character.size(); kk++) {
				if (character.get(kk).isAlive()) {
					boolean hasCollided = collectableManager.getCollectable().get(k).intersects(character.get(kk));
					if (hasCollided) {
						character.get(kk).increaseSpeed();
						collectableManager.getCollectable().get(k).disable();
						break;
					}
				}
			}
			if (collectableManager.getCollectable().get(k).isActive()) {
				spriteDrawPQ.offer(collectableManager.getCollectable().get(k));
			}
		}
	}

	private void draw() {
		graphicContext.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());

		// drawBoundaryFrame(); // debug

		attackManager.draw(graphicContext);
		trapManager.draw(graphicContext);

		while (!spriteDrawPQ.isEmpty()) {
			spriteDrawPQ.poll().render(graphicContext);
		}

		drawFrameRate();
	}

	private void drawMap() {
		GraphicsContext bgc = backgroundCanvas.getGraphicsContext2D();
		bgc.drawImage(resources.getGameMap(0), 0, 0, resources.getGameMap(0).getWidth(),
				resources.getGameMap(0).getHeight(), 0, 0, backgroundCanvas.getWidth(), backgroundCanvas.getHeight());
	}

	private void rebuildOverlay() {
		for (int i = 0; i < Player.getAll_players_list().size(); i++) {
			gameInterface.createFace(Player.getAll_players_list().get(i));
		}
	}

	private void checkConnectivity() {
		for (int i = 0; i < Player.getAll_players_list().size(); i++) {
			if (Player.getAll_players_list().get(i).isConnected()) {
				Player.getAll_players_list().get(i).setPanelConnected();
			} else {
				Player.getAll_players_list().get(i).setPanelDC();
			}
		}
	}

	private void updateFrameRate(double elapsedTime) {
		frameRateCounter++;
		deltaSum += elapsedTime;
		if (deltaSum > 1) {
			deltaSum = 0;
			frameRate = frameRateCounter;
			frameRateCounter = 0;
		}
	}

	private void drawBoundaryFrame() {
		graphicContext.save();
		graphicContext.setGlobalBlendMode(BlendMode.LIGHTEN);
		graphicContext.setGlobalAlpha(0.3);
		graphicContext.setFill(null);
		Bounds b = map_oundary.getLayoutBounds();
		graphicContext.fillRect(b.getMinX(), b.getMinY(), b.getWidth(), b.getHeight());
		graphicContext.restore();
	}

	private void drawFrameRate() {
		graphicContext.fillText(String.valueOf(frameRate) + "fps", mainCanvas.getWidth() - 50,
				mainCanvas.getHeight() - 50);
	}

	public void createAttack(float x, float y, Player player) {
		attackManager.createAttack(x, y, player);
	}

	public void addTrapToField(double d, double e, Player player) {
		trapManager.createTrap(d, e, player);
	}

	public void addRunner(Player p) {
		runners.add(p);
	}

	public void addAttackers(Player p) {
		attackers.add(p);
	}

	public void updateScale(double x, double y) {
		for (int k = 0; k < character.size(); k++) {
			character.get(k).updateScale(x, y);
		}
	}
}
