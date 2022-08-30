/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Java.multiplayer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Java.GamePanel;
import main.Java.entities.Entity;
import main.Java.entities.PlayerMP;
import main.Java.packets.Packet;
import main.Java.packets.Packet00Login;
import main.Java.packets.Packet01Disconnect;
import main.Java.packets.Packet02Move;

public class GameServer extends Thread {

    private DatagramSocket socket;
    private GamePanel gp;
    private List<PlayerMP> connectedPlayers = new ArrayList<PlayerMP>();

    public GameServer(GamePanel gp) {
        this.gp = gp;
        try {
            socket = new DatagramSocket(1234);
        } catch (SocketException ex) {
            Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException ex) {
                Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.ParsePacket(packet.getData(), packet.getAddress(), packet.getPort());
//            String messaggio = new String(packet.getData());
//            System.out.println("Client[" + packet.getAddress().getHostAddress() + ":" + packet.getPort() + "]> " + messaggio);
//            if (messaggio.trim().equals("ping")) {
//                sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
//            }
        }
    }

    public void sendData(byte[] data, InetAddress ip, int port) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
        try {
            socket.send(packet);
        } catch (IOException ex) {
            Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendDataToAllClients(byte[] data) {
        for (PlayerMP p : connectedPlayers) {
            sendData(data, p.getIp(), p.getPort());
        }
    }

    private void ParsePacket(byte[] data, InetAddress address, int port) {
        String message = new String(data).trim();
        int id;
        try {
            id = Integer.parseInt(message.substring(0, 2));
        } catch (NumberFormatException e) {
            id = -1;
        }
        Packet packet = null;
        switch (id) {
            default:
            case -1:
                break;
            case 0:
                packet = new Packet00Login(data);
                System.out.println("[" + address.getHostAddress() + ":" + port + "]" + ((Packet00Login) packet).getUsername() + " has connected...");
                PlayerMP player = new PlayerMP(address, port, gp, ((Packet00Login) packet).getUsername(), 100, 100, "down");
                this.addConnection(player, (Packet00Login) packet);
                break;
            case 1:
                packet = new Packet01Disconnect(data);
                System.out.println("[" + address.getHostAddress() + ":" + port + "]" + ((Packet01Disconnect) packet).getUsername() + " has left...");
                this.removeConnection((Packet01Disconnect) packet);
                break;
            case 2:
                packet = new Packet02Move(data);
                System.out.println(((Packet02Move) packet).getUsername() + " has moved to " + ((Packet02Move) packet).getX() + "," + ((Packet02Move) packet).getY());
                this.HandleMove((Packet02Move) packet);
                break;
        }
    }

    public void addConnection(PlayerMP player, Packet00Login packet) {
        boolean alreadyConnected = false;
        for (PlayerMP p : this.connectedPlayers) {
            if (player.getUsername().equalsIgnoreCase(p.getUsername())) {
                if (p.getIp() == null) {
                    p.setIp(player.getIp());
                }
                if (p.getPort() == -1) {
                    p.setPort(player.getPort());
                }
                alreadyConnected = true;
            } else {
                this.sendData(packet.getData(), p.getIp(), p.getPort());

                packet = new Packet00Login(p.getUsername(), p.GetX(), p.GetY(), p.getDirection());
                this.sendData(packet.getData(), player.getIp(), player.getPort());
            }
        }
        if (!alreadyConnected) {
            this.connectedPlayers.add(player);
            //packet.writeData(this);
        }
    }

    public void removeConnection(Packet01Disconnect packet) {
        this.connectedPlayers.remove(getPlayerMPindex(packet.getUsername()));
        packet.writeData(this);
    }

    public PlayerMP getPlayerMP(String username) {
        for (PlayerMP p : this.connectedPlayers) {
            if (p.getUsername().equalsIgnoreCase(username)) {
                return p;
            }
        }
        return null;
    }

    public int getPlayerMPindex(String username) {
        int i = 0;
        for (PlayerMP p : this.connectedPlayers) {
            if (p.getUsername().equalsIgnoreCase(username)) {
                break;
            }
            i++;
        }
        return i;
    }

    private void HandleMove(Packet02Move packet) {
        if (getPlayerMP(packet.getUsername()) == null) {
            return;
        }
        int index = getPlayerMPindex(packet.getUsername());
        this.connectedPlayers.get(index).position[0] = packet.getX();
        this.connectedPlayers.get(index).position[1] = packet.getY();
        packet.writeData(this);
    }

}
