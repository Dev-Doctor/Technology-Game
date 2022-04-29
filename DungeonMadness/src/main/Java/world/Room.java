package main.Java.world;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;

/** @author DevDoctor */
public class Room {

    final String tile_loc = "src\\main\\resources\\data\\tile";
    List<String> AllFiles;

    JSONObject jo;
    HashMap<String, Tile> materials_map;

    Tile[][] tile_matrix;
    int[][] matrix;

    public Room() {
        this.materials_map = new HashMap<String, Tile>();
        this.tile_matrix = new Tile[12][18];
        this.matrix = new int[12][18];
        GetJSONfiles();
    }

    public void Load(String type) {
        String json = "";
        try {
            json = Files.readString(Path.of("src\\main\\resources\\data\\dungeon\\default.json"));
        } catch (IOException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }

        jo = new JSONObject(json);
        JSONArray json_map = jo.getJSONArray("map");

        for (int r = 0; r < 12; r++) {
            for (int c = 0; c < 18; c++) {
                matrix[r][c] = Integer.parseInt(json_map.getString(r).substring(c, c + 1));
            }
        }

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
                materials_map.put(key, new Tile(tl.getBoolean("collision"), tl.getString("texture")));
            } else {
                materials_map.put(key, new Tile());
            }
        }
        CreateTileMatrix();
    }

    private void CreateTileMatrix() {
        for (int r = 0; r < 12; r++) {
            for (int c = 0; c < 18; c++) {
                tile_matrix[r][c] = materials_map.get(String.valueOf(matrix[r][c]));
            }
        }
    }

    private void GetJSONfiles() {
        File fl = new File(tile_loc);
        AllFiles = Arrays.asList(fl.list());
    }

//    public void Write() {
//        String result = "";
//        for (int r = 0; r < 12; r++) {
//            for (int c = 0; c < 18; c++) {
//                result += matrix[r][c];
//                result += "-";
//            }
//            result += "\n";
//        }
//        System.out.println(result);
//    }
    public Tile[][] getMatrix() {
        return tile_matrix;
    }

}
