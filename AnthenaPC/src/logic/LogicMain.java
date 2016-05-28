package logic;

import connection.SocketInterface;

public class LogicMain {

	private GameLoop gameLoop;

	public LogicMain(GameLoop gameLoop) {
		this.gameLoop = gameLoop;
		SocketInterface.broadcastRoom(this);
	}

	public void hostGame() {

	}

	public void addAttack(float x, float y) {
		gameLoop.createAttack(x,y);
	}
}
