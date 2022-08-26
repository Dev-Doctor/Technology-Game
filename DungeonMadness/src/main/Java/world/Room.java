package main.Java.world;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Java.GamePanel;
import main.Java.DefaultValues;
import static main.Java.DefaultValues.Random;
import main.Java.enemies.Archer;
import main.Java.enemies.Chaser;
import main.Java.enemies.Kamikaze;
import main.Java.enemies.Ogre;
import main.Java.entities.Entity;
import main.Java.entities.PlayerMP;
import org.json.*;

/** @author DevDoctor */
public class Room {

    final public Rectangle hit_top = new Rectangle(DefaultValues.tileSize * 7, DefaultValues.tileSize / 4, DefaultValues.tileSize * 4, DefaultValues.tileSize / 2);
    final public Rectangle hit_right = new Rectangle(DefaultValues.tileSize * 17 + DefaultValues.tileSize / 4, DefaultValues.tileSize * 4, DefaultValues.tileSize / 2, DefaultValues.tileSize * 4);
    final public Rectangle hit_bottom = new Rectangle(DefaultValues.tileSize * 7, DefaultValues.tileSize * 11 + DefaultValues.tileSize / 4, DefaultValues.tileSize * 4, DefaultValues.tileSize / 2);
    final public Rectangle hit_left = new Rectangle(DefaultValues.tileSize / 4, DefaultValues.tileSize * 4, DefaultValues.tileSize / 2, DefaultValues.tileSize * 4);
    public Rectangle next_floor = null;
    GamePanel gp;

    final int gate_pos[][] = {
        {0, 7}, {0, 8}, {0, 9}, {0, 10},
        {4, 17}, {5, 17}, {6, 17}, {7, 17},
        {11, 7}, {11, 8}, {11, 9}, {11, 10},
        {4, 0}, {5, 0}, {6, 0}, {7, 0}
    };
    List<String> AllFiles;

    JSONObject jo;
    HashMap<String, Tile> materials_map;

    ArrayList<Entity> entities;
    ArrayList<Entity> projectileList = new ArrayList<>();

    Tile[][] tile_matrix;
    String[][] matrix;

    int value;

    boolean isEmpty;
    boolean isLast;
    boolean explored;

    public Room(GamePanel gp) {
        this.gp = gp;
        SetDefaultValues();
    }

    public Room(GamePanel gp, int value) {
        this.gp = gp;
        this.value = value;
        SetDefaultValues();
    }

    private void SetDefaultValues() {
        this.materials_map = new HashMap<String, Tile>();
        this.tile_matrix = new Tile[DefaultValues.MaxRowsTiles][DefaultValues.MaxColTiles];
        this.matrix = new String[DefaultValues.MaxRowsTiles][DefaultValues.MaxColTiles];
        this.entities = new ArrayList<Entity>();
        this.isEmpty = false;
        this.isLast = false;
        if (value != 1) {
            Ogre t = new Ogre(gp);
            t.SetNewCoordinates(300, 300);
            entities.add(t);
            Chaser c = new Chaser(gp);
            c.SetNewCoordinates(400, 300);
            entities.add(c);
            Archer a = new Archer(gp);
            a.SetNewCoordinates(500, 300);
            entities.add(a);
            Kamikaze k = new Kamikaze(gp);
            k.SetNewCoordinates(600, 300);
            entities.add(k);
        }
    }

    public void Load(String type) {
        String loc = DefaultValues.themes_location + "\\" + type + "\\data";
        String tile_loc = loc + "\\tile";
        String room_data_loc = loc + "\\rooms";
        File fl = new File(tile_loc);
        AllFiles = Arrays.asList(fl.list());

        gp.obj.clear();
        
        explored = true;
        Matrix(room_data_loc);
        Materials(tile_loc);
        CreateTileMatrix();
        StartEnemiesBrains();
    }

    public void Matrix(String loc) {
        File fl = new File(loc);
        List<String> rooms_types = Arrays.asList(fl.list());

        int selected = Random(0, rooms_types.size() - 1);
        String map_loc = loc + "\\" + rooms_types.get(selected);
        
        String json = "";
        try {
            json = Files.readString(Path.of(map_loc));
        } catch (IOException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }

        jo = new JSONObject(json);
        JSONArray json_map = jo.getJSONArray("map");

        for (int r = 0; r < 12; r++) {
            for (int c = 0; c < 18; c++) {
                matrix[r][c] = json_map.getString(r).substring(c, c + 1);
            }
        }
    }

    public void Materials(String tile_loc) {
        JSONObject materials = jo.getJSONObject("materials");
        var iter = materials.keys();

        while (iter.hasNext()) {
            JSONObject tl = new JSONObject();

            String key = iter.next();
            String val = materials.getString(key);
            if (AllFiles.contains(val)) {
                String Tilejson = "";
                try {
                    Tilejson = Files.readString(Path.of(tile_loc + "\\" + val));
                } catch (IOException ex) {
                    Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
                }
                tl = new JSONObject(Tilejson);

                try {
                    if (tl.getBoolean("gate") == true) {
                        materials_map.put(key, new Tile(tl.getBoolean("collision"), tl.getString("texture"), tl.getBoolean("gate"), gp.theme));
                    }
                } catch (Exception ex) {
                    materials_map.put(key, new Tile(tl.getBoolean("collision"), tl.getString("texture"), gp.theme));
                }
            } else {
                materials_map.put(key, new Tile());
            }
        }
    }

