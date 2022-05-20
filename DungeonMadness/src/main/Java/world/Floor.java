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
 * @author DevDoctor
 */
public class Floor {

    JSONObject jo;
    GamePanel gp;

    Room[][] rooms;
    int[][] rooms_value;

    int[] curr_room_cords;
    int[] last_room_cords;

    Room current_room;
    public int tot_rooms;
    String theme;

    int size = 7;

    public Floor(GamePanel gp, String theme) {
        this.gp = gp;
        this.theme = theme;
        rooms = new Room[7][7];
        rooms_value = new int[7][7];
        current_room = null;
        tot_rooms = -1;
    }

    public void Load() {
        LoadMapFromJSON();
        current_room.Load(theme);
        DefineRoomGates();
        current_room.isEmpty = true;
        rooms[last_room_cords[0]][last_room_cords[1]].isEmpty = true;
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

    private void DefineRoomGates() {
        boolean top = true, right = true, bottom = true, left = true;
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

    public void UpdateRoomEnemies() {
        ArrayList<Entity> entities = current_room.GetEnemies();
        if (gp.GetWorld().GetCurrentRoom().isEmpty == false) {
            for (int i = 0; i < entities.size(); i++) {
                if (!entities.get(i).alive) {
                    entities.remove(i);
                    gp.GetPlayer().EnemyKilled++;
                }
            }
            if (gp.GetWorld().GetCurrentRoom().GetEnemies().isEmpty()) {
                gp.GetWorld().GetCurrentFloor().UnlockRoom();
            }
        }
    }

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

    public void DrawRoomEnemies(Graphics2D gra2) {
        ArrayList<Entity> entities = current_room.GetEnemies();
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).draw(gra2);
        }
    }
    
    public void DrawRoomProjectiles(Graphics2D gra2) {
        ArrayList<Entity> projectileList = current_room.GetProjectiles();
        for (int i = 0; i < projectileList.size(); i++) {
            projectileList.get(i).draw(gra2);
        }
    }

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
