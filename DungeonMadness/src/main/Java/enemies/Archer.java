/**
 * @author Jifrid
 * @version 1.0
 * @file Archer.java
 *
 * @brief The Archer class file
 */
package main.Java.enemies;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import main.Java.DefaultValues;
import main.Java.GamePanel;
import main.Java.entities.Entity;
import main.Java.object.rock;

/**
 * @class Archer
 *
 * @brief The Archer class
 */
public class Archer extends Entity{
    
    public Archer(GamePanel gp) {
        super(gp);
        type = 2;
        name = "Archer";
        speed = 1;
        maxHealth = 100;
        health = maxHealth;
        projectile = new rock(gp);
        
        solidArea = new Rectangle(0, 18, 64, 46);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        animations = new BufferedImage[8];
        
        SetTheme();
    }
    
    /**
     * @brief Loads the theme
     * 
     * Load sounds and textures from the current theme
     */
    public void SetTheme(){
        HitSound = "archer/hit.wav";
        DeathSound = "archer/death.wav";
        
        try {
            animations[0] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/enemies/ogreTemp/right1.png"));
            animations[1] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/enemies/ogreTemp/right2.png"));
            animations[2] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/enemies/ogreTemp/left1.png"));
            animations[3] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/enemies/ogreTemp/left2.png"));
            animations[4] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/enemies/ogreTemp/left1.png"));
            animations[5] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/enemies/ogreTemp/left2.png"));
            animations[6] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/enemies/ogreTemp/right1.png"));
            animations[7] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/enemies/ogreTemp/right2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Archer.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    }
    
    /**
     * @brief set the Action
     * 
     * performs the action
     */
    public void setAction(){
        
        actionLockCounter++;
        if (actionLockCounter == 60) {
            
            int d = DefaultValues.Random(1, 100);
        
            if (d <= 25) {
                direction = "up";
            }else if (d <= 50) {
                direction = "down";
            }else if (d <= 75) {
                direction = "left";
            }else if (d <= 100) {
                direction = "right";
            }  
            actionLockCounter = 0;
        }
        
        Random r = new Random();
        int s = r.nextInt(100)+1;
        if (s > 95 && !projectile.alive && shotAvailableCounter == 30) {
            projectile.set(position[0], position[1], direction, true, this);
            gp.GetWorld().GetCurrentRoom().GetProjectiles().add(projectile);
            shotAvailableCounter = 0;
        }
        
    }
    
}
