package entity;

import javafx.scene.canvas.GraphicsContext;
import logic.Sprite;

public class Attack extends Sprite {

	private double lifeTime = 3;
	private double fadeSpeed = 1;
	private boolean readyToClear = false;

	public boolean getReadyToClear() {
		return readyToClear;
	}

	public void render(GraphicsContext gc) {
		gc.save();
		gc.fillOval(positionX, positionY, width, height);
		gc.restore();
	}

	public void update(double time) {
		lifeTime -= (fadeSpeed * time);
		if (lifeTime <= 0) {
			readyToClear = true;
		}
	}
}
