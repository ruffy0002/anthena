package entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import resource.Resources;

public class Attack extends Player {

	private double timePast;
	private double fadeTime = 3;
	private double fadeDelay = 2;
	private double currentFadeTime = 3;
	private double fadeSpeed = 3;
	private double fadeOpacity = 1;

	private boolean isReadyForCollide = false;
	private boolean readyToClear = false;

	public Attack(double x, double y) {

		timePast = 0;
		super.setImage(Resources.getAttackSet(0), null);

		animationFrameWidth = 400;
		animationFrameHeight = 400;

		currentAnimationFrame = 0;
		currentAnimationFrameX = 0;
		currentAnimationFrameY = 0;

		animationLength = 12;
		animationSpeed = 1500;

		setPositionX(x);
		setPositionY(y);

		setWidth(100);
		setHeight(100);

		setBoundaryX(getPositionX() - getHalfWidth());
		setBoundaryY(getPositionY() - getHalfHeight());

		setRotationAngle(0);
		setVelocityRotate(10);

	}

	public boolean getReadyToClear() {
		return readyToClear;
	}

	public void render(GraphicsContext gc) {
		gc.save();
		shear(gc);
		rotate(gc, rotationAngle, getBoundaryX() + halfWidth, getBoundaryY() + halfHeight);
		gc.setGlobalAlpha(fadeOpacity);
		gc.setGlobalBlendMode(BlendMode.DARKEN);
		gc.drawImage(image, animationFrameWidth * currentAnimationFrameX, animationFrameHeight * currentAnimationFrameY,
				animationFrameWidth, animationFrameHeight, getBoundaryX(), getBoundaryY(), width, height);
		gc.setGlobalAlpha(1);
		gc.restore();
	}

	public void update(double time) {
		if (!isReadyForCollide) {
			timePast += time;
			currentAnimationFrame = (int) ((timePast * 1000) / animationSpeed * animationLength);
			int rotationMultiplier = (animationLength - 1) - currentAnimationFrame;
			spiltFrameToXandY(4);
			setRotationAngle(getRotationAngle() + (rotationMultiplier * getVelocityRotate()) * time);
			if (currentAnimationFrame == animationLength - 1) {
				isReadyForCollide = true;
			}
		} else {
			// Ready for collision detection
			setRotationAngle(getRotationAngle() + getVelocityRotate() * time);
			if (fadeOpacity <= 0) {
				readyToClear = true;
			} else {
				if (fadeDelay > 0) {
					fadeDelay -= (fadeSpeed * time);
				} else {
					currentFadeTime -= (fadeSpeed * time);
					fadeOpacity = currentFadeTime / fadeTime;
				}
			}
		}
	}

	public boolean isReadyForCollide() {
		return isReadyForCollide;
	}
}
