package connection;

import logic.LogicMain;

public class SocketInterface {
    private static hostRoomThread roomThread;
    private static IpReplyThread discoveryThread;
    private static final int PORT_NO = 1356;
    private static final int BROADCAST_PORT_NO = 1355;
    
    public static hostRoomThread broadcastRoom(LogicMain logicMain) {
        roomThread = new hostRoomThread(logicMain, PORT_NO);
        discoveryThread = new IpReplyThread(BROADCAST_PORT_NO);
        Thread thread = new Thread(roomThread);
        Thread dthread = new Thread(discoveryThread);
        thread.start();
        dthread.start();
        return roomThread;
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