package main.Java.world;

import java.awt.Graphics2D;
import main.java.GamePanel;

/** @author DevDoctor */
public class TileManager {
    GamePanel gp;

    public TileManager(GamePanel gp) {
        this.gp = gp;
    }
    
    public void DrawMap(Tile[][] world, Graphics2D gra2) {
        for (int i = 0; i < gp.MaxRowsTiles; i++) {
            for (int j = 0; j < gp.MaxColTiles; j++) {
                gra2.drawImage(world[i][j].getImage(), gp.tileSize*j, gp.tileSize*i, gp.tileSize, gp.tileSize, null);
            }
        }
    }
}
