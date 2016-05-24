package entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import logic.Sprite;

public class Attack extends Player {

	private double lifeTime = 1000;
	private double fadeSpeed = 1;
	private boolean readyToClear = false;

	public boolean getReadyToClear() {
		return readyToClear;
	}

	public void render(GraphicsContext gc) {
		gc.save();
		gc.setGlobalBlendMode(BlendMode.SCREEN);
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
