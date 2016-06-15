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
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.util.Pair;

public class Character extends Sprite {

	protected Player player;
	protected Control control;
	protected double movementSpeed = 100;
	protected double rotationSpeed = 200;
	protected double movementDistance = 0;
	protected boolean isAlive = true;
	protected boolean isFlipped = false;
	protected boolean isImmune = false;
	protected double immuneTime = 3;
	private double immuneTimeStore = 0;
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
		if(isImmune){
			immuneTimeStore -= time;
			if(immuneTimeStore <=0){
				isImmune = false;
			}
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
		gc.setStroke(player.getColor());
		gc.strokeText(nameLabel.getText(), positionX + (width - nameLabel.getMinWidth()) / 2, positionY + height + 12);
		gc.restore();
	}

	public void renderHealth(GraphicsContext gc) {
		gc.save();
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

	public Pair<Boolean, Boolean> getCollidePair(Shape bound) {

		Shape col = Rectangle.union(bound, collisionZone);
		boolean xCollide = false;
		boolean yCollide = false;
		if (col.getLayoutBounds().getWidth() != bound.getLayoutBounds().getWidth()) {
			xCollide = true;
		}
		if (col.getLayoutBounds().getHeight() != bound.getLayoutBounds().getHeight()) {
			yCollide = true;
		}

		Pair<Boolean, Boolean> colXcolY = new Pair<Boolean, Boolean>(xCollide, yCollide);
		return colXcolY;
	}

	public void increaseSpeed() {
		movementSpeed += 10;
	}

	public void takeDamage() {
		player.takeDamage();
		isImmune = true;
		immuneTimeStore = immuneTime;
		if (player.getHealth() < 0) {
			isAlive = false;
		}
	}

	public boolean isImmune() {
		return isImmune;
	}

}
