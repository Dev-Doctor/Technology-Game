/**
 * @author DevDoctor
 * @version 1.0
 * @file TileManager.java
 *
 * @brief The TileManager
 *
 */
package main.Java.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import main.Java.DefaultValues;
import main.Java.GamePanel;

/**
 * @class TileManager
 *
 * @brief The TileManager class
 *
 * Draws the tiles on the screen
 */
public class TileManager {

    GamePanel gp;

    public TileManager(GamePanel gp) {
        this.gp = gp;
    }
    /**
     * @brief Draws the tiles of the room
     * @param r current room to draw
     * @param gra2 
     */
    public void DrawMap(Room r, Graphics2D gra2) {
        for (int i = 0; i < DefaultValues.MaxRowsTiles; i++) {
            for (int j = 0; j < DefaultValues.MaxColTiles; j++) {
                gra2.drawImage(r.tile_matrix[i][j].getImage(), DefaultValues.tileSize * j, DefaultValues.tileSize * i, DefaultValues.tileSize, DefaultValues.tileSize, null);
            }
        }
        if (r.isLast) {
            gra2.setColor(Color.green);
            BufferedImage fi = null;
            try {
                fi = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/tiles/trapdoor.png"));
            } catch (IOException ex) {
                Logger.getLogger(TileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            gra2.drawImage(fi, r.next_floor.x, r.next_floor.y, r.next_floor.width, r.next_floor.height, null);
        }
        
        if (DefaultValues.showHitboxes) {
            gra2.setColor(Color.orange);
            gra2.drawRect(r.hit_top.x, r.hit_top.y, r.hit_top.width, r.hit_top.height);
            gra2.drawRect(r.hit_right.x, r.hit_right.y, r.hit_right.width, r.hit_right.height);
            gra2.drawRect(r.hit_bottom.x, r.hit_bottom.y, r.hit_bottom.width, r.hit_bottom.height);
            gra2.drawRect(r.hit_left.x, r.hit_left.y, r.hit_left.width, r.hit_left.height);
            if (r.isLast) {
                gra2.setColor(Color.green);
                gra2.drawRect(r.next_floor.x, r.next_floor.y, r.next_floor.width, r.next_floor.height);
            }
        }

    }

//    public void DrawMap(Tile[][] world, Graphics2D gra2) {
//        for (int i = 0; i < DefaultValues.MaxRowsTiles; i++) {
//            for (int j = 0; j < DefaultValues.MaxColTiles; j++) {
//                gra2.drawImage(world[i][j].getImage(), DefaultValues.tileSize * j, DefaultValues.tileSize * i, DefaultValues.tileSize, DefaultValues.tileSize, null);
//            }
//        }
//        
//    }
}
