/**
 * @author DevDoctor, Jifrid
 * @version 1.0
 * @file Player.java
 *
 * @brief The Player file
 *
 */
package main.Java.entities;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import main.Java.DefaultValues;
import main.Java.GamePanel;
import main.Java.KeyHandler;
import main.Java.object.arrow;

/**
 * @class Player
 *
 * @brief The Player class
 */
public class Player extends Entity {

    public String Username;

    KeyHandler keyHandler;
    /**
     * Cut Content Key counter
     */
    public int nKey = 0;

    /**
     * Total kills
     */
    public int EnemyKilled = 0;
    /**
     * Current floor Explored rooms
     */
    public int RoomExplored = 0;
    /**
     * All explored rooms
     */
    public int TotRoomExplored = 0;

    public Player(GamePanel gp, KeyHandler keyHandler, String username) {
        super(gp);
        this.Username = username;
        animations = new BufferedImage[8];
        attackAnimations = new BufferedImage[8];
        this.keyHandler = keyHandler;
        attackArea.width = DefaultValues.tileSize;
        attackArea.height = DefaultValues.tileSize;
        name = "Player";
        projectile = new arrow(gp);

        SetDefaultValues();
        LoadTheme();
        getPlayerImage();
        getPlayerAttackImage();
    }

    /**
     * @brief Load the current theme skin and sounds
     */
    public void LoadTheme() {
        String loc = DefaultValues.themes_location;
    }

    /**
     * @brief Set the default values
     */
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

