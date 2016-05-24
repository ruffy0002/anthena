package connection;

import java.io.DataInputStream; 
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.main.anthenaandroid.GamePacket;

import logic.LogicMain;

import com.main.anthenaandroid.BroadcastPacket;

public class hostRoomThread implements Runnable {

	public static final int PORT_NO = 1356;
	private boolean running = true;
	private LogicMain logicMain;

	public hostRoomThread(LogicMain logicMain) {
		this.logicMain = logicMain;
	}

	public void run() {
		listenAtPort();
	}

	public void stop() {
		running = false;
	}

	public void sendDataToProgram(GamePacket data) {
		System.out.println("X: " + data.getX() + ", Y: " + data.getY());
		if (logicMain != null) {
			logicMain.addAttack(data.getX(), data.getY());
		}
	}

	private void listenAtPort() {
		ServerSocket serverSocket = null;
		Socket socket = null;
		ObjectInputStream dataInputStream = null;
		ObjectOutputStream dataOutputStream = null;

		try {
			serverSocket = new ServerSocket(PORT_NO);
			System.out.println("Listening :" + PORT_NO);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (running) {
			try {
				socket = serverSocket.accept();
				dataInputStream = new ObjectInputStream(socket.getInputStream());
				dataOutputStream = new ObjectOutputStream(socket.getOutputStream());
				System.out.println("ip: " + socket.getInetAddress());
				Object object = dataInputStream.readObject();
				if (object instanceof GamePacket) {
					GamePacket p = (GamePacket) object;
					sendDataToProgram(p);
				} else if (object instanceof BroadcastPacket) {
					System.out.println("Broadcast recieved");
				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				if (socket != null) {
					try {
						socket.close();
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

		if (socket != null) {
			try {
				socket.close();
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