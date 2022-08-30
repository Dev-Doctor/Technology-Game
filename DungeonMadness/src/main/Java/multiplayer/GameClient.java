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
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Java.GamePanel;
import main.Java.entities.PlayerMP;
import main.Java.packets.Packet;
import main.Java.packets.Packet00Login;
import main.Java.packets.Packet01Disconnect;
import main.Java.packets.Packet02Move;

public class GameClient extends Thread {

    private InetAddress ipAddress;
    private DatagramSocket socket;
    private GamePanel gp;

    public GameClient(GamePanel gp, String ip) {
        this.gp = gp;
        try {
            socket = new DatagramSocket();
            ipAddress = InetAddress.getByName(ip);
        } catch (SocketException ex) {
            Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
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
            //System.out.println("Server > " + new String(packet.getData()));
            this.ParsePacket(packet.getData(), packet.getAddress(), packet.getPort());
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
                this.HandleLogin((Packet00Login) packet, address, port);
                break;
            case 1:
                packet = new Packet01Disconnect(data);
                System.out.println("[" + address.getHostAddress() + ":" + port + "]" + ((Packet01Disconnect) packet).getUsername() + " has left...");
                gp.GetWorld().GetCurrentRoom().RemovePlayer(((Packet01Disconnect) packet).getUsername());
                break;
            case 2:
                packet = new Packet02Move(data);
                if (((Packet02Move) packet).getUsername().equals(gp.pl.getUsername())) {
                    break;
                }
                System.out.println(((Packet02Move) packet).getUsername() + " has moved to " + ((Packet02Move) packet).getX() + "," + ((Packet02Move) packet).getY());
                this.HandleMove((Packet02Move) packet);
                break;
        }
    }

    public void sendData(byte[] data) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1234);
        try {
            socket.send(packet);
        } catch (IOException ex) {
            Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void HandleMove(Packet02Move packet) {
        gp.GetWorld().GetCurrentRoom().movePlayer(packet.getUsername(), packet.getX(), packet.getY(), packet.getDirection());
    }

    private void HandleLogin(Packet00Login packet, InetAddress address, int port) {
        System.out.println("[" + address.getHostAddress() + ":" + port + "]" + packet.getUsername() + " has joined...");
        PlayerMP player = new PlayerMP(address, port, gp, packet.getUsername(), packet.getX(), packet.getY(), packet.getDirection());
        gp.GetWorld().GetCurrentRoom().AddEntity(player);
    }

}
