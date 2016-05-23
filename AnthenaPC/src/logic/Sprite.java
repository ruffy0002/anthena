package logic;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;

public class Sprite {

	private Image image;
	protected double positionX;
	protected double positionY;
	protected double velocityX;
	protected double velocityY;
	private double width;
	private double height;
	private double halfWidth;
	private double halfHeight;
	protected double rotationAngle;
	protected double velocityRotate;

	public double getVelocityRotate() {
		return velocityRotate;
	}

	public void setVelocityRotate(double velocityRotate) {
		this.velocityRotate = velocityRotate;
	}

	public double getRotationAngle() {
		return rotationAngle;
	}

	public void setRotationAngle(double rotationAngle) {
		this.rotationAngle = rotationAngle;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = new Image(image);
	}

	public double getPositionX() {
		return positionX;
	}

	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}

	public double getPositionY() {
		return positionY;
	}

	public void setPositionY(double positionY) {
		this.positionY = positionY;
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

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
		halfWidth = width / 2;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
		halfHeight = height / 2;
	}

	public void update(double time) {
		positionX += velocityX * time;
		positionY += velocityY * time;
	}

	private void rotate(GraphicsContext gc, double angle, double px, double py) {
		Rotate r = new Rotate(angle, px, py);
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
	}

	public void render(GraphicsContext gc) {
		gc.save();
		rotate(gc, rotationAngle, positionX + halfWidth, positionY + halfHeight);
		gc.drawImage(image, 0, 0, 250, 222, positionX, positionY, width, height);
		gc.restore();
	}

	public Rectangle getBoundary() {

		Rectangle r = new Rectangle(positionX, positionY, width, height);
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
