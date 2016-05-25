package entity;

import controls.Control;
import javafx.scene.input.KeyCode;
import resource.Resources;

public class Character extends Player {

	private double movementSpeed = 100;
	private double rotationSpeed = 200;
	private double movementDistance = 0;
	private boolean isAlive = true;

	public void init() {
		Control c = new Control();
		c.setUp(KeyCode.UP);
		c.setDown(KeyCode.DOWN);
		c.setLeft(KeyCode.LEFT);
		c.setRight(KeyCode.RIGHT);
		control = c;

		super.setWidth(44.8);
		super.setHeight(40);
		super.setPositionX(0);
		super.setPositionY(0);
		super.setVelocityX(0);
		super.setRotationAngle(0);
		super.setImage(Resources.getCharacterImage(0), Resources.getCharacterImage(1));

		animationLength = 3;
		animationSpeed = 9;
		animationFrameWidth = 249;
		animationFrameHeight = 222;

		color = Resources.getRandomColor();
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

	public void setDead() {
		isAlive = false;
	}

	public boolean isAlive() {
		return isAlive;
	}
}
