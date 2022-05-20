package main.Java;

import java.util.ArrayList;
import main.Java.entities.Entity;
import main.Java.entities.Player;
import main.Java.world.Tile;

/**
 * @author Jifrid - DevDoctor
 */
public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        
        /* SOUT MAGICA */
        System.out.println("");
        /* FINE SOUT MAGICA */
        
        int EntityLeftWorldX = entity.GetX() + entity.solidArea.x;
        int EntityRightWorldX = entity.GetX() + entity.solidArea.x + entity.solidArea.width;

        int EntityTopWorldY = entity.GetY() + entity.solidArea.y;
        int EntityBottomWorldY = entity.GetY() + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = EntityLeftWorldX / DefaultValues.tileSize;
        int entityRightCol = EntityRightWorldX / DefaultValues.tileSize;
        int entityTopRow = EntityTopWorldY / DefaultValues.tileSize;
        int entityBottomRow = EntityBottomWorldY / DefaultValues.tileSize;

        /* L'ALTRA SOUT MAGICA */
        System.out.println("");
        /* FINE DELL'ALTRA SOUT MAGICA */
        
        Tile tile_1 = null, tile_2 = null;

        Tile[][] room = gp.GetWorld().GetCurrentRoom().getMatrix();

        switch (entity.direction) {
            case "up":
                entityTopRow = (EntityTopWorldY - entity.GetSpeed()) / DefaultValues.tileSize;
                tile_1 = room[entityTopRow][entityLeftCol];
                tile_2 = room[entityTopRow][entityRightCol];
                break;
            case "down":
                entityBottomRow = (EntityBottomWorldY + entity.GetSpeed()) / DefaultValues.tileSize;
                tile_1 = room[entityBottomRow][entityLeftCol];
                tile_2 = room[entityBottomRow][entityRightCol];
                break;
            case "left":
                entityLeftCol = (EntityLeftWorldX - entity.GetSpeed()) / DefaultValues.tileSize;
                tile_1 = room[entityTopRow][entityLeftCol];
                tile_2 = room[entityBottomRow][entityRightCol];
                break;
            case "right":
                entityRightCol = (EntityRightWorldX + entity.GetSpeed()) / DefaultValues.tileSize;
                tile_1 = room[entityTopRow][entityRightCol];
                tile_2 = room[entityBottomRow][entityRightCol];
                break;
            default:
                throw new AssertionError();
        }

        if (tile_1.Collision == true || tile_2.Collision == true) {
            if (entity.getType() == 3) {
                entity.alive = false;
            }else{
                entity.collisionOn = true;
            }
        }

//        System.out.println("LeftWorldX: " + EntityLeftWorldX + " RightWorldX: " + EntityRightWorldX);
//        System.out.println("TopWorldY: " + EntityTopWorldY + " BottomWorldY: " + EntityBottomWorldY);
//        System.out.println("--------------------------------------------------------------------");
    }

    public int checkEntity(Entity entity, ArrayList<Entity> target) {
        int index = 999;

        for (int i = 0; i < target.size(); i++) {

            if (target.get(i) != null) {

                entity.solidArea.x += entity.GetX();
                entity.solidArea.y += entity.GetY();

                target.get(i).solidArea.x += target.get(i).GetX();
                target.get(i).solidArea.y += target.get(i).GetY();

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                }

                if (entity.solidArea.intersects(target.get(i).solidArea)) {
                    if (target.get(i) != entity) {
                        entity.collisionOn = true;
                        index = i;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target.get(i).solidArea.x = target.get(i).solidAreaDefaultX;
                target.get(i).solidArea.y = target.get(i).solidAreaDefaultY;
            }

        }
        return index;
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gp.obj.length; i++) {

            if (gp.obj[i] != null) {

                entity.solidArea.x += entity.GetX();
                entity.solidArea.y += entity.GetY();

                gp.obj[i].solidArea.x += gp.obj[i].GetX();
                gp.obj[i].solidArea.y += gp.obj[i].GetY();

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                }

                if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                    if (gp.obj[i].collision) {
                        entity.collisionOn = true;
                    }

                    index = i;

                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }

        }
        return index;
    }

    public boolean checkPlayer(Entity entity) {

        boolean contactPlayer = false;

        entity.solidArea.x += entity.GetX();
        entity.solidArea.y += entity.GetY();

        gp.pl.solidArea.x += gp.pl.GetX();
        gp.pl.solidArea.y += gp.pl.GetY();

        switch (entity.direction) {
            case "up":
                entity.solidArea.y -= entity.speed;
                break;
            case "down":
                entity.solidArea.y += entity.speed;
                break;
            case "left":
                entity.solidArea.x -= entity.speed;
                break;
            case "right":
                entity.solidArea.x += entity.speed;
                break;
        }

        if (entity.solidArea.intersects(gp.pl.solidArea)) {
            entity.collisionOn = true;
            contactPlayer = true;
        }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.pl.solidArea.x = gp.pl.solidAreaDefaultX;
        gp.pl.solidArea.y = gp.pl.solidAreaDefaultY;

        return contactPlayer;
    }

    public void checkBorder(Entity entity) {
        entity.solidArea.x += entity.GetX();
        entity.solidArea.y += entity.GetY();
        
        if(entity.type == 3 && (entity.solidArea.intersects(gp.world.GetCurrentRoom().hit_top) 
                || entity.solidArea.intersects(gp.world.GetCurrentRoom().hit_right) 
                || entity.solidArea.intersects(gp.world.GetCurrentRoom().hit_bottom))
                || entity.solidArea.intersects(gp.world.GetCurrentRoom().hit_left)) {
            entity.alive = false;
            return;
        }
        
        if (entity.solidArea.intersects(gp.world.GetCurrentRoom().hit_top)) {
            gp.world.GetCurrentFloor().ChangeRoom("up");
        }

        if (entity.solidArea.intersects(gp.world.GetCurrentRoom().hit_right)) {
            gp.world.GetCurrentFloor().ChangeRoom("right");
        }

        if (entity.solidArea.intersects(gp.world.GetCurrentRoom().hit_bottom)) {
            gp.world.GetCurrentFloor().ChangeRoom("bottom");
        }

        if (entity.solidArea.intersects(gp.world.GetCurrentRoom().hit_left)) {
            gp.world.GetCurrentFloor().ChangeRoom("left");
        }

        if (gp.world.GetCurrentRoom().isLast()) {
            if (entity.solidArea.intersects(gp.world.GetCurrentRoom().next_floor)) {
                gp.world.GetCurrentFloor().ChangeRoom("floor");
            }
        }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
    }
}
