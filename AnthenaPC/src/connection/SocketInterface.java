package connection;

import logic.LogicMain;

public class SocketInterface {
    private static hostRoomThread roomThread;
    public static void broadcastRoom(LogicMain logicMain) {
        roomThread = new hostRoomThread(logicMain);
        Thread thread = new Thread(roomThread);
        thread.start();
    }
    
    public static void stopBroadCast() {
        if(roomThread != null) {
            roomThread.stop();
        }
    }
    
    public static void main (String[] args) {
        broadcastRoom(null);
    }
}