package entity;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import resource.Resources;

public class Collectable extends Sprite {

	private static final double[][] FRAME_POSITION = new double[7][2];
	private static final double FRAME_WIDTH = 100;
	private static final double FRAME_HEIGHT = 170;
	public static final double DRAW_WIDTH = 20;
	public static double DRAW_HEIGHT;
	private static double animationLength = 7;
	private static double animationSpeed = 40;
	private static double frameSpeed = animationLength / animationSpeed;
	private double timeElapse = 0;
	private boolean isActive = true;

	public static void initMain() {

		FRAME_POSITION[0][0] = 0;
		FRAME_POSITION[0][1] = 0;
		FRAME_POSITION[1][0] = FRAME_WIDTH;
		FRAME_POSITION[1][1] = 0;
		FRAME_POSITION[2][0] = FRAME_WIDTH * 2;
		FRAME_POSITION[2][1] = 0;
		FRAME_POSITION[3][0] = FRAME_WIDTH * 3;
		FRAME_POSITION[3][1] = 0;
		FRAME_POSITION[4][0] = FRAME_WIDTH * 2;
		FRAME_POSITION[4][1] = 0;
		FRAME_POSITION[5][0] = FRAME_WIDTH;
		FRAME_POSITION[5][1] = 0;
		FRAME_POSITION[6][0] = 0;
		FRAME_POSITION[6][1] = 0;
		
		DRAW_HEIGHT = DRAW_WIDTH / FRAME_WIDTH * FRAME_HEIGHT;
	}

	public Collectable(double x, double y) {
		init(x,y);
	}

	public void init(double x, double y) {
		super.setScaleXY(1, 1);
		super.setWidth(DRAW_WIDTH);
		super.setHeight(DRAW_HEIGHT);
		super.setPositionX(x);
		super.setPositionY(y);
		super.setVelocityX(0);
		super.setRotationAngle(0);
		currentAnimationFrame = 0;
		super.setImage(Resources.getCollectable(0));
		calculateBoundary();
	}

	private void calculateBoundary() {
		double boundaryWidth = width;
		double boundaryHeight = height * 0.3;
		double boundaryX = positionX;
		double boundaryY = positionY + (height - boundaryHeight);
		super.collisionZone = new Rectangle(boundaryX, boundaryY, boundaryWidth, boundaryHeight);
	}

	public void render(GraphicsContext gc) {
		gc.save();
		gc.setGlobalBlendMode(BlendMode.SRC_OVER);
		gc.drawImage(image, FRAME_POSITION[currentAnimationFrame][0], FRAME_POSITION[currentAnimationFrame][1],
				FRAME_WIDTH, FRAME_HEIGHT, positionX, positionY, displayWidth, displayHeight);

		gc.restore();
	}

	public void update(double time) {
		timeElapse += time;
		if (timeElapse > frameSpeed) {
			timeElapse = timeElapse - frameSpeed;
			currentAnimationFrame++;
			if (currentAnimationFrame > animationLength - 1) {
				currentAnimationFrame = 0;
			}
		}
	}

	public void disable() {
		isActive = false;
	}

	public boolean isActive() {
		return isActive;
	}

}
