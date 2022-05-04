package main.Java.world;

import main.Java.GamePanel;
/** @author DevDoctor */
public class World {
    GamePanel gp;
    Room r;
    public boolean DrawMap;
    int floor = 0;
    int[][] matrix;
    
    public World(GamePanel gp) {
        this.gp = gp;
        this.matrix = new int[12][18];
        this.r = new Room();
        this.DrawMap = true;
    }

    public void LoadRoom() {
        r.Load("default");
    }
    
    public Tile[][] GetTileMatrix() {
        return r.getMatrix();
    }
}
