/**
 * @author Jifrid
 * @version 1.0
 * @file arrow.java
 *
 * @brief The arrow projectile file
 *
 */
package main.Java.object;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import main.Java.GamePanel;
import main.Java.entities.Projectile;

/**
 * @class arrow
 *
 * @brief The arrow projectile class
 * @see main.Java.entities.Projectile
 */
public class arrow extends Projectile {

    GamePanel gp;

    public arrow(GamePanel gp) {
        super(gp);
        animations = new BufferedImage[8];
        this.gp = gp;
        name = "Arrow";
        speed = 8;
        maxHealth = 60;
        health = maxHealth;
        damage = 20;
        alive = false;
        getImage();
    }
    
    /**
     * @brief Load Texture
     * 
     * Loads the texture for the arrow
     */
    private void getImage() {
        try {
            animations[0] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/projectiles/arrow/arrowUp.png"));
            animations[1] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/projectiles/arrow/arrowUp.png"));
            animations[2] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/projectiles/arrow/arrowDown.png"));
            animations[3] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/projectiles/arrow/arrowDown.png"));
            animations[4] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/projectiles/arrow/arrowLeft.png"));
            animations[5] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/projectiles/arrow/arrowLeft.png"));
            animations[6] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/projectiles/arrow/arrowRight.png"));
            animations[7] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/projectiles/arrow/arrowRight.png"));
        } catch (IOException ex) {
            Logger.getLogger(arrow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
