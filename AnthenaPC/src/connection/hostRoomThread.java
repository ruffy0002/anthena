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
import com.main.anthenaandroid.GameMessagePacket;

public class hostRoomThread implements Runnable {
    private static final int PLAYER_NO = 8;
    private static final int SOCKET_NOT_REPEATED = -1;
    
	public int portNo;
	private boolean running = true;
	private LogicMain logicMain;
	PlayerThread[] playerThreads;
	private int currentPlayerNo = 0;
	
	private boolean gameStarted = false;

	public hostRoomThread(LogicMain logicMain, int portNo) {
		this.logicMain = logicMain;
		this.portNo = portNo;
		playerThreads = new PlayerThread[PLAYER_NO];
	}

	/**
	 * Sends game start packet to all connected clients
	 */
	public void sendStartGame () {
	    for(int i = 0; i < PLAYER_NO; i++) {
	        if(playerThreads[i] != null) {
	            playerThreads[i].sendGameStart();
	        }
	    }
	    gameStarted = true;
	}
	
	
	/**
     * Checks if a particular player is ready
     * 
     * @param playerNo
     * @return 0 if not ready, 1 if ready, -1 if this player number does not exist
     */
    public int isPlayerReady(int playerNo) {
        if(playerThreads[playerNo] != null) {
            if( playerThreads[playerNo].checkReady() == false) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return -1;
        }
    }
    
    /**
     * Checks if a particular player has loaded into the game
     * 
     * @param playerNo
     * @return 0 if player has not loaded into the game, 1 if player has loaded, -1 if this player number does not exist
     */
    public int hasPlayerLoadedIntoGame(int playerNo) {
        if(playerThreads[playerNo] != null) {
            if( playerThreads[playerNo].checkLoadedIntoGame() == false) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return -1;
        }
    }
	
    /**
     * Checks if all the players connected has loaded into the game
     * 
     * @return true if all have loaded, false otherwise
     */
    public boolean hasAllPlayersLoadedIntoGame () {
        for(int i = 0; i < PLAYER_NO; i++) {
            if(hasPlayerLoadedIntoGame(i) == 0) {
                return false;
            }
        }
        return true;
    }
    
	/**
	 * Checks if all the players connected are readied up
	 * 
	 * @return true if all are ready, false otherwise
	 */
	public boolean arePlayersReady () {
	    for(int i = 0; i < PLAYER_NO; i++) {
	        if(isPlayerReady(i) == 0) {
	            return false;
	        }
	    }
	    return true;
	}
	
	/**
	 * Checks if a player is connected
	 * 
	 * @param player - int corresponding to the player's number
	 * @return -1 if player does not exist
	 *         1 if player is connected
	 *         0 if player is disconnected
	 */
	public int checkPlayerConnection (int player) {
	    if(playerThreads[player] != null) {
	        if( playerThreads[player].checkConnection() == false) {
	            return 0;
	        } else {
	            return 1;
	        }
	    } else {
	        return -1;
	    }
	}
	
	public boolean sendData (GamePacket data, int player) {
        if(playerThreads[player] != null) {
            return playerThreads[player].sendData(data);
        } else {
            return false;
        }
    }
	
	public boolean sendGameMessage (GameMessagePacket data, int player) {
        if(playerThreads[player] != null) {
            return playerThreads[player].sendGameMessage(data);
        } else {
            return false;
        }
    }
	
	/**
	 * Deletes a disconnected existing player
	 * 
	 * @param playerNo - Array slot of player to be deleted
	 * @return -1 - Player is not disconnected
	 *         -2 - Player number invalid
	 *         1 - Player deletion successful
	 */
	public int deleteDisconnectedPlayerThread (int playerNo) {
	    if(playerThreads[playerNo] == null) {
	        return -2;
	    }
	    if(playerThreads[playerNo].checkConnection() == false) {
	        playerThreads[playerNo].stop();
	        playerThreads[playerNo] = null;
	        currentPlayerNo--;
	        return 1;
	    } else {
	        return -1;
	    }
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
            int repeatedSocketNo = checkRepeatedIp(temSocket);
            if (repeatedSocketNo != SOCKET_NOT_REPEATED) {
                refreshPlayerThread(repeatedSocketNo, temSocket);
            } else {
                createPlayerThread(temSocket);
            }
        }
    }

    private boolean refreshPlayerThread(int repeatedSocketNo, Socket socket) {
        
        if( playerThreads[repeatedSocketNo] != null) {
            playerThreads[repeatedSocketNo].setNewSocket(socket);
            if(gameStarted) {
                if(sendDirectGameStart(repeatedSocketNo)) {
                    System.out.println("Sent forced game start to player " + repeatedSocketNo);
                }
            }
            
            //#FORSHAN
            //logicMain.NOTIFYPLAYERJOINED(repeatedSocketNo); repeatedSocketNo is the player number
            return true;
        } else {
            return false;
        }
    }
    
    public boolean sendDirectGameStart (int playerNo) {
        if(playerThreads[playerNo] != null) {
            return playerThreads[playerNo].sendGameAlreadyStarted();
        } else {
            return false;
        }
    }
    
    private void createPlayerThread(Socket temSocket) {
        for(int i = 0; i < PLAYER_NO; i++) {
            if(playerThreads[i] == null) {
                playerThreads[i] = new PlayerThread(temSocket.getInetAddress(), i, temSocket, logicMain);
                Thread thread = new Thread(playerThreads[i]);
                thread.start();
                currentPlayerNo++;
                if(gameStarted) {
                    sendDirectGameStart(i);
                }
                //#FORSHAN
                //logicMain.NOTIFYPLAYERJOINED(i); i is the player number
                break;
            }
        }
    }

    private int checkRepeatedIp(Socket temSocket) {
        for(int i = 0; i < PLAYER_NO; i++) {
            if(playerThreads[i] != null) {
                if(playerThreads[i].getIp().equals(temSocket.getInetAddress())) {
                    return i;
                }
            }
        }
        return SOCKET_NOT_REPEATED;
    }
}