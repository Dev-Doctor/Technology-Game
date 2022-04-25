/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Java.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import main.Java.GamePanel;
import main.Java.KeyHandler;

/**
 *
 * @author DevDoctor
 */
public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyHandler;

    public Player(GamePanel gp, KeyHandler keyHandler) {
        animations = new BufferedImage[8];
        this.keyHandler = keyHandler;
        this.gp = gp;
        name = "Player";
        
        SetDefaultValues();
        getPlayerImage();
    }

    public void SetDefaultValues() {
        position[0] = 100;
        position[1] = 100;
        direction = "up";
        speed = 4;
        health = 100;
    }

    public void getPlayerImage() {
        try {
            animations[0] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/player/1/up1.png"));
            animations[1] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/player/1/up2.png"));
            animations[2] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/player/1/down1.png"));
            animations[3] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/player/1/down2.png"));
            animations[4] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/player/1/left1.png"));
            animations[5] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/player/1/left2.png"));
            animations[6] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/player/1/right1.png"));
            animations[7] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/player/1/right2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update() {
        if (keyHandler.wPressed) {
            direction = "up";
            position[1] -= speed;
        }
        if (keyHandler.sPressed) {
            direction = "down";
            position[1] += speed;
        }
        if (keyHandler.aPressed) {
            direction = "left";
            position[0] -= speed;
        }
        if (keyHandler.dPressed) {
            direction = "right";
            position[0] += speed;
        }
        if (keyHandler.ePressed) {

        }
        SpriteCounter++;

        if (!keyHandler.aPressed && !keyHandler.dPressed && !keyHandler.sPressed && !keyHandler.wPressed) {
            SpriteNumber = 1;
            SpriteCounter = 0;
            return;
        }

        if (SpriteCounter > 10) {
            if (SpriteNumber == 1) {
                SpriteNumber = 2;
            } else if (SpriteNumber == 2) {
                SpriteNumber = 1;
            }
            SpriteCounter = 0;
        }
    }

    public void draw(Graphics2D gra2) {
        BufferedImage now = null;

        switch (direction) {
            case "up":
                if (SpriteNumber == 1) {
                    now = animations[0];
                } else {
                    now = animations[1];
                }
                break;
            case "down":
                if (SpriteNumber == 1) {
                    now = animations[2];
                } else {
                    now = animations[3];
                }
                break;
            case "left":
                if (SpriteNumber == 1) {
                    now = animations[4];
                } else {
                    now = animations[5];
                }
                break;
            case "right":
                if (SpriteNumber == 1) {
                    now = animations[6];
                } else {
                    now = animations[7];
                }
                break;
        }
        gra2.drawImage(now, position[0], position[1], gp.tileSize, gp.tileSize, null);
    }
}