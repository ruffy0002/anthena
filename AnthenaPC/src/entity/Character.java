package entity;

import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;

import controls.Control;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class Character extends Sprite implements Comparable<Character> {

	protected Player player;
	protected Control control;
	double movementSpeed = 100;
	double rotationSpeed = 200;
	double movementDistance = 0;
	private boolean isAlive = true;
	protected boolean isFlipped = false;
	private boolean hasFinishDeathAnimation = false;

	private Image deathImage;
	protected int deathAnimationLength;
	protected double deathAnimationSpeed;
	protected double deathAnimationFrameSpeed;
	protected int deathAnimationFrameWidth;
	protected int deathAnimationFrameHeight;
	protected int currentDeathAnimationFrame;
	protected int deathAnimationRepeat;

	private double frameSpeedControl = 0;
	private Image defeatedImage;
	protected int defeatedAnimationLength;
	protected double defeatedAnimationSpeed;
	protected double defeatedAnimationFrameSpeed;
	protected int defeatedAnimationFrameWidth;
	protected int defeatedAnimationFrameHeight;
	protected int currentDefeatedAnimationFrame;
	protected int defeatAnimationRepeat;

	private Label nameLabel;

	public Character(Player p) {
		player = p;
		nameLabel = new Label(player.getName());
		FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
		double w = fontLoader.computeStringWidth(nameLabel.getText(), nameLabel.getFont());
		nameLabel.setMinWidth(w);
	}

	public void init() {

	}

	public Control getControl() {
		return control;
	}

	public void setControl(Control control) {
		this.control = control;
	}

	public void update(double time) {

		if (isAlive) {
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
				Rectangle r = (Rectangle) collisionZone;
				r.setX(r.getX() + moveX);
				r.setY(r.getY() + moveY);
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
		gc.save();
		if (isAlive) {
			gc.setGlobalBlendMode(BlendMode.SRC_OVER);
			if (!isFlipped) {
				gc.drawImage(image, animationFrameWidth * currentAnimationFrame, 0, animationFrameWidth,
						animationFrameHeight, positionX, positionY, displayWidth, displayHeight);
			} else {

				Rotate r = new Rotate(180, positionX, positionY);
				r.setAxis(Rotate.Y_AXIS);

				gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
				gc.translate(-width, 0);
				gc.drawImage(image, animationFrameWidth * currentAnimationFrame, 0, animationFrameWidth,
						animationFrameHeight, positionX, positionY, width, height);
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

		gc.save();
		gc.strokeText(nameLabel.getText(), positionX + (width - nameLabel.getMinWidth()) / 2, positionY + height + 12);
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

	public Image getDeathImage() {
		return deathImage;
	}

	public void setDeathImage(Image deathImage) {
		this.deathImage = deathImage;
	}

	public Image getDefeatedImage() {
		return defeatedImage;
	}

	public void setDefeatedImage(Image defeatedImage) {
		this.defeatedImage = defeatedImage;
	}

	@Override
	public int compareTo(Character c1) {
		if (c1.getZIndex() > this.getZIndex()) {
			return -1;
		} else if (c1.getZIndex() < this.getZIndex()) {
			return 1;
		}
		return 0;
	}

}
