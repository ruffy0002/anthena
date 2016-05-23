package logic;

import java.util.ArrayList;

import controls.Controller;
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

	public GameLoop(Canvas canvas, Controller controller) {

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

		for (int k = 0; k < ants.size(); k++) {
			for (int kk = 0; kk < ants.size(); kk++) {
				if (k != kk)
					ants.get(k).intersects(ants.get(kk));
			}
		}
	}

	private void draw() {
		graphicContext.clearRect(0, 0, 512, 512);
		drawFrameRate();
		for (int i = 0; i < ants.size(); i++) {
			ants.get(i).render(graphicContext);
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

}
