package controls;

import javafx.scene.input.KeyCode;

public class Control {

	private KeyCode up;
	private KeyCode down;
	private KeyCode left;
	private KeyCode right;
	private KeyCode skill1;

	public Control(KeyCode left, KeyCode up, KeyCode right, KeyCode down) {
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
	}
	
	public Control(KeyCode left, KeyCode up, KeyCode right, KeyCode down, KeyCode skill1) {
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
		this.skill1 = skill1;
	}

	public KeyCode getUp() {
		return up;
	}

	public void setUp(KeyCode up) {
		this.up = up;
	}

	public KeyCode getDown() {
		return down;
	}

	public void setDown(KeyCode down) {
		this.down = down;
	}

	public KeyCode getLeft() {
		return left;
	}

	public void setLeft(KeyCode left) {
		this.left = left;
	}

	public KeyCode getRight() {
		return right;
	}

	public void setRight(KeyCode right) {
		this.right = right;
	}

	public KeyCode getSkill1() {
		return skill1;
	}

	public void setSkill1(KeyCode skill1) {
		this.skill1 = skill1;
	}

	public boolean contains(KeyCode code) {
		if (this.up.compareTo(code) == 0)
			return true;
		if (this.down.compareTo(code) == 0)
			return true;
		if (this.left.compareTo(code) == 0)
			return true;
		if (this.right.compareTo(code) == 0)
			return true;
		if(this.skill1.compareTo(code) == 0)
			return true;
		return false;
	}

}
