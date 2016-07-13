package entity;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.shape.Ellipse;
import resource.Resources;

public class Trap extends Sprite {
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

	public static void initMain() {
		visibleHeight = Math.cos(Math.toDegrees(60)) * HEIGHT;
	}

	public Trap(double x, double y, Player player) {
		this.player = player;
		timePast = 0;

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
		// collisionZone = new Rectangle(boundaryX, boundaryY, boundaryWidth,
		// boundaryHeight);
		Ellipse es = new Ellipse();
		es.setCenterX(boundaryX + width / 2);
		es.setCenterY(boundaryY + boundaryHeight / 2);
		es.setRadiusX(width / 2);
		es.setRadiusY(boundaryHeight / 2);
		collisionZone = es;
	}

	public void render(GraphicsContext gc) {
		/*
		 * gc.save(); rotateX(gc, perceptionRotate); rotate(gc, rotationAngle,
		 * getBoundaryX() + halfWidth, getBoundaryY() + halfHeight);
		 * gc.setGlobalAlpha(fadeOpacity);
		 * 
		 * gc.setGlobalBlendMode(BlendMode.SRC_OVER);
		 * //gc.setEffect(paintEffect); gc.drawImage(movingStateFrames,
		 * animationFrameWidth * currentAnimationFrameX, animationFrameHeight *
		 * currentAnimationFrameY, animationFrameWidth, animationFrameHeight,
		 * getBoundaryX(), getBoundaryY(), width, height); gc.setGlobalAlpha(1);
		 * gc.restore();
		 */

		// draw collison
		drawCollision(gc);
	}

	public void drawCollision(GraphicsContext gc) {
		gc.save();
		gc.setGlobalBlendMode(BlendMode.LIGHTEN);
		Bounds b = collisionZone.getLayoutBounds();
		gc.fillOval(b.getMinX(), b.getMinY(), b.getWidth(), b.getHeight());
		gc.restore();
	}

	public void update(double time) {

	}

	public boolean belongsToMe(Player player) {
		if (this.player == null || player == null) {
			return false;
		}
		
		if (this.player == player) {
			return true;
		}
		
		return false;
	}
	
	public Player getPlayer(){
		return player;
	}
}
