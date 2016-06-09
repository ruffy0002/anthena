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
import com.main.anthenaandroid.GamePacket;

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
        _player =_logicMain.addNewAttacker();
    }
    
    /**
     * Checks if the player is still connected, updates every 500ms
     * @return true if player is connected, false otherwise
     */
    public boolean checkConnection () {
        return connected;
    }
    
    public void setNewSocket (Socket socket) {
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
    
    private void sendDataToProgram(GamePacket data) {
        System.out.println("Player " + _playerNo + "[X: " + data.getX() + ", Y: " + data.getY() + "]");
        if (_logicMain != null) {
            _logicMain.addAttack(data.getX(), data.getY());
        }
    }
    
    @Override
    public void run() {
        Object object;
        
        while(_running) {
            try {
                object = dataInputStream.readObject();
                if (object instanceof GamePacket) {
                    GamePacket p = (GamePacket) object;
                    sendDataToProgram(p);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SocketTimeoutException ex) {
                
            } catch (IOException e) {
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