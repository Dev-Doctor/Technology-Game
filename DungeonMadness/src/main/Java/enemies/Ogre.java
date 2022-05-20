/**
 * @author Jifrid
 * @version 1.0
 * @file Ogre.java
 *
 * @brief The Ogre class file
 */
package main.Java.enemies;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import main.Java.DefaultValues;
import main.Java.GamePanel;
import main.Java.entities.Entity;

/**
 * @class Ogre
 *
 * @brief The Ogre class
 */
public class Ogre extends Entity {
    
    public Ogre(GamePanel gp) {
        super(gp);
        type = 2;
        name = "Ogre";
        speed = 1;
        maxHealth = 100;
        health = maxHealth;
        damage = 40;
        
        solidArea = new Rectangle(0, 18, 64, 46);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        animations = new BufferedImage[8];
        
        cDamageOn();
        SetTheme();
    }
    
    /**
     * @brief Loads the theme
     * 
     * Load sounds and textures from the current theme
     */
    public void SetTheme(){
        HitSound = "ogre/hit.wav";
        DeathSound = "ogre/death.wav";
        
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
            Logger.getLogger(Ogre.class.getName()).log(Level.SEVERE, null, ex);
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
        
    }
    
}
