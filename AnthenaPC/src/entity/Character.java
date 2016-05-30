package entity;

import javafx.scene.input.KeyCode;

public class Character extends Player {

	double movementSpeed = 100;
	double rotationSpeed = 200;
	double movementDistance = 0;
	private boolean isAlive = true;

	public void init() {

	}

	public void update(double time) {

		double moveX = velocityX * time;
		double moveY = velocityY * time;

		if (moveX + moveY == 0) {
			currentAnimationFrame = 0;
			movementDistance = 0;
		} else {
			movementDistance += Math.sqrt(Math.pow(moveX, 2) + Math.pow(moveY, 2));
			currentAnimationFrame = Math.abs((int) ((movementDistance / animationSpeed) % animationLength));
			currentAnimationFrame = currentAnimationFrame + 1;
			if (currentAnimationFrame > 5) {
				int temp = animationLength - currentAnimationFrame;
				currentAnimationFrame =  5 - temp;
			}
			System.out.println(currentAnimationFrame);
			super.setPositionX(positionX + moveX);
			super.setPositionY(positionY + moveY);
		}
	}

	public void update(KeyCode code) {

		if (control.contains(code)) {
			if (code.equals(control.getUp())) {
				super.setVelocityY(-movementSpeed);
			}

			if (code.equals(control.getDown())) {
				super.setVelocityY(movementSpeed);
			}

			if (code.equals(control.getLeft())) {
				super.setVelocityX(-movementSpeed);
			}

			if (code.equals(control.getRight())) {
				super.setVelocityX(movementSpeed);
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
