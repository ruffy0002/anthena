package entity;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Pair;
import resource.Resources;
import resource.Resources.CharacterType;

public class CharacterSwordMan extends Character {

	private static final double[][] FRAME_POSITION_MOVING = new double[14][2];
	private static final double[][] FRAME_POSITION_IDLE = new double[1][2];

	private static double animationLengthMoving = 14;
	private static double animationSpeedMoving = 5;

	public static void initMain() {

		double fWidth = 300;
		double fHeight = 537;

		// idle state
		FRAME_POSITION_IDLE[0][0] = 0;
		FRAME_POSITION_IDLE[0][1] = 0;

		// moving state
		FRAME_POSITION_MOVING[0][0] = 0;
		FRAME_POSITION_MOVING[0][1] = 0;
		FRAME_POSITION_MOVING[1][0] = fWidth;
		FRAME_POSITION_MOVING[1][1] = 0;
		FRAME_POSITION_MOVING[2][0] = fWidth * 2;
		FRAME_POSITION_MOVING[2][1] = 0;
		FRAME_POSITION_MOVING[3][0] = fWidth * 3;
		FRAME_POSITION_MOVING[3][1] = 0;

		FRAME_POSITION_MOVING[4][0] = 0;
		FRAME_POSITION_MOVING[4][1] = fHeight;
		FRAME_POSITION_MOVING[5][0] = fWidth;
		FRAME_POSITION_MOVING[5][1] = fHeight;
		FRAME_POSITION_MOVING[6][0] = fWidth * 2;
		FRAME_POSITION_MOVING[6][1] = fHeight;
		FRAME_POSITION_MOVING[7][0] = fWidth * 3;
		FRAME_POSITION_MOVING[7][1] = fHeight;

		FRAME_POSITION_MOVING[8][0] = fWidth * 2;
		FRAME_POSITION_MOVING[8][1] = fHeight;
		FRAME_POSITION_MOVING[9][0] = fWidth;
		FRAME_POSITION_MOVING[9][1] = fHeight;
		FRAME_POSITION_MOVING[10][0] = 0;
		FRAME_POSITION_MOVING[10][1] = fHeight;
		FRAME_POSITION_MOVING[11][0] = fWidth * 3;
		FRAME_POSITION_MOVING[11][1] = 0;
		FRAME_POSITION_MOVING[12][0] = fWidth * 2;
		FRAME_POSITION_MOVING[12][1] = 0;
		FRAME_POSITION_MOVING[13][0] = fWidth * 1;
		FRAME_POSITION_MOVING[13][1] = 0;

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
		super.setWidth(40);
		super.setHeight(getWidth() / animationFrameWidth * animationFrameHeight);
		super.setPositionX(400);
		super.setPositionY(200);
		super.setPositionXFinal(400);
		super.setPositionYFinal(200);
		super.setVelocityX(0);
		super.setRotationAngle(0);
		super.setIdleStateFrames(Resources.getAnimationFrame(CharacterType.SWORDMAN, State.IDLE));
		super.setMovingFrames(Resources.getAnimationFrame(CharacterType.SWORDMAN, State.MOVING));
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

	public void updateRunnerPC(double time) {
		double totalMovementDistance = movementSpeed * time;
		double moveX = 0;
		double moveY = 0;

		if (velocityX != 0 && velocityY != 0) {
			double tempShort = Math.sqrt(Math.pow(totalMovementDistance, 2) / 2);
			moveX = tempShort * velocityX;
			moveY = tempShort * velocityY;
		} else {
			moveX = totalMovementDistance * velocityX;
			moveY = totalMovementDistance * velocityY;
		}

		if (moveX < 0) {
			isFlipped = true;
		} else if (moveX > 0) {
			isFlipped = false;
		}

		if (moveX == 0 && moveY == 0) {
			currentState = State.IDLE;
			currentAnimationFrame = 0;
			movementDistance = 0;
		} else {
			currentState = State.MOVING;
			movementDistance += totalMovementDistance;
			currentAnimationFrame = Math.abs((int) ((movementDistance / animationSpeedMoving) % animationLengthMoving));
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
	}

	public void updateRunnerMobile(double time) {
		double totalMovementDistance = movementSpeed * time;
		double moveX = 0;
		double moveY = 0;
		double currentX = getPositionX();
		double currentY = getPositionY();
		double finalX = getPositionXFinal();
		double finalY = getPositionYFinal();

		if (!(currentX == finalX && currentY == finalY)) {
			currentState = State.MOVING;
			double sld = Math.sqrt(Math.pow(currentX - finalX, 2) + Math.pow(currentY - finalY, 2));
			if (sld < totalMovementDistance) {
				positionX = finalX;
				positionY = finalY;
			} else {
				double ratioToGo = totalMovementDistance / sld;
				moveX = (finalX - currentX) * ratioToGo;
				moveY = (finalY - currentY) * ratioToGo;
				if (moveX < 0) {
					isFlipped = true;
				} else if (moveX > 0) {
					isFlipped = false;
				}
				movementDistance += totalMovementDistance;
				currentAnimationFrame = Math
						.abs((int) ((movementDistance / animationSpeedMoving) % animationLengthMoving));
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
			currentState = State.IDLE;
			currentAnimationFrame = 0;
			movementDistance = 0;
		}
	}

	public void update(double time) {
		super.update(time);
		if (isAlive) {
			if (!player.isMobile) {
				updateRunnerPC(time);
			} else {
				updateRunnerMobile(time);
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
		gc.setGlobalBlendMode(BlendMode.SRC_OVER);
		if (isImmune) {
			gc.setEffect(bloom);
		} else {
			gc.setEffect(dropShadow);
		}
		if (isAlive) {
			if (currentState == State.IDLE) {
				if (!isFlipped) {
					gc.drawImage(idleStateFrames, FRAME_POSITION_IDLE[0][0], FRAME_POSITION_IDLE[0][1],
							animationFrameWidth, animationFrameHeight, positionX, positionY, displayWidth,
							displayHeight);
				} else {
					Rotate r = new Rotate(180, positionX, positionY);
					r.setAxis(Rotate.Y_AXIS);
					gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
					gc.translate(-width, 0);
					gc.drawImage(idleStateFrames, FRAME_POSITION_IDLE[0][0], FRAME_POSITION_IDLE[0][1],
							animationFrameWidth, animationFrameHeight, positionX, positionY, width, height);
				}
			} else {

				if (!isFlipped) {
					gc.drawImage(movingStateFrames, FRAME_POSITION_MOVING[currentAnimationFrame][0],
							FRAME_POSITION_MOVING[currentAnimationFrame][1], animationFrameWidth, animationFrameHeight,
							positionX, positionY, displayWidth, displayHeight);
				} else {
					Rotate r = new Rotate(180, positionX, positionY);
					r.setAxis(Rotate.Y_AXIS);
					gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
					gc.translate(-width, 0);
					gc.drawImage(movingStateFrames, FRAME_POSITION_MOVING[currentAnimationFrame][0],
							FRAME_POSITION_MOVING[currentAnimationFrame][1], animationFrameWidth, animationFrameHeight,
							positionX, positionY, width, height);
				}
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
		/*
		 * gc.save(); gc.setGlobalBlendMode(BlendMode.LIGHTEN);
		 * gc.setFill(player.getColor()); Bounds b =
		 * collisionZone.getLayoutBounds(); gc.fillRect(b.getMinX(),
		 * b.getMinY(), b.getWidth(), b.getHeight()); gc.restore();
		 */
	}
}
