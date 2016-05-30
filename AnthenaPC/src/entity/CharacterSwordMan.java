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
		
		super.setWidth(50);
		super.setHeight(88.5);
		super.setPositionX(0);
		super.setPositionY(0);
		super.setVelocityX(0);
		super.setRotationAngle(0);
		super.setImage(Resources.getcharacterSet(0), null);
		
		animationLength = 9;
		animationSpeed = 9;
		animationFrameWidth = 300;
		animationFrameHeight = 531;
		
		color = Resources.getRandomColor();
	}

	
	public void render(GraphicsContext gc) {
		gc.save();

		gc.setGlobalBlendMode(BlendMode.DARKEN);
		gc.drawImage(image, animationFrameWidth * currentAnimationFrame, 0, animationFrameWidth, animationFrameHeight,
				positionX, positionY, width, height);

		gc.restore();
	}
}
