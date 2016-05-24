package entity;

import controls.Control; 
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import logic.Sprite;
import resource.Resources;

public class Player extends Sprite {

	protected Control control;

	public Player() {
	}

	public void initHardCodeAnt() {
		

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
		super.setImage(Resources.getCharacterImage(0), Resources.getCharacterImage(1));
		
		color = Resources.getRandomColor();
	}

}