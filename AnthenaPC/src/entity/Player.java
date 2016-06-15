package entity;

import controls.Control;
import javafx.scene.paint.Color;

public class Player {

	protected Control control;
	private String name;
	private Color color;
	private int score;

	private Sprite sprite;
	private int health;

	public Player(Control control, String name, Color color) {
		this.name = name;
		this.control = control;
		this.color = color;
		health = 3;
	}

	public Control getControl() {
		return control;
	}

	public void setControl(Control control) {
		this.control = control;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public void takeDamage(){
		this.health--;
	}

	public Character createSprite() {
		CharacterSwordMan cm = new CharacterSwordMan(this);
		cm.setControl(control);
		return cm;
	}

	public void addScore(int i) {
		score+=i;
	}

	public int getScore() {
		return score;
	}

}