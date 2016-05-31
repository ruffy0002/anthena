package entity;

import controls.Control;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.KeyCode;
import javafx.scene.transform.Rotate;

public class Character extends Sprite {

	protected Control control;

	double movementSpeed = 100;
	double rotationSpeed = 200;
	double movementDistance = 0;
	private boolean isAlive = true;
	private boolean isFlipped = false;

	public void init() {

	}
	
	public Control getControl() {
		return control;
	}

	public void setControl(Control control) {
		this.control = control;
	}

	public void update(double time) {

		double moveX = velocityX * time;
		double moveY = velocityY * time;

		if (moveX < 0) {
			isFlipped = true;
		} else if (moveX > 0) {
			isFlipped = false;
		}

		if (moveX == 0 && moveY == 0) {
			currentAnimationFrame = 0;
			movementDistance = 0;
		} else {
			movementDistance += Math.sqrt(Math.pow(moveX, 2) + Math.pow(moveY, 2));
			currentAnimationFrame = Math.abs((int) ((movementDistance / animationSpeed) % animationLength));
			currentAnimationFrame = currentAnimationFrame + 1;
			if (currentAnimationFrame > 5) {
				int temp = animationLength - currentAnimationFrame;
				currentAnimationFrame = 5 - temp;
			}
			super.setPositionX(positionX + moveX);
			super.setPositionY(positionY + moveY);
		}
	}

	public void render(GraphicsContext gc) {
		gc.save();
		gc.setGlobalBlendMode(BlendMode.DARKEN);
		if (!isFlipped) {
			gc.drawImage(image, animationFrameWidth * currentAnimationFrame, 0, animationFrameWidth,
					animationFrameHeight, positionX, positionY, width, height);
		} else {

			Rotate r = new Rotate(180, positionX, positionY);
			r.setAxis(Rotate.Y_AXIS);

			gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
			gc.translate(-width, 0);
			gc.drawImage(image, animationFrameWidth * currentAnimationFrame, 0, animationFrameWidth,
					animationFrameHeight, positionX, positionY, width, height);
		}

		gc.restore();
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
