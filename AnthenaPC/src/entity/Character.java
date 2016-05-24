package entity;

import controls.Control;
import javafx.scene.input.KeyCode;

public class Character extends Player {

	private double movementSpeed = 100;
	private double rotationSpeed = 200;
	private double movementDistance = 0;

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
		super.setImage("sprite/ant.png");

		animationLength = 3;
		animationSpeed = 9;
		animationFrameWidth = 249;
		animationFrameHeight = 222;
	}

	public void update(double time) {
		rotationAngle += velocityRotate * time;
		rotationAngle = rotationAngle % 360;
		double strightLineDistance = velocityX * time;
		movementDistance += strightLineDistance;
		currentAnimationFrame = (int) ((movementDistance / animationSpeed) % animationLength);
		positionY += -(strightLineDistance * Math.cos(Math.toRadians(rotationAngle)));
		positionX += (strightLineDistance * Math.sin(Math.toRadians(rotationAngle)));
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

	}
}