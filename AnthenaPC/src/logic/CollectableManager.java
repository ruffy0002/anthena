package logic;

import java.util.ArrayList;
import java.util.Random;

import entity.Collectable;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class CollectableManager {

	private ArrayList<Collectable> collectable = new ArrayList<Collectable>();
	private Shape map_boundary;

	private double spawnminX;
	private double spawnmaxX;
	private double spawnZoneWidth;

	private double spawnminY;
	private double spawnmaxY;
	private double spawnZoneHeight;

	private double rateOfSpace = 3;
	private double maxCount = 5;
	private double elapsedTimeStore = 0;

	public CollectableManager(Shape map_boundary) {
		this.map_boundary = map_boundary;
		spawnminX = map_boundary.getBoundsInLocal().getMinX();
		spawnmaxX = map_boundary.getBoundsInLocal().getMaxX() - Collectable.DRAW_WIDTH;
		spawnZoneWidth = spawnmaxX - spawnminX;

		spawnminY = map_boundary.getBoundsInLocal().getMinY();
		spawnmaxY = map_boundary.getBoundsInLocal().getMaxY() - Collectable.DRAW_HEIGHT;
		spawnZoneHeight = spawnmaxY - spawnminY;
	}

	public ArrayList<Collectable> getCollectable() {
		return collectable;
	}

	public void update(double elapsedTime) {

		for (int k = 0; k < collectable.size(); k++) {
			if (collectable.get(k).isActive()) {
				collectable.get(k).update(elapsedTime);
			} else {
				collectable.remove(k--);
			}
		}

		elapsedTimeStore += elapsedTime;
		
		if (elapsedTimeStore > rateOfSpace) {
			if (collectable.size() < maxCount) {
				spawnCollectable();
				elapsedTimeStore = 0;
			}
		}
	}

	public void spawnCollectable() {
		Random r = new Random();
		double posX = (spawnZoneWidth * r.nextDouble());
		double posY = (spawnZoneHeight * r.nextDouble());
		Collectable c = new Collectable(posX, posY);
		collectable.add(c);
	}
}
