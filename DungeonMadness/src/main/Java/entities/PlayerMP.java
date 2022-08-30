/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Java.entities;

import java.net.InetAddress;
import main.Java.GamePanel;
import main.Java.KeyHandler;
import main.Java.packets.Packet02Move;

public class PlayerMP extends Player {

    private InetAddress ip;
    private int port;

    public PlayerMP(InetAddress ip, int port, GamePanel gp, KeyHandler keyHandler, String username) {
        super(gp, keyHandler, username);
        this.ip = ip;
        this.port = port;
    }

    public PlayerMP(InetAddress ip, int port, GamePanel gp, String username, int x, int y, String direction) {
        super(gp, null, username);
        this.ip = ip;
        this.port = port;
        this.position[0] = x;
        this.position[1] = y;
        this.direction = direction;
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    @Override
    public void update() {

        collisionOn = false;
        int x = 0;
        int y = 0;
        boolean input = false;

        if (keyHandler != null) {
            input = true;
        }

        if (input) {
            gp.collisionChecker.checkTile(this);

            int enemyIndex = gp.collisionChecker.checkEntity(this, gp.GetWorld().GetCurrentRoom().GetEnemies()); //ENEMIES
            contactEnemy(enemyIndex);

            if (collisionOn == false) {
                if (keyHandler.wPressed) {
                    y -= speed;
                    direction = "up";
                }
                if (keyHandler.sPressed) {
                    y += speed;
                    direction = "down";
                }
                if (keyHandler.aPressed) {
                    x -= speed;
                    direction = "left";
                }
                if (keyHandler.dPressed) {
                    x += speed;
                    direction = "right";
                }
            }

            if (x != 0 || y != 0) {
                move(x, y);
                Packet02Move packet = new Packet02Move(Username, position[0], position[1], direction);
                packet.writeData(gp.socketClient);
            } else {
                SpriteNumber = 1;
                SpriteCounter = 0;
            }

        }

        SpriteCounter++;

        if (SpriteCounter
                > 10) {
            if (SpriteNumber == 1) {
                SpriteNumber = 2;
            } else if (SpriteNumber == 2) {
                SpriteNumber = 1;
            }
            SpriteCounter = 0;
        }

    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void move(int x, int y) {
        position[0] += x;
        position[1] += y;
    }

}
