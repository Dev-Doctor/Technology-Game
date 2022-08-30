/**
 * @author DevDoctor
 * @version 1.0
 * @file Floor.java
 *
 * @brief The Floor class file
 *
 */
package main.Java.world;

import java.awt.Graphics2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Java.DefaultValues;
import static main.Java.DefaultValues.Random;
import main.Java.GamePanel;
import main.Java.entities.Entity;
import org.json.*;

/**
 * @class Floor
 *
 * @brief The Floor class
 *
 * Manages the dungeon floor
 */

public class Floor {

    /**Object for JSON loading*/
    JSONObject jo;
    GamePanel gp;
    
    /**Current room tile matrix*/
    Room[][] rooms;
    /**Current room value on the map*/
    int[][] rooms_value;

    /**Current room cords*/
    int[] curr_room_cords;
    /**Exit room cords*/
    int[] last_room_cords;

    /**Current room*/
    Room current_room;
    /**Total number of rooms on the current floor*/
    public int tot_rooms;
    /**Current loaded theme*/
    String theme;
    
    /**Floor size*/
    int size = 7;

    public Floor(GamePanel gp, String theme) {
        this.gp = gp;
        this.theme = theme;
        rooms = new Room[7][7];
        rooms_value = new int[7][7];
        current_room = null;
        tot_rooms = -1;
    }
    
    
    /**
     * @brief Load the Floor
     * 
     * Loads the current room and sets the default values
     */
    public void Load() {
        LoadMapFromJSON();
        current_room.Load(theme);
        DefineRoomGates();
        current_room.isEmpty = true;
        rooms[last_room_cords[0]][last_room_cords[1]].isEmpty = true;
    }

