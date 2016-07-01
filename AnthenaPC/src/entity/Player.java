package entity;

import java.util.ArrayList;

import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;

import connection.PlayerThread;
import controls.Control;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;
import logic.LogicMain;

public class Player {

	public boolean isMobile;
	public static final int TYPE_RUNNER = 1;
	public static final int TYPE_STOMPPER = 0;
	
	public static final int NOT_READY = 0;
	public static final int READY = 1;
	public static final int READY_TO_GO = 2;

	private static int totalPlayer = 0;
	private static ArrayList<Player> all_players_list = new ArrayList<Player>();

	private int player_id;
	private int playerType = -1;
	private int status;

	private PlayerThread thread;
	protected Control control;
	private Character character;
	private String name;
	private Color color;
	private int score;
	private Label nameLabel;

	private LogicMain logicMain;
	private Sprite sprite;
	private int health;

	private ColorAdjust colorAdjust;

	public Player(Control control, String name, Color color, ColorAdjust colorAdjust, PlayerThread thread,
			LogicMain logicMain, Boolean isMobile) {
		Player.all_players_list.add(this);
		this.player_id = totalPlayer++;

		this.isMobile = isMobile;
		this.thread = thread;
		this.logicMain = logicMain;
		this.name = name;
		this.control = control;
		this.color = color;
		this.status = NOT_READY;

		health = 3;
		nameLabel = new Label(name);
		FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
		double w = fontLoader.computeStringWidth(nameLabel.getText(), nameLabel.getFont());
		nameLabel.setMinWidth(w);

		this.colorAdjust = colorAdjust;
	}

	public Control getControl() {
		return control;
	}

	public void setControl(Control control) {
		this.control = control;
	}

	public String getName() {
		return name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void takeDamage() {
		this.health--;
	}

	public int getPlayerType() {
		return playerType;
	}

	public void setPlayerType(int playerType) {
		this.playerType = playerType;
	}

	public int getPlayer_id() {
		return player_id;
	}

	public Character createSprite() {
		character = new CharacterSwordMan(this);
		character.setControl(control);
		return character;
	}
	
	public Character getCharacter(){
		return character;
	}

	public void addScore(int i) {
		score += i;
	}

	public int getScore() {
		return score;
	}

	public Label getNameLabel() {
		return nameLabel;
	}

	public boolean isConnected() {
		if (this.thread == null) {
			return true;
		}
		return this.thread.checkConnection();
	}

	public static ArrayList<Player> getAll_players_list() {
		return all_players_list;
	}

	public ColorAdjust getPaintEffect() {
		return colorAdjust;
	}

}