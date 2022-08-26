/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package main.Java.packets;

import main.Java.multiplayer.GameClient;
import main.Java.multiplayer.GameServer;

public abstract class Packet {
    //INVALID -1        LOGIN 00
    //DISCONNECT 01     MOVE 02
    byte packetId;       

    public Packet(int packetId) {
        this.packetId = (byte)packetId;
    }

    public byte getPacketId() {
        return packetId;
    }
    
    public abstract void writeData(GameServer server);
    public abstract void writeData(GameClient client);
    
    public String readData(byte[] data){
        String message = new String(data).trim();
        return message.substring(2);
    }
    
    public abstract byte[] getData();
    
}