    public void getPlayerAttackImage() {
        try {
            attackAnimations[0] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/player/1/atkUp.png"));
            attackAnimations[1] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/player/1/atkUp.png"));
            attackAnimations[2] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/player/1/atkDown.png"));
            attackAnimations[3] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/player/1/atkDown.png"));
            attackAnimations[4] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/player/1/atkLeft.png"));
            attackAnimations[5] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/player/1/atkLeft.png"));
            attackAnimations[6] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/player/1/atkRight.png"));
            attackAnimations[7] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/player/1/atkRight.png"));
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @brief Update the Player
     *
     * Update the Player movement, action, invincibility, animation and death
     */
    public void update() {
        collisionOn = false;

        if (attacking) {
            attack();
        } else if (keyHandler != null) {
            if (keyHandler.wPressed || keyHandler.sPressed
                    || keyHandler.aPressed || keyHandler.dPressed || keyHandler.spacePressed) {

                if (keyHandler.spacePressed) {
                    attacking = true;
                }

                gp.collisionChecker.checkTile(this);
                gp.collisionChecker.checkBorder(this);

                int enemyIndex = gp.collisionChecker.checkEntity(this, gp.GetWorld().GetCurrentRoom().GetEnemies()); //ENEMIES
                contactEnemy(enemyIndex);

                int objectIndex = gp.collisionChecker.checkObject(this);
                pickUpObject(objectIndex);

                if (collisionOn == false) {
                    if (keyHandler.wPressed) {
                        position[1] -= speed;
                        direction = "up";
                    }
                    if (keyHandler.sPressed) {
                        position[1] += speed;
                        direction = "down";
                    }
                    if (keyHandler.aPressed) {
                        position[0] -= speed;
                        direction = "left";
                    }
                    if (keyHandler.dPressed) {
                        position[0] += speed;
                        direction = "right";
                    }
                }

                if (keyHandler.ePressed) {

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
            } else {
                SpriteNumber = 1;
                SpriteCounter = 0;
            }
        }

        if (keyHandler.fPressed && !projectile.alive && shotAvailableCounter == 30) {
            projectile.set(position[0], position[1], direction, true, this);
            gp.GetWorld().GetCurrentRoom().GetProjectiles().add(projectile);
            shotAvailableCounter = 0;
            //projectile
        }

        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }

        if (health <= 0) {
            if (gp.gameState != gp.gameOverState) {
                gp.gameState = gp.gameOverState;
                gp.GetSoundManager().PlaySound("player\\death.wav");
            }
        }

    }

    /**
     * @brief Attack other Entity
     *
     * Check if the player sword hitbox is colliding with the Entity
     */
    private void attack() {
        SpriteCounter++;
        if (SpriteCounter <= 5) {
            SpriteNumber = 1;
        }
        if (SpriteCounter > 5 && SpriteCounter <= 25) {
            SpriteNumber = 2;

            int currentX = position[0];
            int currentY = position[1];
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            switch (direction) {
                case "up":
                    position[1] -= attackArea.height;
                    break;
                case "down":
                    position[1] += attackArea.height;
                    break;
                case "left":
                    position[0] -= attackArea.width;
                    break;
                case "right":
                    position[0] += attackArea.width;
                    break;
            }

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            int enemyIndex = gp.collisionChecker.checkEntity(this, gp.GetWorld().GetCurrentRoom().GetEnemies());
            damageEnemy(enemyIndex, 40);

            position[0] = currentX;
            position[1] = currentY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }

        if (SpriteCounter > 25) {
            SpriteNumber = 1;
            SpriteCounter = 0;
            attacking = false;
        }
    }

    /**
     * @brief Function for pick up items
     * @param i the item location in the obj ArrayList
     */
    private void pickUpObject(int i) {
        if (i == 999) {
            return;
        }
        String objName = gp.obj.get(i).getName();
        switch (objName) {
            case "key":
                nKey++;
                gp.obj.remove(i);
                break;
            case "chest":
                if (nKey > 0) {
                    nKey--;
                    gp.obj.remove(i);
                }
                break;
            case "heart":
                gp.GetSoundManager().PlaySound("heart/pickup.wav");
                gp.obj.remove(i);
                health += 25;
                if (health > maxHealth) {
                    health = 100;
                }
                break;
        }
    }

    /**
     * @brief CUT CONTENT: Interaction with NPC
     *
     * The function to interact with the NPCs
     * @param i The NPC location in the ArrayList
     * @deprecated
     */
    @Deprecated
    private void interactNPC(int i) {
        if (i == 999) {
            return;
        }
        //System.out.println("Stai colpendo un NPC");
    }

    /**
     * @brief Entity colliding with Player
     *
     * If the other Entity doesn't have the damage on return; If the other
     * Entity is not dying and the player is not invincibility get damaged by
     * the other Entity; If the other Entity is the Kamikaze damage and delete
     * him
     * @param i the other Entity position in the ArrayList
     */
    private void contactEnemy(int i) {
        if (i == 999) {
            return;
        }
        if (!gp.GetWorld().GetCurrentRoom().GetEnemies().get(i).isCDamageOn()) {
            return;
        }
        if (!isInvincible() && !gp.GetWorld().GetCurrentRoom().GetEnemies().get(i).dying) {
            health -= gp.GetWorld().GetCurrentRoom().GetEnemies().get(i).getDamage();
            invincible = true;
            if (gp.GetWorld().GetCurrentRoom().GetEnemies().get(i).GetName() == "Kamikaze") {
                gp.GetWorld().GetCurrentRoom().GetEnemies().get(i).alive = false;
            }
        }
    }

    /**
     * @brief Damage other Entity
     *
     * Damage the other Entity, give him knockback and invincibility
     * @param i the other Entity position in the ArrayList
     * @param dmg the damage
     */
    public void damageEnemy(int i, int dmg) {
        if (i == 999) {
            //System.out.println("miss");
            return;
        }
        //System.out.println("hit");
        if (!gp.GetWorld().GetCurrentRoom().GetEnemies().get(i).isInvincible()) {
            int damage = dmg;
            gp.GetWorld().GetCurrentRoom().GetEnemies().get(i).health -= damage;
            gp.GetWorld().GetCurrentRoom().GetEnemies().get(i).invincible = true;
            gp.GetWorld().GetCurrentRoom().GetEnemies().get(i).damageReaction();
            System.out.println(gp.GetWorld().GetCurrentRoom().GetEnemies().get(i).HitSound);
            gp.GetSoundManager().PlaySound(gp.GetWorld().GetCurrentRoom().GetEnemies().get(i).HitSound);

            if (gp.GetWorld().GetCurrentRoom().GetEnemies().get(i).health <= 0) {
                gp.GetWorld().GetCurrentRoom().GetEnemies().get(i).dying = true;
            }
        }
    }

    /**
     * @brief Change the coordinates
     *
     * Sets the new coordinates for the Player
     * @param X
     * @param Y
     */
    public void SetNewCoordinates(int X, int Y) {
        position[0] = X;
        position[1] = Y;
    }

    /**
     * @brief Draws the Player
     *
     * Draws the current Player on the screen, this is affected by invincibility
     * and if the player is attacking
     *
     * @param gra2
     */
    public void draw(Graphics2D gra2) {
        BufferedImage now = null;
        int tempX = position[0];
        int tempY = position[1];
        int height = DefaultValues.tileSize;
        int width = DefaultValues.tileSize;

        switch (direction) {
            case "up":
                if (attacking) {
                    tempY -= DefaultValues.tileSize;
                    if (SpriteNumber == 1) {
                        now = attackAnimations[0];
                    } else {
                        now = attackAnimations[1];
                    }
                    height = DefaultValues.tileSize * 2;
                } else {
                    if (SpriteNumber == 1) {
                        now = animations[0];
                    } else {
                        now = animations[1];
                    }
                }
                break;
            case "down":
                if (attacking) {
                    if (SpriteNumber == 1) {
                        now = attackAnimations[2];
                    } else {
                        now = attackAnimations[3];
                    }
                    height = DefaultValues.tileSize * 2;
                } else {
                    if (SpriteNumber == 1) {
                        now = animations[2];
                    } else {
                        now = animations[3];
                    }
                }
                break;
            case "left":
                if (attacking) {
                    tempX -= DefaultValues.tileSize;
                    if (SpriteNumber == 1) {
                        now = attackAnimations[4];
                    } else {
                        now = attackAnimations[5];
                    }
                    width = DefaultValues.tileSize * 2;
                } else {
                    if (SpriteNumber == 1) {
                        now = animations[4];
                    } else {
                        now = animations[5];
                    }
                }
                break;
            case "right":
                if (attacking) {
                    if (SpriteNumber == 1) {
                        now = attackAnimations[6];
                    } else {
                        now = attackAnimations[7];
                    }
                    width = DefaultValues.tileSize * 2;
                } else {
                    if (SpriteNumber == 1) {
                        now = animations[6];
                    } else {
                        now = animations[7];
                    }
                }
                break;
        }

        //SET TRANSPARENCY 
        if (isInvincible()) {
            gra2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        // DRAW PLAYER
        gra2.drawImage(now, tempX, tempY, width, height, null);

        // DRAW HITBOX
        if (DefaultValues.showHitboxes) {
            gra2.setColor(Color.red);
            gra2.drawRect(position[0] + solidArea.x, position[1] + solidArea.y, solidArea.width, solidArea.height);
        }

        //RESET TRANSPARENCY
        gra2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        //gra2.drawString("Attacking:" + attacking, 10, 500);
        //gra2.drawString("Invincible:"+invincibleCounter, 10, 600); 
        gra2.setFont(new Font("Arial", Font.PLAIN, 25));
        gra2.setColor(Color.WHITE);
        int length = (int) gra2.getFontMetrics().getStringBounds(Username, gra2).getWidth();
        gra2.drawString(Username, position[0] + DefaultValues.tileSize / 2 - length / 2, position[1] - 10);
    }

    public int getRoomExplored() {
        return RoomExplored;
    }

    public int getEnemyKilled() {
        return EnemyKilled;
    }

    public String getUsername() {
        return Username;
    }

}
