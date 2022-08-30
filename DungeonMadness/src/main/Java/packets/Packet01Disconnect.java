/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package main.Java.packets;

import main.Java.multiplayer.GameClient;
import main.Java.multiplayer.GameServer;

public class Packet01Disconnect extends Packet {

    private String username;

    public Packet01Disconnect(byte[] data) {
        super(01);
        this.username = readData(data);
    }
    
    public Packet01Disconnect(String username) {
        super(01);
        this.username = username;
    }

    @Override
    public void writeData(GameClient client) {
       client.sendData(getData());
    }

    @Override
    public void writeData(GameServer server) {
        server.sendDataToAllClients(getData());
    }

    @Override
    public byte[] getData() {
        return ("01" + username).getBytes();
    }

    public String getUsername() {
        return username;
    }
    
}