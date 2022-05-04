package main.Java.entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/** @author DevDoctor */
public class Entity {
    int health;
    int armor;
    String name;
    int speed = 6;
    int[] position = new int[2];
    
    public BufferedImage[] animations;
    public String direction;
    
    int SpriteCounter = 0;
    int SpriteNumber = 1;
    
    public Rectangle solidArea;
    public boolean collisionOn = false;
    
    public Entity() {
        health = 0;
        armor = 0;
        name = "";
    }

    public Entity(int[] pos, int health, String name) {
        this.position = pos;
        this.health = health;
        this.name = name;
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
