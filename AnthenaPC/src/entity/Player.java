package entity;

import controls.Control;
import javafx.scene.paint.Color;

public class Player {

	protected Control control;
	private String name;
	private Color color;

	private Sprite sprite;

	public Player(Control control, String name, Color color) {
		this.name = name;
		this.control = control;
		this.color = color;
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

	public Character createSprite() {
		CharacterSwordMan cm = new CharacterSwordMan();
		cm.setControl(control);
		return cm;
	}

}