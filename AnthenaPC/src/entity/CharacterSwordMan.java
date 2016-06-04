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

		deathAnimationLength = 2;
		deathAnimationSpeed = 0.5;
		deathAnimationFrameSpeed = deathAnimationSpeed / deathAnimationLength;
		deathAnimationFrameWidth = 250;
		deathAnimationFrameHeight = 270;
		currentDeathAnimationFrame = 0;
		deathAnimationRepeat = -1;

		defeatedAnimationLength = 2;
		defeatedAnimationSpeed = 1;
		defeatedAnimationFrameSpeed = defeatedAnimationSpeed / defeatedAnimationLength;
		defeatedAnimationFrameWidth = 300;
		defeatedAnimationFrameHeight = 531;
		currentDefeatedAnimationFrame = 0;
		defeatAnimationRepeat = 3;

		super.setScaleXY(1, 1);
		super.setWidth(80);
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
