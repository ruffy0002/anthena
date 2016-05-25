package entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;

public class Attack extends Player {

	private double lifeTime = 3;
	private double fadeSpeed = 1;
	private double currentDelay = 3;
	private double delay = 3;
	private double scale = 0;
	private boolean isReadyForCollide = false;

	private boolean readyToClear = false;

	public boolean getReadyToClear() {
		return readyToClear;
	}

	public void render(GraphicsContext gc) {
		gc.save();
		gc.setGlobalAlpha(scale);
		gc.setGlobalBlendMode(BlendMode.SCREEN);
		if (scale < 1) {
			gc.setFill(Color.BLACK);
			double cWidth = width * scale;
			double cHeight = height * scale;
			gc.fillOval(positionX - (cWidth / 2), positionY - (cHeight / 2), cWidth, cHeight);
		} else {
			gc.setFill(Color.RED);
			gc.fillOval(positionX - (width / 2), positionY - (height / 2), width, height);
		}
		gc.setGlobalAlpha(1);
		gc.restore();
	}

	public void update(double time) {
		if (!isReadyForCollide) {
			currentDelay -= (delay * time);
			scale = (delay - currentDelay) / delay;
			if (currentDelay <= 0) {
				isReadyForCollide = true;
			}
		} else {
			lifeTime -= (fadeSpeed * time);
			if (lifeTime <= 0) {
				readyToClear = true;
			}
		}
	}

	public boolean isReadyForCollide() {
		return isReadyForCollide;
	}
}
