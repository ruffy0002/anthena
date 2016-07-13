package connection;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import com.main.anthenaandroid.BroadcastPacket;
import com.main.anthenaandroid.GameMessagePacket;
import com.main.anthenaandroid.GamePacket;
import com.main.anthenaandroid.PingPacket;

import entity.Player;
import logic.LogicMain;

public class PlayerThread implements Runnable {
	private boolean _running = true;

	private InetAddress _ipAddress;
	private int _playerNo;
	private Socket _socket;
	private LogicMain _logicMain;
	private Player _player;

	private ObjectInputStream dataInputStream = null;
	private ObjectOutputStream dataOutputStream = null;

	boolean connected = true;
	boolean isReady = false;
	boolean isStarted = false;

	private int packetCount = 0;
	public PlayerThread(InetAddress ipAddress, int playerNo, Socket socket, LogicMain logicMain) {
		_ipAddress = ipAddress;
		_playerNo = playerNo;
		_socket = socket;
		_logicMain = logicMain;

		try {
			_socket.setSoTimeout(500);
			dataInputStream = new ObjectInputStream(socket.getInputStream());
			dataOutputStream = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Player " + playerNo + "(" + ipAddress + ") joined the game");
		_player = _logicMain.addNewPlayer(this);
	}

	/**
	 * Checks if the player is still connected, updates every 500ms
	 * 
	 * @return true if player is connected, false otherwise
	 */
	public boolean checkConnection() {
		return connected;
	}

	/**
	 * Checks if the player has sent a ready packet
	 * 
	 * @return true if the player is ready, false otherwise
	 */
	public boolean checkReady() {
		return isReady;
	}

	/**
	 * Checks if the player has loaded into the game
	 * 
	 * @return true if the player has loaded, false otherwise
	 */
	public boolean checkLoadedIntoGame() {
		return isStarted;
	}

	public void setNewSocket(Socket socket) {
		_socket = socket;

		try {
			_socket.setSoTimeout(500);
			dataInputStream = new ObjectInputStream(socket.getInputStream());
			dataOutputStream = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		connected = true;
		System.out.println("Player " + _playerNo + "(" + _ipAddress + ") reconnected");
	}

	public InetAddress getIp() {
		return _ipAddress;
	}

	public int getPlayerNo() {
		return _playerNo;
	}

	public void stop() {
		_running = false;
	}

	private void verifyData(GamePacket data) {
		if (data.getX() == -1000 && data.getY() == -1000) {
			// Initialisation data
			System.out.println("type = " + data.getType());
			_logicMain.updatePlayerType(_player.getPlayer_id(), data.getType());
		} else {
			sendDataToProgram(data);
		}
	}

	private void sendDataToProgram(GamePacket data) {
		if (data.getType() == GamePacket.TYPE_STOMPER) {
		    if(data.getX() != -1000) {
		        packetCount++;
		    }
			System.out.println("("+ packetCount + ")Player " + _playerNo + " stomps [X: " + data.getX() + ", Y: " + data.getY() + "]");
			if (_logicMain != null) {
				_logicMain.executeAttack(_player, data.getX(), data.getY());
			}
		} else if (data.getType() == GamePacket.TYPE_RUNNER) {
		    if(data.getX() != -1000) {
                packetCount++;
            }
		    System.out.println("("+ packetCount + ")Player " + _playerNo + " running to [X: " + data.getX() + ", Y: " + data.getY() + "]");
			if (_logicMain != null) {
				_logicMain.updatePlayerPosition(_player, data.getX(), data.getY());
			}
		} else if (data.getType() == GamePacket.TYPE_POSITIONUPDATE) {
			if (_logicMain != null) {
				// Fill logic main portion here
			}
		} else if (data.getType() == GamePacket.TYPE_READY) {
			isReady = true;
			if (_logicMain != null) {
				// Fill logic main portion here
				_player.setStatus(Player.READY);
				_logicMain.updatePlayerStatus(_player);
			}
		} else if (data.getType() == GamePacket.TYPE_UNREADY) {
			isReady = false;
			if (_logicMain != null) {
				_player.setStatus(Player.NOT_READY);
				_logicMain.updatePlayerStatus(_player);
			}
		} else if (data.getType() == GamePacket.TYPE_GAMESTART) {
			System.out.println("Player " + _playerNo + " loaded");
			isStarted = true;
			if (_logicMain != null) {
				// Fill logic main portion here
				_player.setStatus(Player.READY_TO_GO);
				// _logicMain.updatePlayerStatus(_player);
			}
		} else if(data.getType() == GamePacket.TYPE_SKILL) {
		    System.out.println("Player " + _playerNo +" used skill");
		    //This is the skill type
		    data.getSkill();
		} else {
			System.out.println("Data sent from client not recognized!");
		}
	}

	public void sendGameStart() {
		GamePacket gameStartPacket = new GamePacket(0, 0, GamePacket.TYPE_GAMESTART);
		sendData(gameStartPacket);
	}

	/**
	 * Used for vampire mode to change a runner to a stomper
	 * 
	 * @return
	 */
	public boolean sendPlayerTypeChange() {
		GamePacket gameStartPacket = new GamePacket(0, 0, GamePacket.TYPE_CHANGEPLAYERTYPE);
		return sendData(gameStartPacket);
	}

	/**
	 * Sends a message to the player's mobile screen
	 * @param packet
	 * @return
	 */
	public boolean sendGameMessage(GameMessagePacket packet) {
	    return sendData(packet);
	}
	
	public boolean sendData(Object obj) {
		try {
			dataOutputStream.writeObject(obj);
			return true;
		} catch (IOException ex) {
			return false;
		}
	}

	public boolean sendPing() {
		PingPacket obj = new PingPacket();
		try {
			dataOutputStream.writeObject(obj);
			return true;
		} catch (IOException ex) {
			return false;
		}
	}

	@Override
	public void run() {
		Object object;

		while (_running) {
			try {
				object = dataInputStream.readObject();
				if (object instanceof GamePacket) {
					GamePacket p = (GamePacket) object;
					verifyData(p);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SocketTimeoutException ex) {

			} catch (IOException e) {
				connected = false;
			}
			if (sendPing() == false) {
				connected = false;
			}
		}

		closeSocket();
	}

	private void closeSocket() {
		if (_socket != null) {
			try {
				_socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (dataInputStream != null) {
			try {
				dataInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (dataOutputStream != null) {
			try {
				dataOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}