package entity;

import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;

import controls.Control;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.util.Pair;
import resource.Resources;

public class Character extends Sprite {

	public enum State {
		IDLE, MOVING, ATTACKING, DEFEATED, DEAD, IMMUNE, STUN;
	}

	protected Player player;
	protected State currentState;
	protected Control control;

	protected double positionXFinal;
	protected double positionYFinal;
	protected double movementSpeed = 100;
	protected double rotationSpeed = 200;
	protected double movementDistance = 0;
	protected boolean isAlive = true;
	protected boolean isFlipped = false;
	protected boolean isImmune = false;
	protected double immuneTime = 2;
	protected double immuneFlashSpeed = 0.1;
	protected double immuneStunTime = 1;
	protected double immuneOpacity = 1;
	protected int bloomDirection = 1;
	protected double immuneTimeStore = 0;
	protected boolean hasFinishDeathAnimation = false;

	protected Image deathImage;
	protected int deathAnimationLength;
	protected double deathAnimationSpeed;
	protected double deathAnimationFrameSpeed;
	protected int deathAnimationFrameWidth;
	protected int deathAnimationFrameHeight;
	protected int currentDeathAnimationFrame;
	protected int deathAnimationRepeat;

	protected double frameSpeedControl = 0;
	protected Image defeatedImage;
	protected int defeatedAnimationLength;
	protected double defeatedAnimationSpeed;
	protected double defeatedAnimationFrameSpeed;
	protected int defeatedAnimationFrameWidth;
	protected int defeatedAnimationFrameHeight;
	protected int currentDefeatedAnimationFrame;
	protected int defeatAnimationRepeat;

	private static Image heart = Resources.getHeart();
	private static double heartWidth = 12;
	private static double heartHeight = 12;
	DropShadow dropShadow;
	Shadow shadow;
	Bloom bloom;

	public Character(Player p) {
		currentState = State.IDLE;
		player = p;
		dropShadow = new DropShadow();
		dropShadow.setRadius(5.0);
		dropShadow.setOffsetX(-5.0);
		dropShadow.setOffsetY(3.0);
		dropShadow.setColor(Color.BLACK);
		shadow = new Shadow(BlurType.GAUSSIAN, new Color(1, 0, 0, 0.3), 0.1);
		bloom = new Bloom();
		bloom.setThreshold(0.3);
		// bloom.setInput(shadow);
	}

	public void init() {

	}

	public Control getControl() {
		return control;
	}

	public void setControl(Control control) {
		this.control = control;
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

	public void update(double time) {
		if (isImmune && isAlive) {
			immuneTimeStore -= time;
			if (immuneTimeStore <= 0) {
				isImmune = false;
			}
			double tPast = immuneTime - immuneTimeStore;
			int noTick = (int) (tPast / immuneFlashSpeed);
			int val = noTick % 2;
			immuneOpacity = val;
		}
	}

	public void render(GraphicsContext gc) {
		renderCharacter(gc);
		renderName(gc);
		renderHealth(gc);
	}

	public void renderCharacter(GraphicsContext gc) {
	}

	public void renderName(GraphicsContext gc) {
		gc.save();
		gc.setGlobalBlendMode(BlendMode.SRC_OVER);
		gc.setEffect(dropShadow);
		gc.setStroke(player.getColor());
		gc.strokeText(player.getNameLabel().getText(), positionX + (width - player.getNameLabel().getMinWidth()) / 2,
				positionY + height + 12);
		gc.restore();
	}

	public void renderHealth(GraphicsContext gc) {
		gc.save();
		gc.setGlobalBlendMode(BlendMode.SRC_OVER);
		for (int i = 0; i < player.getHealth(); i++) {
			double tempC = heartWidth * i;
			gc.drawImage(heart, 0, 0, 65, 60, positionX + tempC, positionY - heartHeight, heartWidth, heartHeight);
		}
		gc.restore();
	}

	public void update(KeyCode code) {
		if (control.contains(code)) {
			if (code.equals(control.getUp())) {
				super.setVelocityY(-1);
			}

			if (code.equals(control.getDown())) {
				super.setVelocityY(1);
			}

			if (code.equals(control.getLeft())) {
				super.setVelocityX(-1);
			}

			if (code.equals(control.getRight())) {
				super.setVelocityX(1);
			}
		}
	}

	public void update(Movement code) {
		if (code == Movement.UP) {
			super.setVelocityY(-1);
		} else if (code == Movement.RIGHT) {
			super.setVelocityX(1);
		} else if (code == Movement.DOWN) {
			super.setVelocityY(1);
		} else if (code == Movement.LEFT) {
			super.setVelocityX(-1);
		}
	}

	public Pair<Double, Double> getCollideXY(Shape s, double moveX, double moveY) {

		double xCollide = moveX;
		double yCollide = moveY;

		Shape col = Shape.union(s, mapBoundary);
		double extraX = col.getLayoutBounds().getWidth() - mapBoundary.getLayoutBounds().getWidth();
		double extraY = col.getLayoutBounds().getHeight() - mapBoundary.getLayoutBounds().getHeight();
		if (extraX != 0) {
			if (xCollide < 0) {
				xCollide += extraX;
			} else {
				xCollide -= extraX;
			}
		}
		if (extraY != 0) {
			if (yCollide < 0) {
				yCollide += extraY;
			} else {
				yCollide -= extraY;
			}
		}

		Pair<Double, Double> colXcolY = new Pair<Double, Double>(xCollide, yCollide);
		return colXcolY;
	}

	public void increaseSpeed() {
		movementSpeed += 100;
		player.addScore(10);
	}

	public void takeDamage() {
		player.takeDamage();
		isImmune = true;
		immuneTimeStore = immuneTime;
		if (player.getHealth() <= 0) {
			isAlive = false;
		}
	}

	public boolean isImmune() {
		return isImmune;
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setState(State s) {
		currentState = s;
	}

	public void setPositionXFinal(double xPos) {
		positionXFinal = xPos;
	}

	public void setPositionYFinal(double yPos) {
		positionYFinal = yPos;
	}

	public double getPositionXFinal() {
		return positionXFinal;
	}

	public double getPositionYFinal() {
		return positionYFinal;
	}

	public Player getPlayer() {
		return player;
	}
}
