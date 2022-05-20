package main.Java.world;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import main.Java.DefaultValues;
import main.Java.GamePanel;

/**
 * @author DevDoctor
 */
public class World {

    GamePanel gp;
    ArrayList<Floor> floors;

    List<String> AllThemesNames;
    public boolean DrawMap;
    int floor_number = 0;
    int[][] matrix;
    String theme;

//  !!! TEMPORARY !!!
    Room r;
    Floor temp;

    public World(GamePanel gp, String theme) {
        this.gp = gp;
        this.matrix = new int[12][18];
        this.r = null;
        this.theme = theme;
        this.DrawMap = true;
        this.floors = new ArrayList<Floor>();
        this.floors.add(new Floor(gp, theme));
        LoadThemes();
    }

    public void LoadDungeon() {
        floors.get(floor_number).Load();
        floor_number++;
    }

    public void ChangeFloor() {
        floors.add(new Floor(gp, theme));
        floors.get(floor_number).Load();
        floor_number++;
        gp.pl.RoomExplored = 0;
    }
    
    public void LoadThemes() {
        File fl = new File(DefaultValues.themes_location);
        AllThemesNames = Arrays.asList(fl.list());
    }

    public Room GetCurrentRoom() {
        return floors.get(floor_number - 1).GetCurrentRoom();
    }

    public Floor GetCurrentFloor() {
        return floors.get(floor_number - 1);
    }

    public int getFloor_number() {
        return floor_number;
    }

    
}
