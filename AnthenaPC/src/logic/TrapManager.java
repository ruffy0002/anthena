package logic;

import java.util.ArrayList;

import entity.Attack;
import entity.Player;
import entity.Trap;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Shape;

public class TrapManager {
	
	private ArrayList<Trap> traps = new ArrayList<Trap>();
	private Shape map_boundary;

	private double spawnminX;
	private double spawnmaxX;
	private double spawnZoneWidth;

	private double spawnminY;
	private double spawnmaxY;
	private double spawnZoneHeight;
	
	public TrapManager(Shape map_boundary) {
		this.map_boundary = map_boundary;
		spawnminX = map_boundary.getBoundsInLocal().getMinX() + (Trap.WIDTH / 2);
		spawnmaxX = map_boundary.getBoundsInLocal().getMaxX() - (Trap.WIDTH / 2);
		spawnZoneWidth = spawnmaxX - spawnminX;

		spawnminY = map_boundary.getBoundsInLocal().getMinY() + (Trap.visibleHeight / 2);
		spawnmaxY = map_boundary.getBoundsInLocal().getMaxY() - (Trap.visibleHeight / 2);
		spawnZoneHeight = spawnmaxY - spawnminY;
	}
	
	public void draw(GraphicsContext graphicContext) {
		for (int i = 0; i < traps.size(); i++) {
			traps.get(i).render(graphicContext);
		}
	}
	
	public void createTrap(double d, double e, Player player) {
		Trap trap = new Trap(d, e, player);
		if (inBounds(trap)) {
			traps.add(trap);
		}
	}
	
	public boolean inBounds(Trap trap) {
		Shape shape = Shape.subtract(trap.getCollisionZone(), map_boundary);
		if (shape.getBoundsInLocal().getWidth() == -1 && shape.getBoundsInLocal().getHeight() == -1) {
			return true;
		}
		return false;
	}
	
	public ArrayList<Trap> getTraps(){
		return traps;
	}
}
