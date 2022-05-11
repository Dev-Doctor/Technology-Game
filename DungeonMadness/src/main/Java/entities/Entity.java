package main.Java.entities;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.Java.DefaultValues;
import main.Java.GamePanel;

/**
 * @author DevDoctor Jifrid
 */
public class Entity {

    public GamePanel gp;

    public int maxHealth;
    public int health;
    public int armor;
    public String name;
    public int speed = 6;
    public int damage = 0;
    private boolean collisionDamageOn = false;
    public int[] position = new int[2];

    public BufferedImage[] animations;
    public BufferedImage[] attackAnimations;
    public String direction;

    public int SpriteCounter = 0;
    public int SpriteNumber = 1;

    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public Rectangle attackArea;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    public boolean invincible = false;
    public int invincibleCounter = 0;
    public boolean attacking = false;
    public int type; // 0=player 1=npc 2=monster

    public Entity(GamePanel gp) {
        this.gp = gp;
        solidArea = new Rectangle(16, 18, 32, 46);
        attackArea = new Rectangle(0, 0, 0, 0);
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

    public void setAction() {

    }

    public void update() {
        setAction();

        collisionOn = false;
        gp.collisionChecker.checkTile(this);
        gp.collisionChecker.checkEntity(this, gp.npc);
        gp.collisionChecker.checkEntity(this, gp.enemy);
        gp.collisionChecker.checkObject(this, false);
        boolean contactPlayer = gp.collisionChecker.checkPlayer(this);

        if (this.type == 2 && contactPlayer == true) {
            if (!gp.pl.invincible) {
                gp.pl.health -= 20;
                gp.pl.invincible = true;
                //System.out.println("Health: " + gp.pl.health + "/" + gp.pl.maxHealth);
            }
        }

        if (collisionOn == false) {
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
        
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
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
        
        //SET TRANSPARENCY
        if (invincible) {
            gra2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }

        // DRAW ENTITY SPRITE
        gra2.drawImage(now, position[0], position[1], DefaultValues.tileSize, DefaultValues.tileSize, null);
        
        //RESET TRANSPARENCY
        gra2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // DRAW HITBOX
        if (DefaultValues.showHitboxes) {
            gra2.setColor(Color.blue);
            gra2.drawRect(position[0] + solidArea.x, position[1] + solidArea.y, solidArea.width, solidArea.height);
        }
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

    public void cDamageOn() {
        collisionDamageOn = true;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isCDamageOn() {
        return collisionDamageOn;
    }

}
