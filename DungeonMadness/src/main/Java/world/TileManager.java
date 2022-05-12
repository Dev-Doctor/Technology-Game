package main.Java.world;

import java.awt.Color;
import java.awt.Graphics2D;
import main.Java.DefaultValues;
import main.Java.GamePanel;

/**
 * @author DevDoctor
 */
public class TileManager {

    GamePanel gp;

    public TileManager(GamePanel gp) {
        this.gp = gp;
    }

    public void DrawMap(Room r, Graphics2D gra2) {
        for (int i = 0; i < DefaultValues.MaxRowsTiles; i++) {
            for (int j = 0; j < DefaultValues.MaxColTiles; j++) {
                gra2.drawImage(r.tile_matrix[i][j].getImage(), DefaultValues.tileSize * j, DefaultValues.tileSize * i, DefaultValues.tileSize, DefaultValues.tileSize, null);
            }
        }
        if (DefaultValues.showHitboxes) {
            gra2.setColor(Color.orange);
            gra2.drawRect(r.hit_top.x, r.hit_top.y, r.hit_top.width, r.hit_top.height);
            gra2.drawRect(r.hit_right.x, r.hit_right.y, r.hit_right.width, r.hit_right.height);
            gra2.drawRect(r.hit_bottom.x, r.hit_bottom.y, r.hit_bottom.width, r.hit_bottom.height);
            gra2.drawRect(r.hit_left.x, r.hit_left.y, r.hit_left.width, r.hit_left.height);
            if(r.isLast) {
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
