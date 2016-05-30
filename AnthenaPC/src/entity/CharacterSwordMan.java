package entity;

import controls.Control;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.KeyCode;
import resource.Resources;

public class CharacterSwordMan extends Character {

	public void init() {
		super.init();

		Control c = new Control();
		c.setUp(KeyCode.UP);
		c.setDown(KeyCode.DOWN);
		c.setLeft(KeyCode.LEFT);
		c.setRight(KeyCode.RIGHT);
		control = c;

		animationLength = 9;
		animationSpeed = 9;
		animationFrameWidth = 300;
		animationFrameHeight = 531;

		super.setWidth(100);
		super.setHeight(getWidth() / animationFrameWidth * animationFrameHeight);
		super.setPositionX(0);
		super.setPositionY(0);
		super.setVelocityX(0);
		super.setRotationAngle(0);
		super.setImage(Resources.getcharacterSet(0), null);

		color = Resources.getRandomColor();
	}
}
