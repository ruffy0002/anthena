package com.main.anthenaandroid;

import java.io.Serializable;

/**
 * Created by ruffy0002_2 on 23/5/2016.
 */
public class BroadcastPacket implements Serializable{
    static final long serialVersionUID = 568750792;
    boolean isServer;

    public BroadcastPacket (boolean isServer) {
        this.isServer = isServer;
    }

    public boolean isServer() {
        return isServer;
    }
}
