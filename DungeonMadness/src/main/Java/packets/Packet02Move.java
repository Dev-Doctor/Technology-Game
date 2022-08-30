/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Java.packets;

import main.Java.multiplayer.GameClient;
import main.Java.multiplayer.GameServer;

public class Packet02Move extends Packet {

    private String username;
    private int x, y;
    private String direction;

    public Packet02Move(byte[] data) {
        super(02);
        String[] dataArray = readData(data).split(",");
        this.username = dataArray[0];
        this.x = Integer.parseInt(dataArray[1]);
        this.y = Integer.parseInt(dataArray[2]);
        this.direction = dataArray[3];
    }

    public Packet02Move(String username, int x, int y, String direction) {
        super(02);
        this.username = username;
        this.x = x;
        this.y = y;
        this.direction = direction;
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
        return ("02" + username + "," + x + "," + y + "," + direction).getBytes();
    }

    public String getUsername() {
        return username;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getDirection() {
        return direction;
    }

}
