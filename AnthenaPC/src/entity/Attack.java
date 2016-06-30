package entity;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import resource.Resources;

public class Attack extends Sprite {

	private Player player;

	public static final double HEIGHT = 100;
	public static final double WIDTH = 100;
	public static double visibleHeight = 0;
	private static double perceptionRotate = 60;
	private double timePast;
	private double fadeTime = 3;
	private double fadeDelay = 2;
	private double currentFadeTime = 3;
	private double fadeSpeed = 3;
	private double fadeOpacity = 1;

	private boolean isReadyForCollide = false;
	private boolean readyToClear = false;
	private ColorAdjust paintEffect;

	public static void initMain() {
		visibleHeight = Math.cos(Math.toDegrees(60)) * HEIGHT;
	}

	public Attack(double x, double y, Player player) {
		this.player = player;
		if (player != null) {
			paintEffect = player.getPaintEffect();
		} else {
			paintEffect = Resources.getNextColorAdjust();
		}
		timePast = 0;
		super.setMovingFrames(Resources.getAttackSet(0));

		perceptionRotate = 60;

		animationFrameWidth = 400;
		animationFrameHeight = 400;

		currentAnimationFrame = 0;
		currentAnimationFrameX = 0;
		currentAnimationFrameY = 0;

		animationLength = 12;
		animationSpeed = 1500;

		setPositionX(x);
		setPositionY(y);

		setWidth(WIDTH);
		setHeight(HEIGHT);

		setBoundaryX(getPositionX() - getHalfWidth());
		setBoundaryY(getPositionY() - getHalfHeight());

		setRotationAngle(0);
		setVelocityRotate(10);
		calculateBoundary();
	}

	public void calculateBoundary() {
		double boundaryWidth = width;
		double boundaryHeight = Math.sin(Math.toRadians(90 - perceptionRotate)) * (height);
		double boundaryX = getBoundaryX();
		double boundaryY = getBoundaryY() + (height - boundaryHeight) / 2;
		collisionZone = new Rectangle(boundaryX, boundaryY, boundaryWidth, boundaryHeight);
	}

	public boolean getReadyToClear() {
		return readyToClear;
	}

	public void render(GraphicsContext gc) {
		gc.save();
		rotateX(gc, perceptionRotate);
		rotate(gc, rotationAngle, getBoundaryX() + halfWidth, getBoundaryY() + halfHeight);
		gc.setGlobalAlpha(fadeOpacity);

		gc.setGlobalBlendMode(BlendMode.SRC_OVER);
		gc.setEffect(paintEffect);
		gc.drawImage(movingStateFrames, animationFrameWidth * currentAnimationFrameX, animationFrameHeight * currentAnimationFrameY,
				animationFrameWidth, animationFrameHeight, getBoundaryX(), getBoundaryY(), width, height);
		gc.setGlobalAlpha(1);
		gc.restore();

		// draw collison
		// drawCollision(gc);
	}

	public void drawCollision(GraphicsContext gc) {
		gc.save();
		gc.setGlobalBlendMode(BlendMode.LIGHTEN);
		Bounds b = collisionZone.getLayoutBounds();
		gc.fillRect(b.getMinX(), b.getMinY(), b.getWidth(), b.getHeight());
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

	public void addScore(int score) {
		if (player != null) {
			player.addScore(score);
		}
	}
}
