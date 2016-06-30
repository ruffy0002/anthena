package logic;

import java.util.ArrayList;
import java.util.Random;

import entity.Attack;
import entity.Collectable;
import entity.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Shape;

public class AttackManager {

	private int mode = 1;
	private ArrayList<Attack> attacks = new ArrayList<Attack>();
	private Shape map_boundary;

	private double spawnminX;
	private double spawnmaxX;
	private double spawnZoneWidth;

	private double spawnminY;
	private double spawnmaxY;
	private double spawnZoneHeight;

	private double rateOfSpawn = 1;
	private double maxCount = 10;
	private double elapsedTimeStore = 0;

	public AttackManager(Shape map_boundary) {
		this.map_boundary = map_boundary;
		spawnminX = map_boundary.getBoundsInLocal().getMinX() + (Attack.WIDTH / 2);
		spawnmaxX = map_boundary.getBoundsInLocal().getMaxX() - (Attack.WIDTH / 2);
		spawnZoneWidth = spawnmaxX - spawnminX;

		spawnminY = map_boundary.getBoundsInLocal().getMinY() + (Attack.visibleHeight / 2);
		spawnmaxY = map_boundary.getBoundsInLocal().getMaxY() - (Attack.visibleHeight / 2);
		spawnZoneHeight = spawnmaxY - spawnminY;
	}

	public ArrayList<Attack> getAttacks() {
		return attacks;
	}

	public void update(double elapsedTime) {
		for (int k = 0; k < attacks.size(); k++) {
			if (attacks.get(k).getReadyToClear()) {
				attacks.remove(k--);
			} else {
				attacks.get(k).update(elapsedTime);
			}
		}

		if (mode == 0) {
			elapsedTimeStore += elapsedTime;
			if (elapsedTimeStore > rateOfSpawn) {
				if (attacks.size() < maxCount) {
					elapsedTimeStore = elapsedTimeStore - rateOfSpawn;
					spawnAttack();
				}
			}
		}
	}

	public void draw(GraphicsContext graphicContext) {
		for (int i = 0; i < attacks.size(); i++) {
			attacks.get(i).render(graphicContext);
		}
	}

	public void spawnAttack() {
		Random r = new Random();
		double posX = (spawnZoneWidth * r.nextDouble()) + spawnminX;
		double posY = (spawnZoneHeight * r.nextDouble()) + spawnminY;
		Attack att = new Attack(posX, posY, null);
		attacks.add(att);
	}

	public void createAttack(float x, float y, Player player) {
		double x1 = 800 * x;
		double y1 = 600 * y;
		Attack att = new Attack(x1, y1, player);
		if (inBounds(att)) {
			attacks.add(att);
		}
	}

	public boolean inBounds(Attack att) {
		Shape shape = Shape.subtract(att.getCollisionZone(), map_boundary);
		if (shape.getBoundsInLocal().getWidth() == -1 && shape.getBoundsInLocal().getHeight() == -1) {
			return true;
		}
		return false;
	}
}
