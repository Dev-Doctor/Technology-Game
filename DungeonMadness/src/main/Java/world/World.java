package main.Java.world;

import java.util.List;
import main.Java.GamePanel;

/**
 * @author DevDoctor
 */
public class World {

    GamePanel gp;
    List<Floor> floors;

    public boolean DrawMap;
    int floor_number = 0;
    int[][] matrix;

//  !!! TEMPORARY !!!
    Room r;
    Floor temp;

    public World(GamePanel gp) {
        this.gp = gp;
        this.matrix = new int[12][18];
        this.r = null;
        this.DrawMap = true;
        this.temp = new Floor(gp);
    }
    
    public void LoadDungeon(String theme) {
        floor_number++;
        temp.Load(theme);
    }
    
    public Room GetCurrentRoom() {
        return temp.GetCurrentRoom();
    }
    
    public Floor GetCurrentFloor() {
        return temp;
    }
}
