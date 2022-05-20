/**
 * @author Jifrid
 * @version 1.0
 * @file rock.java
 *
 * @brief The rock projectile file
 *
 */
package main.Java.object;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import main.Java.GamePanel;
import main.Java.entities.Projectile;

/**
 * @class rock
 *
 * @brief The rock projectile class
 * @see main.Java.entities.Projectile
 */
public class rock extends Projectile{
    GamePanel gp;

    public rock(GamePanel gp) {
        super(gp);
        animations = new BufferedImage[8];
        this.gp = gp;
        name = "Rock";
        speed = 4;
        maxHealth = 70;
        health = maxHealth;
        damage = 30;
        alive = false;
        solidArea = new Rectangle(16, 16, 32, 32);
        getImage();
    }

     /**
     * @brief Load Texture
     * 
     * Loads the texture for the rock
     */
    private void getImage() {
        try {
            animations[0] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/projectiles/rock/rock.png"));
            animations[1] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/projectiles/rock/rock.png"));
            animations[2] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/projectiles/rock/rock.png"));
            animations[3] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/projectiles/rock/rock.png"));
            animations[4] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/projectiles/rock/rock.png"));
            animations[5] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/projectiles/rock/rock.png"));
            animations[6] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/projectiles/rock/rock.png"));
            animations[7] = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/projectiles/rock/rock.png"));
        } catch (IOException ex) {
            Logger.getLogger(arrow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
