package connection;

import logic.LogicMain;

public class SocketInterface {
    private static hostRoomThread roomThread;
    private static IpReplyThread discoveryThread;
    private static final int PORT_NO = 1356;
    
    public static void broadcastRoom(LogicMain logicMain) {
        roomThread = new hostRoomThread(logicMain, PORT_NO);
        discoveryThread = new IpReplyThread(PORT_NO);
        Thread thread = new Thread(roomThread);
        Thread dthread = new Thread(discoveryThread);
        thread.start();
        dthread.start();
    }
    
    public static void stopBroadCast() {
        if(discoveryThread != null) {
            discoveryThread.stop();
        }
    }
    
    public static void stopNewConnection() {
        if(roomThread != null) {
            roomThread.stop();
        }
    }
    
    public static void main (String[] args) {
        broadcastRoom(null);
    }
}