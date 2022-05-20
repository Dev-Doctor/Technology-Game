package main.Java.entities;

import java.awt.AlphaComposite;
import java.awt.Color;
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
 * @author DevDoctor
 */
public class Player extends Entity {

    KeyHandler keyHandler;
    public int nKey = 0;

    public int EnemyKilled = 0;
    public int RoomExplored = 0;
    public int TotRoomExplored = 0;

    public Player(GamePanel gp, KeyHandler keyHandler) {
        super(gp);
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

    public void LoadTheme() {
        String loc = DefaultValues.themes_location;
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

    public void update() {
        collisionOn = false;

        if (attacking) {
            attack();
        } else if (keyHandler.wPressed || keyHandler.sPressed
                || keyHandler.aPressed || keyHandler.dPressed || keyHandler.spacePressed) {
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
            if (keyHandler.spacePressed) {
                attacking = true;
            }

            gp.collisionChecker.checkTile(this);
            gp.collisionChecker.CheckBorder(this);

            int npcIndex = gp.collisionChecker.checkEntity(this, gp.GetWorld().GetCurrentRoom().GetEnemies()); // NPCS
            interactNPC(npcIndex);

            int enemyIndex = gp.collisionChecker.checkEntity(this, gp.GetWorld().GetCurrentRoom().GetEnemies()); //ENEMIES
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

    private void pickUpObject(int i) {
        if (i == 999) {
            return;
        }
        String objName = gp.obj[i].getName();
        switch (objName) {
            case "key":
                nKey++;
                gp.obj[i] = null;
                //System.out.println("key:" + nKey);
                break;
            case "chest":
                if (nKey > 0) {
                    nKey--;
                    gp.obj[i] = null;
                    //System.out.println("key:" + nKey);
                }
                break;
            case "heart":
                gp.obj[i] = null;
                health += 25;
                if (health > maxHealth) {
                    health = 100;
                }
                break;
        }
    }

    private void interactNPC(int i) {
        if (i == 999) {
            return;
        }
        //System.out.println("Stai colpendo un NPC");
    }

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
        }
    }

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

    public void SetNewCordinates(int X, int Y) {
        position[0] = X;
        position[1] = Y;
    }

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
    }

    public int getRoomExplored() {
        return RoomExplored;
    }

    public int getEnemyKilled() {
        return EnemyKilled;
    }

}
