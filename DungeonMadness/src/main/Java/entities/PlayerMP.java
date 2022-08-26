/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package main.Java.entities;

import java.net.InetAddress;
import main.Java.GamePanel;
import main.Java.KeyHandler;

public class PlayerMP extends Player {
    private InetAddress ip;
    private int port;

    public PlayerMP(InetAddress ip, int port, GamePanel gp, KeyHandler keyHandler, String username) {
        super(gp, keyHandler, username);
        this.ip = ip;
        this.port = port;
    }
    
    public PlayerMP(InetAddress ip, int port, GamePanel gp, String username) {
        super(gp, null, username);
        this.ip = ip;
        this.port = port;
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    @Override
    public void update() {
        super.update(); 
    }    

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
    
}