    private void CreateTileMatrix() {
        for (int r = 0; r < 12; r++) {
            for (int c = 0; c < 18; c++) {
                tile_matrix[r][c] = materials_map.get(String.valueOf(matrix[r][c]));
            }
        }
        if (isLast) {
            next_floor = new Rectangle(DefaultValues.tileSize * (DefaultValues.MaxColTiles / 2), DefaultValues.tileSize * (DefaultValues.MaxRowsTiles / 2), DefaultValues.tileSize, DefaultValues.tileSize);
        }
    }

    public void DefineGates(boolean top, boolean right, boolean bottom, boolean left) {
        if (top == false) {
            // {0, 7}, {0, 8}, {0, 9}, {0, 10},
            tile_matrix[0][7] = materials_map.get("1");
            tile_matrix[0][8] = materials_map.get("1");
            tile_matrix[0][9] = materials_map.get("1");
            tile_matrix[0][10] = materials_map.get("1");
        }
        if (right == false) {
//            {4, 17}, {5, 17}, {6, 17}, {7, 17},
            tile_matrix[4][17] = materials_map.get("1");
            tile_matrix[5][17] = materials_map.get("1");
            tile_matrix[6][17] = materials_map.get("1");
            tile_matrix[7][17] = materials_map.get("1");
        }
        if (bottom == false) {
//            {11, 7}, {11, 8}, {11, 9}, {11, 10},
            tile_matrix[11][7] = materials_map.get("1");
            tile_matrix[11][8] = materials_map.get("1");
            tile_matrix[11][9] = materials_map.get("1");
            tile_matrix[11][10] = materials_map.get("1");
        }

        if (left == false) {
//            {4, 0}, {5, 0}, {6, 0}, {7, 0}
            tile_matrix[4][0] = materials_map.get("1");
            tile_matrix[5][0] = materials_map.get("1");
            tile_matrix[6][0] = materials_map.get("1");
            tile_matrix[7][0] = materials_map.get("1");
        }
    }

    public void CloseGates() {
        tile_matrix[0][7] = materials_map.get("1");
        tile_matrix[0][8] = materials_map.get("1");
        tile_matrix[0][9] = materials_map.get("1");
        tile_matrix[0][10] = materials_map.get("1");
        tile_matrix[4][17] = materials_map.get("1");
        tile_matrix[5][17] = materials_map.get("1");
        tile_matrix[6][17] = materials_map.get("1");
        tile_matrix[7][17] = materials_map.get("1");
        tile_matrix[11][7] = materials_map.get("1");
        tile_matrix[11][8] = materials_map.get("1");
        tile_matrix[11][9] = materials_map.get("1");
        tile_matrix[11][10] = materials_map.get("1");
        tile_matrix[4][0] = materials_map.get("1");
        tile_matrix[5][0] = materials_map.get("1");
        tile_matrix[6][0] = materials_map.get("1");
        tile_matrix[7][0] = materials_map.get("1");
    }

    public void OpenAllGates() {
        tile_matrix[0][7] = materials_map.get("0");
        tile_matrix[0][8] = materials_map.get("0");
        tile_matrix[0][9] = materials_map.get("0");
        tile_matrix[0][10] = materials_map.get("0");
        tile_matrix[4][17] = materials_map.get("0");
        tile_matrix[5][17] = materials_map.get("0");
        tile_matrix[6][17] = materials_map.get("0");
        tile_matrix[7][17] = materials_map.get("0");
        tile_matrix[11][7] = materials_map.get("0");
        tile_matrix[11][8] = materials_map.get("0");
        tile_matrix[11][9] = materials_map.get("0");
        tile_matrix[11][10] = materials_map.get("0");
        tile_matrix[4][0] = materials_map.get("0");
        tile_matrix[5][0] = materials_map.get("0");
        tile_matrix[6][0] = materials_map.get("0");
        tile_matrix[7][0] = materials_map.get("0");
    }

    public void SetIsLast(boolean value) {
        isLast = value;
        entities.clear();
    }

    public void SetIsEmpty(boolean value) {
        isEmpty = value;
    }

    public ArrayList<Entity> GetEnemies() {
        return entities;
    }
    
    public ArrayList<Entity> GetProjectiles() {
        return projectileList;
    }

    public boolean isLast() {
        return isLast;
    }

    public boolean isExplored() {
        return explored;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public int GetValue() {
        return value;
    }

    public Tile[][] getMatrix() {
        return tile_matrix;
    }
    
     private void StartEnemiesBrains() {
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).start();
        }
    }

    public void Write() {
        String result = "";
        for (int r = 0; r < 12; r++) {
            for (int c = 0; c < 18; c++) {
                result += matrix[r][c];
                result += "-";
            }
            result += "\n";
        }
        System.out.println(result);
    }

    public void AddEntity(Entity e) {
        entities.add(e);
    }
    
    public void RemoveEntity(Entity e) {
        entities.remove(e);
    }
}
