package main.Java.entities;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import main.Java.DefaultValues;
import main.Java.GamePanel;
import main.Java.KeyHandler;

/**
 * @author DevDoctor */
public class Player extends Entity {

    KeyHandler keyHandler;
    int nKey = 0;

    public Player(GamePanel gp, KeyHandler keyHandler) {
        super(gp);
        animations = new BufferedImage[8];
        this.keyHandler = keyHandler;
        this.solidArea = new Rectangle(16, 18, 32, 46);
        name = "Player";

        SetDefaultValues();
        getPlayerImage();
    }

    public void SetDefaultValues() {
        position[0] = DefaultValues.PlDefaultX;
        position[1] = DefaultValues.PlDefaultY;
        direction = "up";
        speed = 3;
        maxHealth = 100;
        health = maxHealth;
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
        collisionOn = false;
        gp.collisionChecker.checkTile(this);
        gp.collisionChecker.CheckBorder(this);
        
        if (keyHandler.wPressed) {
            direction = "up";
        }
        if (keyHandler.sPressed) {
            direction = "down";
        }
        if (keyHandler.aPressed) {
            direction = "left";
        }
        if (keyHandler.dPressed) {
            direction = "right";
        }

        int npcIndex = gp.collisionChecker.checkEntity(this, gp.npc);
        interactNPC(npcIndex);

        int enemyIndex = gp.collisionChecker.checkEntity(this, gp.enemy);
        contactEnemy(enemyIndex);

        int objectIndex = gp.collisionChecker.checkObject(this, true);
        pickUpObject(objectIndex);

        if (collisionOn == false) {
            if (keyHandler.wPressed) {
                position[1] -= speed;
            }
            if (keyHandler.sPressed) {
                position[1] += speed;
            }
            if (keyHandler.aPressed) {
                position[0] -= speed;
            }
            if (keyHandler.dPressed) {
                position[0] += speed;
            }
        }

        if (keyHandler.ePressed) {

        }

        SpriteCounter++;

        if (!keyHandler.aPressed && !keyHandler.dPressed && !keyHandler.sPressed && !keyHandler.wPressed) {
            SpriteNumber = 1;
            SpriteCounter = 0;
            //return;
        }

        if (SpriteCounter
                > 10) {
            if (SpriteNumber == 1) {
                SpriteNumber = 2;
            } else if (SpriteNumber == 2) {
                SpriteNumber = 1;
            }
            SpriteCounter = 0;
        }

        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    private void pickUpObject(int i) {
        if (i == 999) {
            return;
        }
        String objName = gp.obj[i].getName();
        switch (objName) {
            case "key":
                nKey++;
                gp.obj[i] = null;
                System.out.println("key:" + nKey);
                break;
            case "chest":
                if (nKey > 0) {
                    nKey--;
                    gp.obj[i] = null;
                    System.out.println("key:" + nKey);
                }
                break;
        }
    }

    private void interactNPC(int i) {
        if (i != 999) {
            //System.out.println("Stai colpendo un NPC");
        }
    }

    private void contactEnemy(int i) {
        if (i != 999) {
            if (!gp.enemy[i].isCDamageOn()) {
                return;
            }
            if (!invincible) {
                health -= gp.enemy[i].getDamage();
                //System.out.println("Health: " + health + "/" + maxHealth);
                invincible = true;
            }
        }
    }
    
    public void SetNewCordinates(int X, int Y) {
        position[0] = X;
        position[1] = Y;
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

        if (invincible) {
            gra2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        
        // DRAW PLAYER
        gra2.drawImage(now, position[0], position[1], DefaultValues.tileSize, DefaultValues.tileSize, null);

        // DRAW HITBOX
        if (DefaultValues.showHitboxes) {
            gra2.setColor(Color.red);
            gra2.drawRect(position[0] + solidArea.x, position[1] + solidArea.y, solidArea.width, solidArea.height);
        }

        gra2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        
        // DRAW HEALTH
        gra2.setFont(new Font("Arial", Font.PLAIN, 26));
        gra2.setColor(Color.white);
        gra2.drawString("Health:" + health + "/" + maxHealth, 10, 400);

        //gra2.drawString("Invincible:"+invincibleCounter, 10, 400);  
    }

}
