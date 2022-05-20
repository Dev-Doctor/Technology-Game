/**
 * @author DevDoctor
 * @version 1.0
 * @file Tile.java
 *
 * @brief Tile class file
 *
 */
package main.Java.world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import main.Java.DefaultValues;

/**
 * @class Tile
 *
 * @brief The Tile class
 */
public class Tile {
    
    /**Tile collisions*/
    public boolean Collision;
    /**Define if the tile is a gate*/
    public boolean isGate = false;
    /**Texture of the tile*/
    BufferedImage texture;
    /**default image of the file*/
    final String def_image = "/main/resources/assets/textures/default.png";

    public Tile() {
        Collision = false;
        try {
            texture = ImageIO.read(getClass().getResourceAsStream(def_image));
        } catch (IOException ex) {
            Logger.getLogger(Tile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Tile(boolean Collision, String image, String theme) {
        String loc = DefaultValues.themes_location + theme + "/textures/tiles/" + image;
        this.Collision = Collision;
        try {
            System.out.println(loc);
            texture = ImageIO.read(new File(loc));
            System.out.println(loc);
        } catch (IOException ex) {
            Logger.getLogger(Tile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Tile(boolean Collision, String image, boolean isGate, String theme) {
        this.Collision = Collision;
        this.isGate = isGate;
        try {
            System.out.println(DefaultValues.themes_location + theme + "/textures/tiles/" + image);
            texture = ImageIO.read(getClass().getResourceAsStream(DefaultValues.themes_location + theme + "/textures/tiles/" + image));
        } catch (IOException ex) {
            Logger.getLogger(Tile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean hasCollision() {
        return Collision;
    }

    public void setCollision(boolean Collision) {
        this.Collision = Collision;
    }

    public BufferedImage getImage() {
        return texture;
    }

    public void setImage(BufferedImage image) {
        this.texture = image;
    }
}
