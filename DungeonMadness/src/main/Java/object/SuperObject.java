/**
 * @author Jifrid
 * @version 1.0
 * @file SuperObject.java
 *
 * @brief The main file for Objects
 *
 */
package main.Java.object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.Java.DefaultValues;
import main.Java.GamePanel;

/**
 * @class SuperObject
 *
 * @brief The main class for all the Objects
 *
 * It is the main class used to create all the objects
 */
public class SuperObject {
    
    /**The object image*/
    public BufferedImage image;
    /**The name of the object*/
    public String name;
    /**determinate if the object has collisions*/
    public boolean collision = false;
    /**coordinates of the object*/
    public int[] position = new int[2];
    /**Hitbox of the object*/
    public Rectangle solidArea = new Rectangle(0, 0, 64, 64);
    /**Default hitbox values*/
    public int solidAreaDefaultX = 0, solidAreaDefaultY = 0;
    
    /**
     * @brief Draws the object on the screen
     * @param gra2
     * @param gp 
     */
    public void draw(Graphics2D gra2, GamePanel gp) {
        
        // DRAW OBJECT SPRITE
        gra2.drawImage(image, position[0], position[1], DefaultValues.tileSize, DefaultValues.tileSize, null);

        // DRAW HITBOX
        if (DefaultValues.showHitboxes) {
            gra2.setColor(Color.green);
            gra2.drawRect(position[0] + solidArea.x, position[1] + solidArea.y, solidArea.width, solidArea.height);
        }
    }

    public String getName() {
        return name;
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
}
