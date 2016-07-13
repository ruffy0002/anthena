package resource;

import java.util.ArrayList;
import java.util.Random;

import controls.Control;
import entity.Attack;
import entity.Character.State;
import entity.CharacterSwordMan;
import entity.Collectable;
import entity.Trap;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Resources {

	public enum CharacterType {
		SWORDMAN;
	}

	private static final Image[] GAME_MAPS = new Image[10];
	private static final Image[] ANTS = new Image[2];

	private static final Image[] SWORD_MAN = new Image[2];
	private static final Image[] CHARACTER_SET_DEATH = new Image[1];
	private static final Image[] DEATH = new Image[1];

	private static final Image[] ATTACK_SET = new Image[5];
	public static final Color[] COLORS = new Color[10];
	public static final ColorAdjust[] COLOR_ADJUST = new ColorAdjust[10];

	public static final Image[] CHARACTER_ATTACK_PROFILE = new Image[2];
	private static final Image[] CHARACTER_DEFEND_PROFILE = new Image[1];
	private static final Image HEART = new Image("sprite/heart.png");

	private static final Image[] COLLECTABLE = new Image[1];

	public static final String[] NAMES = new String[10];
	public static final ArrayList<Control> controls = new ArrayList<Control>();

	private static int colorCounter = 0;
	private static int nameCounter = 0;
	private static int colorAdjustCounter = 0;

	public Resources() {
		loadControls();
		loadColorAdjust();
		loadName();
		loadProfiles();
		loadMap();
		loadCharacterSprites();
		loadColors();
		loadETC();
		CharacterSwordMan.initMain();
		Collectable.initMain();
		Attack.initMain();
		Trap.initMain();
	}

	private void loadETC() {
		COLLECTABLE[0] = new Image("sprite/cubesheet.png");
	}

	private void loadControls() {
		// will change to setting bindings.
		Control c1 = new Control(KeyCode.LEFT, KeyCode.UP, KeyCode.RIGHT, KeyCode.DOWN, KeyCode.SPACE);
		controls.add(c1);
		Control c2 = new Control(KeyCode.A, KeyCode.W, KeyCode.D, KeyCode.S, KeyCode.Q);
		controls.add(c2);
		Control c3 = new Control(KeyCode.G, KeyCode.Y, KeyCode.J, KeyCode.H, KeyCode.T);
		controls.add(c3);
		Control c4 = new Control(KeyCode.L, KeyCode.P, KeyCode.QUOTE, KeyCode.SEMICOLON, KeyCode.O);
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
		shuffle(NAMES);
	}

	public static String getNextName() {
		return NAMES[nameCounter++ % NAMES.length];
	}

	public static Image getHeart() {
		return HEART;
	}

	public void loadMap() {
		Resources.GAME_MAPS[0] = new Image("sprite/game_map_1.jpg");
		Resources.GAME_MAPS[1] = null;
	}

	public Image getGameMap(int index) {
		return GAME_MAPS[index];
	}

	public void loadProfiles() {
		CHARACTER_ATTACK_PROFILE[0] = new Image("sprite/swordmanProfile.png");
		CHARACTER_ATTACK_PROFILE[1] = new Image("sprite/attackProfile.png");
	}

	public void loadCharacterSprites() {
		SWORD_MAN[0] = new Image("sprite/swordmanMoving.png");
		SWORD_MAN[1] = new Image("sprite/swordmanIdle.png");

		CHARACTER_SET_DEATH[0] = new Image("sprite/swordmandefeated.png");
		DEATH[0] = new Image("sprite/swordmandeath.png");

		ATTACK_SET[0] = new Image("sprite/attackPattern_master.png");
		ATTACK_SET[1] = new Image("sprite/attackPattern_blue.png");
		ATTACK_SET[2] = new Image("sprite/attackPattern_green.png");
		ATTACK_SET[3] = new Image("sprite/attackPattern_yellow.png");
		ATTACK_SET[4] = new Image("sprite/attackPattern_red.png");

		ANTS[0] = new Image("sprite/ant.png");
		ANTS[1] = new Image("sprite/ant2.png");
	}

	public static Image getCharacterImage(int index) {
		return ANTS[index];
	}

	public static Image getAttackSet(int index) {
		return ATTACK_SET[index];
	}

	public static Image getDeathImage(int index) {
		return DEATH[index];
	}

	public static Image getDefeatedImage(int index) {
		return CHARACTER_SET_DEATH[index];
	}

	public static Image getCollectable(int index) {
		return COLLECTABLE[index];
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
		shuffle(COLORS);
	}

	public static Color getNextColor() {
		return COLORS[colorCounter++ % COLORS.length];
	}

	public void loadColorAdjust() {
		COLOR_ADJUST[0] = new ColorAdjust(-1, 1, 0.3, 0.4);
		COLOR_ADJUST[1] = new ColorAdjust(-0.2, 1, 0.3, 0.4);
		COLOR_ADJUST[2] = new ColorAdjust(-0.4, 1, 0.2, 0.4);
		COLOR_ADJUST[3] = new ColorAdjust(-0.6, 1, 0.2, 0.4);
		COLOR_ADJUST[4] = new ColorAdjust(-0.8, 1, 0.4, 0.4);
		COLOR_ADJUST[5] = new ColorAdjust(0, 1, 0.4, 0.4);
		COLOR_ADJUST[6] = new ColorAdjust(0.2, 1, 0.4, 0.4);
		COLOR_ADJUST[7] = new ColorAdjust(0.4, 1, 0.4, 0.4);
		COLOR_ADJUST[8] = new ColorAdjust(0.6, 1, 0.4, 0.4);
		COLOR_ADJUST[9] = new ColorAdjust(0.8, 1, 0.4, 0.4);
		shuffle(COLOR_ADJUST);
	}

	public static ColorAdjust getNextColorAdjust() {
		return COLOR_ADJUST[colorAdjustCounter++ % COLOR_ADJUST.length];
	}

	public static Image getAnimationFrame(CharacterType type, State state) {
		if (type == CharacterType.SWORDMAN) {
			if (state == State.IDLE) {
				return SWORD_MAN[1];
			} else if (state == State.MOVING) {
				return SWORD_MAN[0];
			}
		}
		return null;
	}

	public void shuffle(Object[] array) {
		// shuffle colors so no need get random on init
		for (int i = 0; i < array.length; i++) {
			Random r = new Random();
			int x = r.nextInt(array.length);
			int y = r.nextInt(array.length);
			Object temp = array[x];
			array[x] = array[y];
			array[y] = temp;
		}
	}

}
