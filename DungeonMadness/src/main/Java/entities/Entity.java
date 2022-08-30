/**
 * @author Jifrid
 * @version 1.0
 * @file Entity.java
 *
 * @brief The Entity
 *
 */
package main.Java.entities;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Java.DefaultValues;
import main.Java.GamePanel;
import main.Java.object.heart;

/**
 * @class Entity
 *
 * @brief The Entity class
 *
 * It is the abstracted class used to create all the entities
 */
public class Entity extends Thread {

    public GamePanel gp;

    //HITBOXES
    /**
     * The hitbox of the Entity as a Rectangle
     */
    public Rectangle solidArea;
    /**
     * The default X and Y of the default hitbox
     */
    public int solidAreaDefaultX, solidAreaDefaultY;
    /**
     * The hitbox of the attack area for the Entity as a Rectangle
     *
     * @see main.Java.entities.Player#attack()
     */
    public Rectangle attackArea;

    //STYLE
    /**
     * Animations for the movement of the Entity
     */
    public BufferedImage[] animations;
    /**
     * Animations for the attack of the Entity
     *
     * @see main.Java.entities.Player#attack()
     */
    public BufferedImage[] attackAnimations;
    /**
     * Sound file for an hit
     */
    public String HitSound;
    /**
     * Sound file for the death of the Entity
     */
    public String DeathSound;

    //ATTRIBUTES
    /**
     * Max health of the Entity
     */
    public int maxHealth;
    /**
     * Current health of the Entity
     */
    public int health;
    /**
     * Name of the Entity
     */
    public String name;
    /**
     * Movement speed of the Entity
     */
    public int speed = 6;
    /**
     * Damage that the Entity does
     */
    public int damage = 0;
    /**
     * The Projectile that the Entity shoots
     */
    public Projectile projectile;

    //STATE
    /**
     * The direction where the Entity is looking
     */
    public String direction = "down";
    /**
     * Determinate if the Entity has collision damage
     */
    private boolean collisionDamageOn = false;
    /**
     * The X and Y position as an Array
     */
    public int[] position = new int[2];
    /**
     * Current sprite of the animation
     */
    public int SpriteNumber = 1;
    /**
     * Determinate if the Entity collision is ON or OFF
     */
    public boolean collisionOn = false;
    /**
     * Determinate if the Entity is invincible
     */
    public boolean invincible = false;
    /**
     * Determinate if the Entity is attacking
     */
    public boolean attacking = false;
    /**
     * Determinate if the Entity is dead or alive
     */
    public boolean alive = true;
    /**
     * Determinate if the Entity is dying
     */
    public boolean dying = false;
    /**
     * Determinate the type of the Entity; 0 = player, 1 = npc, 2 = enemy, 3 =
     * projectile
     */
    public int type;

    //COUNTER
    /**
     * passed milliseconds of invincibility
     */
    public int invincibleCounter = 0;
    /**
     * passed milliseconds for the next animation
     */
    public int SpriteCounter = 0;
    /**
     * passed milliseconds from the last action
     */
    public int actionLockCounter = 0;
    /**
     * passed milliseconds from the Entity death
     */
    public int dyingCounter = 0;
    /**
     * milliseconds for the next shoot
     */
    public int shotAvailableCounter = 0;

    public Entity(GamePanel gp) {
        this.gp = gp;
        //HITBOXES
        solidArea = new Rectangle(16, 18, 32, 46);
        attackArea = new Rectangle(0, 0, 0, 0);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        //ATTRIBUTES
        maxHealth = 0;
        health = 0;
        name = "";
    }

    public Entity(int[] pos, int maxHealth, String name) {
        this.position = pos;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.name = name;
    }