    /**
     * @brief load the map from JSON
     */
    private void LoadMapFromJSON() {
        String json = "";
        try {
            json = Files.readString(Path.of("src\\main\\resources\\data\\dungeon\\default_floor.json"));
        } catch (IOException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }

        jo = new JSONObject(json);
        JSONArray json_map = jo.getJSONArray("floor");

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                rooms_value[r][c] = Integer.parseInt(json_map.getString(r).substring(c, c + 1));
                rooms[r][c] = new Room(gp, rooms_value[r][c]);

                if (rooms[r][c].GetValue() == 1) {
                    current_room = rooms[r][c];
                    curr_room_cords = new int[]{r, c};
                }

                if (rooms_value[r][c] != 0 && rooms_value[r][c] != 1) {
                    tot_rooms++;
                }
            }
        }

        DefineLastRoom();
        rooms[last_room_cords[0]][last_room_cords[1]].SetIsLast(true);
        System.out.println(last_room_cords[0] + " " + last_room_cords[1]);
    }
    /**
     * @brief Define Room Gates
     * 
     * Closes the close exit gates from the room by reading the tile map
     */
    private void DefineRoomGates() {
        boolean top = false, right = false, bottom = false, left = false;
        if (current_room.isEmpty == true) {
            current_room.OpenAllGates();
        }
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
    
    /**
     * @brief Change to the next room
     * 
     * Changes the room from the current one to the next one according to the value passed as parameter.
     * Loads it and it empty the current room.
     * @param what the next room location according to the current room
     */
    public void ChangeRoom(String what) {
        switch (what) {
            case "up":
                gp.GetPlayer().SetNewCoordinates(9 * DefaultValues.tileSize - DefaultValues.tileSize / 2, 9 * DefaultValues.tileSize);
                current_room = rooms[curr_room_cords[0] - 1][curr_room_cords[1]];
                curr_room_cords = new int[]{curr_room_cords[0] - 1, curr_room_cords[1]};
                break;
            case "right":
                gp.GetPlayer().SetNewCoordinates(2 * DefaultValues.tileSize, 6 * DefaultValues.tileSize - DefaultValues.tileSize / 2);
                current_room = rooms[curr_room_cords[0]][curr_room_cords[1] + 1];
                curr_room_cords = new int[]{curr_room_cords[0], curr_room_cords[1] + 1};
                break;
            case "bottom":
                gp.GetPlayer().SetNewCoordinates(9 * DefaultValues.tileSize - DefaultValues.tileSize / 2, 2 * DefaultValues.tileSize);
                current_room = rooms[curr_room_cords[0] + 1][curr_room_cords[1]];
                curr_room_cords = new int[]{curr_room_cords[0] + 1, curr_room_cords[1]};
                break;
            case "left":
                gp.GetPlayer().SetNewCoordinates(15 * DefaultValues.tileSize, 6 * DefaultValues.tileSize - DefaultValues.tileSize / 2);
                current_room = rooms[curr_room_cords[0]][curr_room_cords[1] - 1];
                curr_room_cords = new int[]{curr_room_cords[0], curr_room_cords[1] - 1};
                break;
            case "floor":
                gp.GetWorld().ChangeFloor();
                break;
            default:
                throw new AssertionError();
        }
        current_room.Load(theme);
        DefineRoomGates();
        if (current_room.isEmpty == false) {
            current_room.CloseGates();
            gp.GetPlayer().RoomExplored++;
            gp.GetPlayer().TotRoomExplored++;
        }
        gp.GetWorld().GetCurrentRoom();
    }

    /**
     * @brief Updates the room enemies
     * 
     * Updates the room enemies, it see if they are alive and if they don't it removes them from the ArrayList
     */
    public void UpdateRoomEnemies() {
        ArrayList<Entity> entities = current_room.GetEnemies();
        if (gp.GetWorld().GetCurrentRoom().isEmpty == false) {
            for (int i = 0; i < entities.size(); i++) {
                if (!entities.get(i).alive) {
                    gp.GetSoundManager().PlaySound(entities.get(i).DeathSound);
                    entities.remove(i);
                    gp.GetPlayer().EnemyKilled++;
                }
            }
            if (gp.GetWorld().GetCurrentRoom().GetEnemies().isEmpty()) {
                gp.GetWorld().GetCurrentFloor().UnlockRoom();
            }
        }
    }

    /**
     * @brief Update Room Projectiles
     * 
     * Update the current "alive" projectiles in the room
     */
    public void UpdateRoomProjectiles() {
        ArrayList<Entity> projectileList = current_room.GetProjectiles();
        for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i).alive) {
                    projectileList.get(i).update();
                }
                if (!projectileList.get(i).alive) {
                    projectileList.remove(i);
                }
            }
    }
    
    /**
     * @brief Draws the enemies on the screen
     * 
     * @param gra2 
     */
    public void DrawRoomEnemies(Graphics2D gra2) {
        ArrayList<Entity> entities = current_room.GetEnemies();
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).draw(gra2);
        }
    }
    
    /**
     * @brief Draws the projectiles on the screen
     * 
     * @param gra2 
     */
    public void DrawRoomProjectiles(Graphics2D gra2) {
        ArrayList<Entity> projectileList = current_room.GetProjectiles();
        for (int i = 0; i < projectileList.size(); i++) {
            projectileList.get(i).draw(gra2);
        }
    }
    
    /**
     * @brief Define the last room
     * 
     * defines the last room of the dungeon by looking at the hightest number on them map and then randomizing it if it's more than one room 
     */
    public void DefineLastRoom() {
        ArrayList<int[]> last_rooms = new ArrayList<int[]>();
        int current_last_room = -1;

        for (int r = 0; r < 7; r++) {
            for (int c = 0; c < 7; c++) {
                if (current_last_room < rooms_value[r][c]) {
                    current_last_room = rooms_value[r][c];
                }
            }
        }

        for (int r = 0; r < 7; r++) {
            for (int c = 0; c < 7; c++) {
                if (rooms_value[r][c] == current_last_room) {
                    last_rooms.add(new int[]{r, c});
                }
            }
        }

        int selected = Random(0, last_rooms.size() - 1);
        last_room_cords = last_rooms.get(selected);
    }
    
    /**
     * @brief Unlocks the current room
     */
    public void UnlockRoom() {
        current_room.SetIsEmpty(true);
        DefineRoomGates();
    }

    public int[] getCurr_room_cords() {
        return curr_room_cords;
    }

    public Room[][] getRoomsMatrix() {
        return rooms;
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
    public int getSize() {
        return size;
    }

}
