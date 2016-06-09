package logic;

import java.util.ArrayList;
import java.util.PriorityQueue;

import controls.Controller;
import entity.Ant;
import entity.Attack;
import entity.Character;
import entity.CharacterSwordMan;
import entity.Player;
import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.KeyCode;
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

	private Canvas mainCanvas;
	private Canvas backgroundCanvas;
	private GraphicsContext graphicContext;
	private Controller controller;
	private ArrayList<Character> character = new ArrayList<Character>();
	private PriorityQueue<Character> characterPq = new PriorityQueue<>();
	private ArrayList<Attack> attack = new ArrayList<Attack>();

	private Resources resources;
	private Shape boundary;

	public GameLoop(GameInterface gi, Controller controller, Resources resources) {
		this.resources = resources;

		this.controller = controller;
		this.mainCanvas = gi.getMainCanvas();
		this.backgroundCanvas = gi.getBackgroundCanvas();

		drawMap();
		graphicContext = mainCanvas.getGraphicsContext2D();

		double boundaryWidth = mainCanvas.getWidth() - 50;
		double boundaryHeight = mainCanvas.getHeight() - 100;
		double boundaryX = (mainCanvas.getWidth() - boundaryWidth) / 2;
		double boundaryY = (mainCanvas.getHeight() - boundaryHeight) / 2;
		boundary = new Rectangle(boundaryX, boundaryY, boundaryWidth, boundaryHeight);
	}

	public void initGameLoop() {
		for (int i = 0; i < runners.size(); i++) {
			Character c = runners.get(i).createSprite();
			c.setGameBoundary(boundary);
			character.add(c);
		}
		startNanoTime = System.nanoTime();
	}

	@Override
	public void handle(long currentNanoTime) {
		double elapsedTime = (currentNanoTime - startNanoTime) / 1000000000.0;
		startNanoTime = currentNanoTime;

		process();
		update(elapsedTime);
		draw();
	}

	private void process() {

		// set all ants velocity to 0
		for (int k = 0; k < character.size(); k++) {
			character.get(k).setVelocityX(0);
			character.get(k).setVelocityY(0);
			character.get(k).setVelocityRotate(0);
		}

		// process user controls
		ArrayList<KeyCode> temp = controller.getKeyCodes();
		for (int i = 0; i < temp.size(); i++) {
			KeyCode code = temp.get(i);
			if (code.compareTo(KeyCode.DIGIT1) == 0) {
				createAttack(100, 100);
			}
			for (int k = 0; k < character.size(); k++) {
				character.get(k).update(code);
			}
		}
	}

	private void update(double elapsedTime) {

		updateFrameRate(elapsedTime);

		for (int k = 0; k < character.size(); k++) {
			character.get(k).update(elapsedTime);
			characterPq.offer(character.get(k));
		}

		ArrayList<Integer> lsitToRemove = new ArrayList<Integer>();
		for (int k = 0; k < attack.size(); k++) {
			if (attack.get(k).getReadyToClear()) {
				lsitToRemove.add(k);
			} else {
				attack.get(k).update(elapsedTime);
			}
		}

		// remove attacks from list
		for (int i = lsitToRemove.size() - 1; i >= 0; i--) {
			attack.remove(i);
		}

		// collision check
		for (int k = 0; k < attack.size(); k++) {
			if (attack.get(k).isReadyForCollide()) {
				for (int kk = 0; kk < character.size(); kk++) {
					if (character.get(kk).isAlive()) {
						boolean hasCollided = attack.get(k).intersects(character.get(kk));
						if (hasCollided) {
							character.get(kk).setDead();
						}
					}
				}
			}
		}
	}

	private void draw() {
		graphicContext.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());

		//drawBoundaryFrame(); // debug

		for (int i = 0; i < attack.size(); i++) {
			attack.get(i).render(graphicContext);
		}

		while (!characterPq.isEmpty()) {
			characterPq.poll().render(graphicContext);
		}

		drawFrameRate();
	}

	private void drawMap() {
		GraphicsContext bgc = backgroundCanvas.getGraphicsContext2D();
		// bgc.drawImage(resources.getGameMap(0), 0, 0, 512, 512, 0, 0, 512,
		// 512);
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
		graphicContext.setFill(null);
		Bounds b = boundary.getLayoutBounds();
		graphicContext.fillRect(b.getMinX(), b.getMinY(), b.getWidth(), b.getHeight());
		graphicContext.restore();
	}

	private void drawFrameRate() {
		graphicContext.fillText(String.valueOf(frameRate) + "fps", mainCanvas.getWidth() - 50,
				mainCanvas.getHeight() - 50);
	}

	public void createAttack(double x, double y) {
		Attack att = new Attack(x, y);
		attack.add(att);
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
