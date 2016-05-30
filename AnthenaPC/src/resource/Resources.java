package resource;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Resources {

	private static Image[] gameMap = new Image[10];
	private static Image[] ant = new Image[2];
	private static Image[] characterSet = new Image[1];
	private static Color[] colors = new Color[10];

	public void loadMap() {
		Resources.gameMap[0] = new Image("sprite/tempGameMap.png");
		Resources.gameMap[1] = null;
	}

	public Image getGameMap(int index) {
		return gameMap[index];
	}

	public void loadCharacterSprites() {
		characterSet[0] = new Image("sprite/characterSwordMan.png");
		ant[0] = new Image("sprite/ant.png");
		ant[1] = new Image("sprite/ant2.png");
	}

	public static Image getCharacterImage(int index) {
		return ant[index];
	}
	
	public static Image getcharacterSet(int index) {
		return characterSet[index];
	}

	public void loadColors() {
		colors[0] = new Color(1, 0, 0, 1);
		colors[1] = new Color(0, 1, 0, 1);
		colors[2] = new Color(0, 0, 1, 1);
		colors[3] = new Color(1, 1, 0, 1);
		colors[4] = new Color(1, 0, 1, 1);
		colors[5] = new Color(0, 1, 1, 1);
		colors[6] = new Color(0.8, 0.2, 0.2, 1);
		colors[7] = new Color(0.2, 0.8, 0.2, 1);
		colors[8] = new Color(0.2, 0.2, 0.8, 1);
		colors[9] = new Color(0.4, 0.4, 0.4, 1);
	}

	public static Color getRandomColor() {
		Random random = new Random();
		return colors[random.nextInt(10)];
	}

}
