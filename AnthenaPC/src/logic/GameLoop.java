package logic;

import java.util.ArrayList;

import controls.Controller;
import entity.Attack;
import entity.Player;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public class GameLoop extends AnimationTimer {

	private long startNanoTime;
	private int frameRate = 0;
	private int frameRateCounter = 0;
	private double deltaSum = 0;

	private Canvas mainCanvas;
	private GraphicsContext graphicContext;
	private Controller controller;
	private ArrayList<Player> ants = new ArrayList<Player>();
	private ArrayList<Attack> attack = new ArrayList<Attack>();

	public GameLoop(Canvas canvas, Controller controller) {

		new LogicMain(this);

		this.controller = controller;
		mainCanvas = canvas;
		graphicContext = mainCanvas.getGraphicsContext2D();

		initGameComponents();
		initGameLoop();
	}

	public void initGameComponents() {
		Player p = new Player();
		p.initHardCodeAnt();
		ants.add(p);

		Player temp = new Player();
		temp.initRandomAnt();
		ants.add(temp);
	}

	public void initGameLoop() {
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
		for (int k = 0; k < ants.size(); k++) {
			ants.get(k).setVelocityX(0);
			ants.get(k).setVelocityY(0);
			ants.get(k).setVelocityRotate(0);
		}

		// process user controls
		ArrayList<KeyCode> temp = controller.getKeyCodes();
		for (int i = 0; i < temp.size(); i++) {
			KeyCode code = temp.get(i);
			if (code.compareTo(KeyCode.G) == 0) {
				createAttack();
			}
			for (int k = 0; k < ants.size(); k++) {
				ants.get(k).update(code);
			}
		}
	}

	private void update(double elapsedTime) {
		updateFrameRate(elapsedTime);
		for (int k = 0; k < ants.size(); k++) {
			ants.get(k).update(elapsedTime);
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
			for (int kk = 0; kk < ants.size(); kk++) {
				boolean hasCollided = attack.get(k).intersects(ants.get(kk));
				if (hasCollided) {
					ants.get(kk).setDead();
				}
			}
		}
	}

	private void draw() {
		graphicContext.clearRect(0, 0, 512, 512);
		drawFrameRate();
		for (int i = 0; i < ants.size(); i++) {
			ants.get(i).render(graphicContext);
		}

		for (int i = 0; i < attack.size(); i++) {
			attack.get(i).render(graphicContext);
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

	private void drawFrameRate() {
		graphicContext.fillText(String.valueOf(frameRate) + "fps", 460, 490);
	}

	private void createAttack() {
		Attack att = new Attack();
		att.setPositionX(50);
		att.setPositionY(90);
		att.setWidth(30);
		att.setHeight(30);

		attack.add(att);
	}
}