    @Override
    public void run() {
        while (!dying && gp.gameState != gp.gameOverState) {
            if (gp.gameState != gp.pauseState) {
                update();
                try {
                    sleep(16);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Entity.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (type == 2) {
            int rand = DefaultValues.Random(1, 4);
            if (rand == 1) {
                gp.obj.add(new heart(position[0], position[1]));
            }
        }
    }

    /**
     * @brief Set the action for the Enemy
     *
     * Used only in the classes Archer, Chaser, Kamikaze and Ogre
     */
    public void setAction() {
    }

    /**
     * @brief Set the reaction after taking damage for the Enemy
     *
     * Used only in the classes Archer, Chaser, Kamikaze and Ogre
     * @see main.Java.enemies.Archer#damageReaction()
     * @see main.Java.enemies.Chaser#damageReaction()
     * @see main.Java.enemies.Kamikaze#damageReaction()
     * @see main.Java.enemies.Ogre#damageReaction()
     */
    public void damageReaction() {
    }

    /**
     * @brief Update the Entity
     *
     * Update the Entity movement, action, invincibility and animation
     */
    public void update() {
        setAction();

        collisionOn = false;
        gp.collisionChecker.checkTile(this);
        gp.collisionChecker.checkEntity(this, gp.GetWorld().GetCurrentRoom().GetEnemies()); // ENEMIES
        boolean contactPlayer = gp.collisionChecker.checkPlayer(this);

        if (this.type == 2 && contactPlayer == true) {
            damagePlayer(damage);
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
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
    }

    /**
     * @brief Damage the Player
     *
     * Used in all the child classes except Player
     */
    public void damagePlayer(int damage) {
        if (gp.GetPlayer().isInvincible()) {
            return;
        }
        if (type == 2 && !collisionDamageOn) {
            return;
        }
        gp.GetSoundManager().PlaySound("player\\hit.wav");
        gp.GetPlayer().health -= damage;
        gp.GetPlayer().invincible = true;
        if (name == "Kamikaze") {
            alive = false;
        }
        //System.out.println("Health: " + gp.pl.health + "/" + gp.pl.maxHealth);
    }

    /**
     * @brief Draws the current Entity
     *
     * Draws the current Entity on the screen and his health bar, this is affect
     * by invincibility
     *
     * @param gra2
     */
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

        //ENEMY HEALTH BAR
        if (type == 2 && health < maxHealth) {
            double oneScale = (double) DefaultValues.tileSize / maxHealth;
            double hpBarValue = oneScale * health;

            gra2.setColor(new Color(35, 35, 35));
            gra2.fillRect(position[0] - 3, position[1] - 18, DefaultValues.tileSize + 6, 16);

            if (health > 0) {
                gra2.setColor(Color.red);
                gra2.fillRect(position[0], position[1] - 15, (int) hpBarValue, 10);
            }

        }

        //SET TRANSPARENCY
        if (invincible) {
            gra2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }

        if (dying) {
            dyingAnimation(gra2);
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

    /**
     * @brief Sprite for the dying animation
     *
     * Select the alpha channel of the sprite for the dying animation then it
     * calls the changeAlpha method
     * @see #changeAlpha(java.awt.Graphics2D, float)
     * @param g2
     */
    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;
        int i = 5;

        if (dyingCounter <= i) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i && dyingCounter < i * 2) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 2 && dyingCounter < i * 3) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 3 && dyingCounter < i * 4) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 4 && dyingCounter < i * 5) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 5 && dyingCounter < i * 6) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 6 && dyingCounter < i * 7) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 7 && dyingCounter < i * 8) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 8) {
            alive = false;
        }
    }

    /**
     * @brief Change the alpha of the passed sprite
     *
     * Changes the value of the passed sprite to the new one
     * @param g2
     * @param alpha the new alpha value to set to the photo
     */
    private void changeAlpha(Graphics2D g2, float alpha) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
    }

    /**
     * @return the X position
     */
    public int GetX() {
        return position[0];
    }

    /**
     *
     * @return the Y position
     */
    public int GetY() {
        return position[1];
    }

    /**
     * @brief Change the coordinates
     *
     * Sets the new coordinates for the Entity
     * @param X
     * @param Y
     */
    public void SetNewCoordinates(int X, int Y) {
        position[0] = X;
        position[1] = Y;
    }

    /**
     * @brief Sets the X coordinate
     * @param x
     */
    public void SetX(int x) {
        position[0] = x;
    }

    /**
     * @brief Sets the Y coordinate
     * @param y
     */
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

    public String GetName() {
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

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getType() {
        return type;
    }

    public boolean isInvincible() {
        return invincible;
    }
    
    public String getDirection() {
        return direction;
    }

}
