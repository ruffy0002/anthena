package resource;

import java.util.ArrayList;
import java.util.Random;

import controls.Control;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Resources {

	private static final Image[] GAME_MAPS = new Image[10];
	private static final Image[] ANTS = new Image[2];
	private static final Image[] CHARACTER_SET = new Image[1];
	private static final Image[] ATTACK_SET = new Image[1];
	public static final Color[] COLORS = new Color[10];

	public static final Image[] CHARACTER_ATTACK_PROFILE = new Image[2];
	private static final Image[] CHARACTER_DEFEND_PROFILE = new Image[1];

	public static final String[] NAMES = new String[10];
	public static final ArrayList<Control> controls = new ArrayList<Control>();

	public Resources() {
		loadControls();
		loadName();
		loadProfiles();
		loadMap();
		loadCharacterSprites();
		loadColors();
	}

	private void loadControls() {
		// will change to setting bindings.
		Control c1 = new Control(KeyCode.LEFT, KeyCode.UP, KeyCode.RIGHT, KeyCode.DOWN);
		controls.add(c1);
		Control c2 = new Control(KeyCode.A, KeyCode.W, KeyCode.D, KeyCode.S);
		controls.add(c2);
		Control c3 = new Control(KeyCode.G, KeyCode.Y, KeyCode.J, KeyCode.H);
		controls.add(c3);
		Control c4 = new Control(KeyCode.L, KeyCode.P, KeyCode.QUOTE, KeyCode.SEMICOLON);
		controls.add(c4);
	}

	private void loadName() {
		NAMES[0] = "RainbowUnicorn";
		NAMES[1] = "BlackFish";
		NAMES[2] = "SaltyDog";
		NAMES[3] = "MummyBoy";
		NAMES[4] = "DadMistress";
		NAMES[5] = "TwerkKing";
		NAMES[6] = "SmellyButt";
		NAMES[7] = "FacePalm";
		NAMES[8] = "BlindBat";
		NAMES[9] = "HotCream";
	}

	public String[] getNames() {
		return NAMES;
	}

	public void loadMap() {
		Resources.GAME_MAPS[0] = new Image("sprite/tempGameMap.png");
		Resources.GAME_MAPS[1] = null;
	}

	public Image getGameMap(int index) {
		return GAME_MAPS[index];
	}

	public void loadProfiles() {
		CHARACTER_ATTACK_PROFILE[0] = new Image("sprite/swordmanProfile.png");
		CHARACTER_ATTACK_PROFILE[1] = new Image("sprite/antProfile.png");
	}

	public void loadCharacterSprites() {
		CHARACTER_SET[0] = new Image("sprite/characterSwordMan.png");

		ATTACK_SET[0] = new Image("sprite/attackPattern1.png");

		ANTS[0] = new Image("sprite/ant.png");
		ANTS[1] = new Image("sprite/ant2.png");
	}

	public static Image getCharacterImage(int index) {
		return ANTS[index];
	}

	public static Image getcharacterSet(int index) {
		return CHARACTER_SET[index];
	}

	public static Image getAttackSet(int index) {
		return ATTACK_SET[index];
	}

	public void loadColors() {
		COLORS[0] = new Color(1, 0, 0, 1);
		COLORS[1] = new Color(0, 1, 0, 1);
		COLORS[2] = new Color(0, 0, 1, 1);
		COLORS[3] = new Color(1, 1, 0, 1);
		COLORS[4] = new Color(1, 0, 1, 1);
		COLORS[5] = new Color(0, 1, 1, 1);
		COLORS[6] = new Color(0.8, 0.2, 0.2, 1);
		COLORS[7] = new Color(0.2, 0.8, 0.2, 1);
		COLORS[8] = new Color(0.2, 0.2, 0.8, 1);
		COLORS[9] = new Color(0.4, 0.4, 0.4, 1);
	}

}
