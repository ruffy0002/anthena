package entity;

import resource.Resources;

public class CharacterSwordMan extends Character {

	public CharacterSwordMan() {
		init();
	}

	public void init() {
		super.init();
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
	}
}
