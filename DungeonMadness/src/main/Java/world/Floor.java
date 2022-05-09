/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Java.world;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Java.DefaultValues;
import main.Java.GamePanel;
import org.json.*;

/** @author DevDoctor */
public class Floor {

    JSONObject jo;
    GamePanel gp;

    Room[][] rooms;
    int[][] rooms_value;
    int[] curr_room_cords;
    Room current_room;
    int tot_rooms;

    public Floor(GamePanel gp) {
        this.gp = gp;
        rooms = new Room[7][7];
        rooms_value = new int[7][7];
        current_room = null;
        tot_rooms = -1;
    }

    public void Load(String theme) {
        LoadMapFromJSON();
        current_room.Load("default");
        DefineRoomGates();
    }

    private void LoadMapFromJSON() {
        String json = "";
        try {
            json = Files.readString(Path.of("src\\main\\resources\\data\\dungeon\\default_floor.json"));
        } catch (IOException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }

        jo = new JSONObject(json);
        JSONArray json_map = jo.getJSONArray("floor");

        for (int r = 0; r < 7; r++) {
            for (int c = 0; c < 7; c++) {
                rooms_value[r][c] = Integer.parseInt(json_map.getString(r).substring(c, c + 1));
                rooms[r][c] = new Room(gp, rooms_value[r][c]);
                if (rooms[r][c].GetValue() == 1) {
                    current_room = rooms[r][c];
                    curr_room_cords = new int[]{r, c};
                }
            }
        }
    }

    private void DefineRoomGates() {
        boolean top = true, right = true, bottom = true, left = true;
        if (curr_room_cords[0] - 1 < 0
                || rooms_value[curr_room_cords[0] - 1][curr_room_cords[1]] == 0 || (rooms_value[curr_room_cords[0] - 1][curr_room_cords[1]] != current_room.value + 1
                && rooms_value[curr_room_cords[0] - 1][curr_room_cords[1]] != current_room.value - 1)) {
            top = false;
        }
        if (curr_room_cords[1] + 1 >= rooms_value.length
                || rooms_value[curr_room_cords[0]][curr_room_cords[1] + 1] == 0 || (rooms_value[curr_room_cords[0]][curr_room_cords[1] + 1] != current_room.value + 1
                && rooms_value[curr_room_cords[0]][curr_room_cords[1] + 1] != current_room.value - 1)) {
            right = false;
        }
        if (curr_room_cords[0] + 1 >= rooms_value.length
                || rooms_value[curr_room_cords[0] + 1][curr_room_cords[1]] == 0 || (rooms_value[curr_room_cords[0] + 1][curr_room_cords[1]] != current_room.value + 1
                && rooms_value[curr_room_cords[0] + 1][curr_room_cords[1]] != current_room.value - 1)) {
            bottom = false;
        }
        if (curr_room_cords[1] - 1 < 0
                || rooms_value[curr_room_cords[0]][curr_room_cords[1] - 1] == 0 || (rooms_value[curr_room_cords[0]][curr_room_cords[1] - 1] != current_room.value + 1
                && rooms_value[curr_room_cords[0]][curr_room_cords[1] - 1] != current_room.value - 1)) {
            left = false;
        }
        current_room.DefineGates(top, right, bottom, left);
    }

    public Room GetCurrentRoom() {
        return current_room;
    }

    public void ChangeRoom(String what) {
        switch (what) {
            case "up":
                gp.GetPlayer().SetNewCordinates(9 * DefaultValues.tileSize - DefaultValues.tileSize / 2, 9 * DefaultValues.tileSize);
                current_room = rooms[curr_room_cords[0] - 1][curr_room_cords[1]];
                curr_room_cords = new int[]{curr_room_cords[0] - 1, curr_room_cords[1]};
                break;
            case "right":
                gp.GetPlayer().SetNewCordinates(2 * DefaultValues.tileSize, 6 * DefaultValues.tileSize - DefaultValues.tileSize / 2);
                current_room = rooms[curr_room_cords[0]][curr_room_cords[1] + 1];
                curr_room_cords = new int[]{curr_room_cords[0], curr_room_cords[1] + 1};
                break;
            case "bottom":
                gp.GetPlayer().SetNewCordinates(9 * DefaultValues.tileSize - DefaultValues.tileSize / 2, 2 * DefaultValues.tileSize);
                current_room = rooms[curr_room_cords[0] + 1][curr_room_cords[1]];
                curr_room_cords = new int[]{curr_room_cords[0] + 1, curr_room_cords[1]};
                break;
            case "left":
                gp.GetPlayer().SetNewCordinates(15 * DefaultValues.tileSize, 6 * DefaultValues.tileSize - DefaultValues.tileSize / 2);
                current_room = rooms[curr_room_cords[0]][curr_room_cords[1] - 1];
                curr_room_cords = new int[]{curr_room_cords[0], curr_room_cords[1] - 1};
                break;
            default:
                throw new AssertionError();
        }
        current_room.Load("default");
        DefineRoomGates();
        gp.GetWorld().GetCurrentRoom();
    }

//    public void Write() {
//        String result = "";
//        for (int r = 0; r < 7; r++) {
//            for (int c = 0; c < 7; c++) {
//                result += rooms_value[r][c];
//                result += "-";
//            }
//            result += "\n";
//        }
//        System.out.println(result);
//    }
}
