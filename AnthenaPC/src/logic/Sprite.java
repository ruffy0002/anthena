package logic;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;

public class Sprite {

	protected Image image;
	protected Image image2;
	protected Color color;
	protected int animationLength;
	protected int animationSpeed;
	protected int currentAnimationFrame;
	protected int animationFrameWidth;
	protected int animationFrameHeight;

	protected double positionX;
	protected double positionY;

	protected double boundaryX;
	protected double boundaryY;

	protected double velocityX;
	protected double velocityY;
	protected double width;
	protected double height;
	protected double halfWidth;
	protected double halfHeight;
	protected double rotationAngle;
	protected double velocityRotate;
	protected double scale;

	public void setImage(Image image, Image image2) {
		this.image = image;
		this.image2 = image2;
	}

	public void setWidth(double width) {
		this.width = width;
		halfWidth = width / 2;
	}

	public void setHeight(double height) {
		this.height = height;
		halfHeight = height / 2;
		boundaryY = height;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getAnimationLength() {
		return animationLength;
	}

	public void setAnimationLength(int animationLength) {
		this.animationLength = animationLength;
	}

	public int getAnimationSpeed() {
		return animationSpeed;
	}

	public void setAnimationSpeed(int animationSpeed) {
		this.animationSpeed = animationSpeed;
	}

	public double getPositionX() {
		return positionX;
	}

	public void setPositionX(double positionX) {
		this.positionX = positionX;
		boundaryX = positionX;
	}

	public double getPositionY() {
		return positionY;
	}

	public void setPositionY(double positionY) {
		this.positionY = positionY;
		boundaryY = positionY;
	}

	public double getVelocityX() {
		return velocityX;
	}

	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}

	public double getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}

	public double getHalfWidth() {
		return halfWidth;
	}

	public void setHalfWidth(double halfWidth) {
		this.halfWidth = halfWidth;
	}

	public double getBoundaryX() {
		return boundaryX;
	}

	public void setBoundaryX(double boundaryX) {
		this.boundaryX = boundaryX;
	}

	public double getBoundaryY() {
		return boundaryY;
	}

	public void setBoundaryY(double boundaryY) {
		this.boundaryY = boundaryY;
	}

	public double getHalfHeight() {
		return halfHeight;
	}

	public void setHalfHeight(double halfHeight) {
		this.halfHeight = halfHeight;
	}

	public double getRotationAngle() {
		return rotationAngle;
	}

	public void setRotationAngle(double rotationAngle) {
		this.rotationAngle = rotationAngle;
	}

	public double getVelocityRotate() {
		return velocityRotate;
	}

	public void setVelocityRotate(double velocityRotate) {
		this.velocityRotate = velocityRotate;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public void update(double time) {
		
	}

	protected void rotate(GraphicsContext gc, double angle, double px, double py) {
		Rotate r = new Rotate(angle, px, py);
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
	}

	public void render(GraphicsContext gc) {
		gc.save();

		gc.setGlobalBlendMode(BlendMode.DARKEN);
		gc.drawImage(image, animationFrameWidth * currentAnimationFrame, 0, animationFrameWidth, animationFrameHeight,
				positionX, positionY, width, height);

		gc.restore();
	}

	public Rectangle getBoundary() {
		Rectangle r = new Rectangle(boundaryX, boundaryY, width, height);
		r.setRotate(rotationAngle);
		return r;
	}

	public boolean intersects(Sprite s) {
		Shape rec = Rectangle.intersect(this.getBoundary(), s.getBoundary());
		Bounds bound = rec.getLayoutBounds();
		if (bound.getWidth() > 0 || bound.getHeight() > 0) {
			return true;
		}
		return false;
	}

}
