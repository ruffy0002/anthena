package entity;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Pair;
import resource.Resources;

public class CharacterSwordMan extends Character {

	private static final double[][] FRAME_POSITION = new double[14][2];
	private static double animationLength = 14;
	private static double animationSpeed = 5;

	public static void initMain() {

		double fWidth = 300;
		double fHeight = 537;

		FRAME_POSITION[0][0] = 0;
		FRAME_POSITION[0][1] = 0;
		FRAME_POSITION[1][0] = fWidth;
		FRAME_POSITION[1][1] = 0;
		FRAME_POSITION[2][0] = fWidth * 2;
		FRAME_POSITION[2][1] = 0;
		FRAME_POSITION[3][0] = fWidth * 3;
		FRAME_POSITION[3][1] = 0;

		FRAME_POSITION[4][0] = 0;
		FRAME_POSITION[4][1] = fHeight;
		FRAME_POSITION[5][0] = fWidth;
		FRAME_POSITION[5][1] = fHeight;
		FRAME_POSITION[6][0] = fWidth * 2;
		FRAME_POSITION[6][1] = fHeight;
		FRAME_POSITION[7][0] = fWidth * 3;
		FRAME_POSITION[7][1] = fHeight;

		FRAME_POSITION[8][0] = fWidth * 2;
		FRAME_POSITION[8][1] = fHeight;
		FRAME_POSITION[9][0] = fWidth;
		FRAME_POSITION[9][1] = fHeight;
		FRAME_POSITION[10][0] = 0;
		FRAME_POSITION[10][1] = fHeight;
		FRAME_POSITION[11][0] = fWidth * 3;
		FRAME_POSITION[11][1] = 0;
		FRAME_POSITION[12][0] = fWidth * 2;
		FRAME_POSITION[12][1] = 0;
		FRAME_POSITION[13][0] = fWidth * 1;
		FRAME_POSITION[13][1] = 0;

	}

	public CharacterSwordMan(Player p) {
		super(p);
		init();
	}

	public void init() {
		super.init();

		animationFrameWidth = 300;
		animationFrameHeight = 537;

		deathAnimationLength = 2;
		deathAnimationSpeed = 0.5;
		deathAnimationFrameSpeed = deathAnimationSpeed / deathAnimationLength;
		deathAnimationFrameWidth = 250;
		deathAnimationFrameHeight = 270;
		currentDeathAnimationFrame = 0;
		deathAnimationRepeat = -1;

		defeatedAnimationLength = 2;
		defeatedAnimationSpeed = 0.5;
		defeatedAnimationFrameSpeed = defeatedAnimationSpeed / defeatedAnimationLength;
		defeatedAnimationFrameWidth = 300;
		defeatedAnimationFrameHeight = 531;
		currentDefeatedAnimationFrame = 0;
		defeatAnimationRepeat = 1;

		super.setScaleXY(1, 1);
		super.setWidth(25);
		super.setHeight(getWidth() / animationFrameWidth * animationFrameHeight);
		super.setPositionX(400);
		super.setPositionY(200);
		super.setVelocityX(0);
		super.setRotationAngle(0);
		super.setImage(Resources.getcharacterSet(0));
		super.setDeathImage(Resources.getDeathImage(0));
		super.setDefeatedImage(Resources.getDefeatedImage(0));
		calculateBoundary();
	}

	public void calculateBoundary() {
		double boundaryWidth = width * 0.6;
		double boundaryHeight = height * 0.3;
		double boundaryX = positionX + (width - boundaryWidth) / 3;
		double boundaryY = positionY + (height - boundaryHeight);
		collisionZone = new Rectangle(boundaryX, boundaryY, boundaryWidth, boundaryHeight);
	}

	public void update(double time) {
		super.update(time);
		if (isAlive) {
			double totalMovementDistance = movementSpeed * time;

			double moveX = 0;
			double moveY = 0;

			if (velocityX != 0 && velocityY != 0) {
				double tempShort = Math.sqrt(Math.pow(totalMovementDistance, 2) / 2);
				moveX = tempShort * velocityX;
				moveY = tempShort * velocityY;
			}else {
				moveX = totalMovementDistance * velocityX;
				moveY = totalMovementDistance * velocityY;
			}

			if (moveX < 0) {
				isFlipped = true;
			} else if (moveX > 0) {
				isFlipped = false;
			}
			
			if (moveX == 0 && moveY == 0) {
				currentAnimationFrame = 0;
				movementDistance = 0;
			} else {
				movementDistance += totalMovementDistance;
				currentAnimationFrame = Math.abs((int) ((movementDistance / animationSpeed) % animationLength));
				Rectangle r = (Rectangle) collisionZone;
				previousBoundaryX = r.getX();
				previousBoundaryY = r.getY();
				r.setX(r.getX() + moveX);
				r.setY(r.getY() + moveY);

				Pair<Boolean, Boolean> xy = getCollidePair(mapBoundary);
				if (!xy.getKey()) {
					super.setPositionX(positionX + moveX);
				} else {
					r.setX(previousBoundaryX);
				}
				if (!xy.getValue()) {
					super.setPositionY(positionY + moveY);
				} else {
					r.setY(previousBoundaryY);
				}
			}
		} else {
			frameSpeedControl += time;
			if (!hasFinishDeathAnimation) {
				int tempFrame = (int) (frameSpeedControl / defeatedAnimationFrameSpeed);
				currentDefeatedAnimationFrame = tempFrame % defeatedAnimationLength;

				if (tempFrame / defeatedAnimationLength > defeatAnimationRepeat) {
					hasFinishDeathAnimation = true;
					frameSpeedControl = 0;
				}
			} else {
				currentDeathAnimationFrame = (int) (frameSpeedControl / deathAnimationFrameSpeed);
				currentDeathAnimationFrame = currentDeathAnimationFrame % deathAnimationLength;
			}

		}
	}

	public void render(GraphicsContext gc) {
		super.render(gc);
		gc.save();
		if (isAlive) {
			gc.setGlobalBlendMode(BlendMode.SRC_OVER);
			if (!isFlipped) {
				gc.drawImage(image, FRAME_POSITION[currentAnimationFrame][0], FRAME_POSITION[currentAnimationFrame][1],
						animationFrameWidth, animationFrameHeight, positionX, positionY, displayWidth, displayHeight);
			} else {
				Rotate r = new Rotate(180, positionX, positionY);
				r.setAxis(Rotate.Y_AXIS);
				gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
				gc.translate(-width, 0);
				gc.drawImage(image, FRAME_POSITION[currentAnimationFrame][0], FRAME_POSITION[currentAnimationFrame][1],
						animationFrameWidth, animationFrameHeight, positionX, positionY, width, height);
			}
		} else {
			if (!hasFinishDeathAnimation) {
				gc.drawImage(defeatedImage, defeatedAnimationFrameWidth * currentDefeatedAnimationFrame, 0,
						defeatedAnimationFrameWidth, defeatedAnimationFrameHeight, positionX, positionY, displayWidth,
						displayHeight);
			} else {
				gc.drawImage(deathImage, deathAnimationFrameWidth * currentDeathAnimationFrame, 0,
						deathAnimationFrameWidth, deathAnimationFrameHeight, positionX, positionY, displayWidth,
						displayHeight);
			}
		}
		gc.restore();
		
		// draw collison box
		/*gc.save();
		gc.setGlobalBlendMode(BlendMode.LIGHTEN);
		gc.setFill(player.getColor());
		Bounds b = collisionZone.getLayoutBounds();
		gc.fillRect(b.getMinX(), b.getMinY(), b.getWidth(), b.getHeight());
		gc.restore();*/
	}
}
