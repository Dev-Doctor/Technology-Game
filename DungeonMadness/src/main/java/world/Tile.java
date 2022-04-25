/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.world;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author DevDoctor
 */
public class Tile {
    boolean Collision;
    BufferedImage texture;

    public Tile() {
        Collision = false;
        try {
            texture = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/default.png"));
        } catch (IOException ex) {
            Logger.getLogger(Tile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Tile(boolean Collision, String loc) {
        this.Collision = Collision;
        try {
            texture = ImageIO.read(getClass().getResourceAsStream(loc));
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
