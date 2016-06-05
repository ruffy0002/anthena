package entity;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.shape.Rectangle;
import resource.Resources;

public class CharacterSwordMan extends Character {

	public CharacterSwordMan(Player p) {
		super(p);
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
		super.setPositionX(400);
		super.setPositionY(200);
		super.setVelocityX(0);
		super.setRotationAngle(0);
		super.setImage(Resources.getcharacterSet(0), null);
		super.setDeathImage(Resources.getDeathImage(0));
		super.setDefeatedImage(Resources.getDefeatedImage(0));
		calculateBoundary();
	}

	public void calculateBoundary() {
		double boundaryWidth = width * 0.6;
		double boundaryHeight = height * 0.3;
		double boundaryX = positionX + (width - boundaryWidth) / 3;
		double boundaryY = positionY + (height - boundaryHeight);
		collisionZone = new Rectangle(boundaryX, boundaryY, boundaryWidth, boundaryHeight);
	}

	public void render(GraphicsContext gc) {
		super.render(gc);

		// draw collison box
		gc.save();
		gc.setGlobalBlendMode(BlendMode.LIGHTEN);
		gc.setFill(player.getColor());
		Bounds b = collisionZone.getLayoutBounds();
		gc.fillRect(b.getMinX(), b.getMinY(), b.getWidth(), b.getHeight());
		gc.restore();
	}
}
