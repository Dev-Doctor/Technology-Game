package main.Java.world;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/** @author DevDoctor */
public class Tile {

    public boolean Collision;
    public boolean isGate = false;
    BufferedImage texture;
    final String def_image = "/main/resources/assets/textures/default.png";
    final String def_loc = "/main/resources/assets/textures/tiles";

    public Tile() {
        Collision = false;
        try {
            texture = ImageIO.read(getClass().getResourceAsStream(def_image));
        } catch (IOException ex) {
            Logger.getLogger(Tile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Tile(boolean Collision, String image) {
        this.Collision = Collision;
        try {
            System.out.println(def_loc+"/"+image);
            texture = ImageIO.read(getClass().getResourceAsStream(def_loc + "/" + image));
        } catch (IOException ex) {
            Logger.getLogger(Tile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Tile(boolean Collision, String image, boolean isGate) {
        this.Collision = Collision;
        this.isGate = isGate;
        try {
            System.out.println(def_loc+"/"+image);
            texture = ImageIO.read(getClass().getResourceAsStream(def_loc + "/" + image));
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
