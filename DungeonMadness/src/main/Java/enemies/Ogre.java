/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package main.Java.enemies;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import main.Java.GamePanel;
import main.Java.entities.Entity;

public class Ogre extends Entity {

    public Ogre(GamePanel gp) {
        super(gp);
        type = 2;
        name = "Ogre";
        speed = 1;
        maxHealth = 2;
        health = maxHealth;
        solidArea = new Rectangle(0, 18, 64, 46);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        animations = new BufferedImage[8];
        direction = "down";
        
        getImage();
    }
    
    public void getImage(){

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
    
    public void setAction(){
        
        actionLockCounter++;
        if (actionLockCounter == 60) {
            
            Random r = new Random();
            int d = r.nextInt(100)+1;
        
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
