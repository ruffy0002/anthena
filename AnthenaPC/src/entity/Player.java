package entity;

import controls.Control;
import javafx.scene.input.KeyCode;
import logic.Sprite;

public class Player extends Sprite {

	private Control control;
	private double movementSpeed = 100;
	private double rotationSpeed = 200;

	public Player() {
	}

	public void initHardCodeAnt() {
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
		super.setImage("ant.png");
	}

	public void initRandomAnt() {
		
		Control c = new Control();
		c.setUp(KeyCode.W);
		c.setDown(KeyCode.S);
		c.setLeft(KeyCode.A);
		c.setRight(KeyCode.D);
		control = c;
		
		super.setWidth(44.8);
		super.setHeight(40);
		super.setPositionX(200);
		super.setPositionY(200);
		super.setVelocityX(0);
		super.setRotationAngle(0);
		super.setImage("ant.png");
	}

	public void update(double time) {
		rotationAngle += velocityRotate * time;
		rotationAngle = rotationAngle % 360;
		double strightLineDistance = velocityX * time;
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
	
	public void setDead()
	{
		
	}

}