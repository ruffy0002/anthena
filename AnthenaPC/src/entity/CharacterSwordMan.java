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
		
		deathAnimationLength =2;
		deathAnimationSpeed =5;
		
		defeatedAnimationLength = 2;
		defeatedAnimationSpeed =5;

		super.setScaleXY(1,1);
		super.setWidth(50);
		super.setHeight(getWidth() / animationFrameWidth * animationFrameHeight);
		super.setPositionX(0);
		super.setPositionY(0);
		super.setVelocityX(0);
		super.setRotationAngle(0);
		super.setImage(Resources.getcharacterSet(0), null);
		super.setDeathImage(Resources.getDeathImage(0));
		super.setDefeatedImage(Resources.getDefeatedImage(0));
		
	}
}
