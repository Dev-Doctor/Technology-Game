/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Java.entities;

import java.awt.Rectangle;
import main.Java.GamePanel;

public class Projectile extends Entity {

    Entity user;

    public Projectile(GamePanel gp) {
        super(gp);
        type = 3;
        solidArea = new Rectangle(16, 16, 32, 32);
    }

    public void set(int x, int y, String direction, boolean alive, Entity user) {
        this.position[0] = x;
        this.position[1] = y;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.health = maxHealth;
    }

    public void update() {
        
        if (user == gp.GetPlayer()) {
            int enemyIndex = gp.collisionChecker.checkEntity(this, gp.GetWorld().GetCurrentRoom().GetEnemies());
            if (enemyIndex != 999) {
                gp.GetPlayer().damageEnemy(enemyIndex, damage);
                alive = false;
            }
        }else{
            boolean contactPlayer = gp.collisionChecker.checkPlayer(this);
            if (!gp.GetPlayer().isInvincible() && contactPlayer) {
                damagePlayer(damage);
                alive = false;
            }
        }
        
        gp.collisionChecker.checkTile(this);
        
        switch (direction) {
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
        
        health--;
        if (health <= 0) {
            alive = false;
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
}
