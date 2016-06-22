package entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import resource.Resources;

public class Ant extends Character {

	public Ant(Player p) {
		super(p);
	}

	public void init() {
		super.setWidth(44.8);
		super.setHeight(40);
		super.setPositionX(0);
		super.setPositionY(0);
		super.setVelocityX(0);
		super.setRotationAngle(0);
		super.setImage(Resources.getCharacterImage(0));

		animationLength = 3;
		animationSpeed = 9;
		animationFrameWidth = 249;
		animationFrameHeight = 222;
	}

	public void initRandomAnt() {
		super.setWidth(44.8);
		super.setHeight(40);
		super.setPositionX(200);
		super.setPositionY(200);
		super.setVelocityX(0);
		super.setRotationAngle(0);
		super.setImage(Resources.getCharacterImage(0));
	}

	public void update(double time) {

		double strightLineDistance = velocityX * time;
		if (strightLineDistance != 0) {
			rotationAngle += velocityRotate * time;
			rotationAngle = rotationAngle % 360;
		}

		movementDistance += strightLineDistance;
		currentAnimationFrame = Math.abs((int) ((movementDistance / animationSpeed) % animationLength));
		super.setPositionX(positionX + (strightLineDistance * Math.sin(Math.toRadians(rotationAngle))));
		super.setPositionY(positionY + -(strightLineDistance * Math.cos(Math.toRadians(rotationAngle))));
	}

	public void update(KeyCode code) {

		if (control.contains(code)) {
			if (code.equals(control.getUp())) {
				super.setVelocityX(movementSpeed);
			}

			if (code.equals(control.getDown())) {
				super.setVelocityX(-movementSpeed);
			}

			if (code.equals(control.getLeft())) {
				super.setVelocityRotate(-rotationSpeed);
			}

			if (code.equals(control.getRight())) {
				super.setVelocityRotate(rotationSpeed);
			}
		}
	}

	public void render(GraphicsContext gc) {
		gc.save();

		rotate(gc, rotationAngle, positionX + halfWidth, positionY + halfHeight);

		gc.setGlobalBlendMode(BlendMode.DARKEN);
		gc.drawImage(image, animationFrameWidth * currentAnimationFrame, 0, animationFrameWidth, animationFrameHeight,
				positionX, positionY, width, height);

		gc.restore();
	}

	public Rectangle getBoundary() {
		/*Rectangle r = new Rectangle(boundaryX, boundaryY, width, height);
		r.setRotate(rotationAngle);*/
		return null;
	}
}
