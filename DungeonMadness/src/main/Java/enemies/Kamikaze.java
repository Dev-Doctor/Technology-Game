/**
 * @author Jifrid
 * @version 1.0
 * @file Kamikaze.java
 *
 * @brief The Kamikaze class file
 */
package main.Java.enemies;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import main.Java.GamePanel;
import main.Java.entities.Entity;

/**
 * @class Kamikaze
 *
 * @brief The Kamikaze class
 */
public class Kamikaze extends Entity{
    public Kamikaze(GamePanel gp) {
        super(gp);
        type = 2;
        name = "Kamikaze";
        speed = 2;
        maxHealth = 1;
        health = maxHealth;
        damage = 50;
        
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
        HitSound = "kamikaze/hit.wav";
        DeathSound = "kamikaze/death.wav";
        
        
        try {
            animations[0] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/enemies/kamikaze/def.png"));
            animations[1] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/enemies/kamikaze/def2.png"));
            animations[2] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/enemies/kamikaze/def.png"));
            animations[3] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/enemies/kamikaze/def2.png"));
            animations[4] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/enemies/kamikaze/def.png"));
            animations[5] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/enemies/kamikaze/def2.png"));
            animations[6] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/enemies/kamikaze/def.png"));
            animations[7] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/enemies/kamikaze/def2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Kamikaze.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    }
    
    /**
     * @brief set the Action
     * 
     * performs the action
     */
    public void setAction(){
        
        actionLockCounter++;
        if (actionLockCounter == 10) {
             
            int diffX = position[0] - gp.GetPlayer().GetX();
            int diffY = position[1] - gp.GetPlayer().GetY();
            
            if (Math.abs(diffY) < Math.abs(diffX)) {
                if (diffX < 0) {
                    direction = "right";
                }else{
                    direction = "left";
                }
            }else{
                if (diffY < 0) {
                    direction = "down";
                }else{
                    direction = "up";
                }
            }
            actionLockCounter = 0;
        }
       
    }
}
