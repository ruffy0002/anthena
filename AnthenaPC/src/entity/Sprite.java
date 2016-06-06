package entity;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Shear;

public class Sprite {

	protected double perceptionRotate;
	protected Image image;
	protected Image image2;

	protected Shape mapBoundary;
	
	protected int animationLength;
	protected int animationSpeed;
	protected int currentAnimationFrame;
	protected int currentAnimationFrameX;
	protected int currentAnimationFrameY;
	protected int animationFrameWidth;
	protected int animationFrameHeight;

	protected double positionX;
	protected double positionY;

	protected double previousX;
	protected double previousY;

	protected double boundaryX;
	protected double boundaryY;

	protected double previousBoundaryX;
	protected double previousBoundaryY;

	protected double velocityX;
	protected double velocityY;

	protected double width;
	protected double height;
	protected double halfWidth;
	protected double halfHeight;

	protected double displayWidth;
	protected double displayHeight;

	protected double rotationAngle;
	protected double velocityRotate;

	protected double scaleX;
	protected double scaleY;

	protected Shape collisionZone;

	public Shape getCollisionZone() {
		return collisionZone;
	}

	public void setCollisionZone(Shape collisionZone) {
		this.collisionZone = collisionZone;
	}

	public void setCollisonZoneXY(double x, double y) {
		Rectangle r = (Rectangle) collisionZone;
		previousBoundaryX = r.getX();
		previousBoundaryY = r.getY();
		r.setX(r.getX() + x);
		r.setY(r.getY() + y);
	}

	public void setImage(Image image, Image image2) {
		this.image = image;
		this.image2 = image2;
	}

	public void setWidth(double width) {
		this.width = width;
		halfWidth = width / 2;
		displayWidth = width * scaleX;
	}

	public void setHeight(double height) {
		this.height = height;
		halfHeight = height / 2;
		displayHeight = height * scaleX;
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
		this.previousX = positionX;
		this.positionX = positionX;
		boundaryX = positionX;
	}

	public double getPositionY() {
		return positionY;
	}

	public void setPositionY(double positionY) {
		this.previousY = positionY;
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
		Affine temp = gc.getTransform();
		temp.append(r);
		gc.setTransform(temp);
	}

	protected void rotateX(GraphicsContext gc) {
		Rotate r = new Rotate(perceptionRotate, positionX, positionY);
		r.setAxis(Rotate.X_AXIS);
		Affine temp = gc.getTransform();
		temp.append(r);
		gc.setTransform(temp);
	}

	public void render(GraphicsContext gc) {
		gc.save();

		gc.setGlobalBlendMode(BlendMode.DARKEN);
		gc.drawImage(image, animationFrameWidth * currentAnimationFrame, 0, animationFrameWidth, animationFrameHeight,
				positionX, positionY, width, height);

		gc.restore();
	}

	public void setScaleXY(double x, double y) {
		scaleX = x;
		scaleY = y;
	}

	public void updateScale(double x, double y) {
		scaleX = x;
		scaleY = y;
		displayWidth = width * x;
		displayHeight = height * y;
	}

	public boolean intersects(Sprite s) {
		Shape rec = Rectangle.intersect(this.getCollisionZone(), s.getCollisionZone());
		Bounds bound = rec.getLayoutBounds();
		if (bound.getWidth() > 0 || bound.getHeight() > 0) {
			return true;
		}
		return false;
	}

	public void spiltFrameToXandY(int x) {

		if (currentAnimationFrame != 0) {
			currentAnimationFrameX = currentAnimationFrame % x;
			currentAnimationFrameY = currentAnimationFrame / x;
		} else {
			currentAnimationFrameX = 0;
			currentAnimationFrameY = 0;
		}
	}

	public double getZIndex() {
		return positionY + displayHeight;
	}
	
	public void setGameBoundary(Shape boundary){
		mapBoundary = boundary;
	}
}
