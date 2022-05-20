/**
 * @author heart
 * @version 1.0
 * @file heart.java
 *
 * @brief The heart object class file
 *
 */
package main.Java.object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.Java.DefaultValues;
import main.Java.GamePanel;

/**
 * @class heart
 *
 * @brief The heart object class
 */
public class heart extends SuperObject {

    public heart(int x, int y) {
        name = "heart";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/objects/heart.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        solidArea = new Rectangle(0, 0, 32, 32); //52, 48
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        this.position[0] = x;
        this.position[1] = y;
    }

    /**
     * @brief Draws the heart
     * 
     * @param gra2
     * @param gp 
     */
    @Override
    public void draw(Graphics2D gra2, GamePanel gp) {

        // DRAW OBJECT SPRITE
        gra2.drawImage(image, position[0], position[1], solidArea.width, solidArea.height, null);

        // DRAW HITBOX
        if (DefaultValues.showHitboxes) {
            gra2.setColor(Color.green);
            gra2.drawRect(position[0] + solidArea.x, position[1] + solidArea.y, solidArea.width, solidArea.height);
        }
    }

}
