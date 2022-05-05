package main.Java.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.Java.GamePanel;

/** @author DevDoctor */
public class Entity {
    
    public GamePanel gp;
    
    public int maxHealth;
    public int health;
    public int armor;
    public String name;
    public int speed = 6;
    public int[] position = new int[2];
    
    public BufferedImage[] animations;
    public String direction;
    
    public int SpriteCounter = 0;
    public int SpriteNumber = 1;
    
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    
    public Entity(GamePanel gp) {
        this.gp = gp;
        solidArea = new Rectangle(16, 18, 32, 46);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        maxHealth = 0;
        health = 0;
        armor = 0;
        name = "";
    }

    public Entity(int[] pos, int maxHealth, String name) {
        this.position = pos;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.name = name;
    }
    
    public void setAction(){
        
    }
    
    public void update(){
        setAction();
        
        collisionOn = false;
        gp.collisionChecker.checkTile(this);
        gp.collisionChecker.checkPlayer(this);
        
        if (collisionOn == false) {
            switch(direction){
                case "up":
                    position[1] -= speed;
                    break;
                case "down":
                    position[1] += speed;
                    break;
                case "left":
                    position[0] -= speed;
                    break;
                case "right":
                    position[0] += speed;
                    break;
            }
        }

        SpriteCounter++;

        if (SpriteCounter > 10) {
            if (SpriteNumber == 1) {
                SpriteNumber = 2;
            } else if (SpriteNumber == 2) {
                SpriteNumber = 1;
            }
            SpriteCounter = 0;
        }
    }
    
    public void draw(Graphics2D gra2){
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
        gra2.setColor(Color.red);
        gra2.drawRect(position[0] + solidArea.x, position[1] + solidArea.y, solidArea.width, solidArea.height);
    }
    
    public int GetX() {
        return position[0];
    }
    
    public int GetY() {
        return position[1];
    }
    
    public void SetX(int x) {
        position[0] = x;
    }
    
    public void SetY(int y) {
        position[1] = y;
    }
    
    public int GetSpeed() {
        return speed;
    }
    
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    
    public String getName() {
        return name;
    }
}
