/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package main.Java.packets;

import main.Java.multiplayer.GameClient;
import main.Java.multiplayer.GameServer;

public class Packet00Login extends Packet{
    
    private String username;

    public Packet00Login(byte[] data) {
        super(00);
        this.username = readData(data);
    }
    
    public Packet00Login(String username) {
        super(00);
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
        return ("00" + username).getBytes();
    }

    public String getUsername() {
        return username;
    }
       
}
