/**
 * @author DevDoctor
 * @version 1.0
 * @file World.java
 *
 * @brief The World file class
 *
 */
package main.Java.world;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import main.Java.DefaultValues;
import main.Java.GamePanel;

/**
 * @class World
 *
 * @brief The World class
 */
public class World {

    GamePanel gp;
    /**All the floors explored*/
    ArrayList<Floor> floors;
    
    /**All the themes*/
    List<String> AllThemesNames;
    /**Draw map*/
    public boolean DrawMap;
    /**floor counter*/
    int floor_number = 0;
    /**floor map*/
    int[][] matrix;
    /**current loaded theme*/
    String theme;

    /**current room*/
    Room r;
    /**current loaded floor*/
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
    
    /**
     * @brief Loads the dungeon
     */
    public void LoadDungeon() {
        floors.get(floor_number).Load();
        floor_number++;
    }
    
    /**
     * @brief Changes the floor 
     * 
     * to the next one
     */
    public void ChangeFloor() {
        floors.add(new Floor(gp, theme));
        floors.get(floor_number).Load();
        gp.GetSoundManager().PlaySound("world/next_floor.wav");
        gp.GetPlayer().SetNewCoordinates(DefaultValues.PlDefaultX, DefaultValues.PlDefaultY);
        floor_number++;
        gp.pl.RoomExplored = 0;
    }
    
    /**
     * @brief Loads all the themes
     * 
     * gets all the available theme names
     */
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
