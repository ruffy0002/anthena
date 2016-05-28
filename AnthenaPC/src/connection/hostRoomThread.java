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
    private static final int PLAYER_NO = 4;
    
	public int portNo;
	private boolean running = true;
	private LogicMain logicMain;
	PlayerThread[] playerThreads;
	private int currentPlayerNo = 0;

	public hostRoomThread(LogicMain logicMain, int portNo) {
		this.logicMain = logicMain;
		this.portNo = portNo;
		playerThreads = new PlayerThread[PLAYER_NO];
	}

	public void run() {
		listenAtPort();
		
	}

	public void stop() {
	    for(int i = 0; i < PLAYER_NO; i++) {
	        if(playerThreads[i] != null) {
	            playerThreads[i].stop();
	        }
	    }
		running = false;
	}

	private void listenAtPort() {
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(portNo);
			System.out.println("Listening :" + portNo);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (running) {
			try {
			    listenForPlayer(serverSocket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

    private void listenForPlayer(ServerSocket serverSocket) throws IOException {
        if(currentPlayerNo < PLAYER_NO) {
            Socket temSocket = serverSocket.accept();
            temSocket.setKeepAlive(true);
            if (checkRepeatedIp(temSocket) ) {
                return;
            } else {
                createPlayerThread(temSocket);
            }
        }
    }

    private void createPlayerThread(Socket temSocket) {
        for(int i = 0; i < PLAYER_NO; i++) {
            if(playerThreads[i] == null) {
                playerThreads[i] = new PlayerThread(temSocket.getInetAddress(), i, temSocket, logicMain);
                Thread thread = new Thread(playerThreads[i]);
                thread.start();
                currentPlayerNo++;
                break;
            }
        }
    }

    private boolean checkRepeatedIp(Socket temSocket) {
        for(int i = 0; i < PLAYER_NO; i++) {
            if(playerThreads[i] != null) {
                if(playerThreads[i].getIp() == temSocket.getInetAddress()) {
                    return true;
                }
            }
        }
        return false;
    }
}