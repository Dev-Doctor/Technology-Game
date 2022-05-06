package main.Java;

import main.Java.entities.Entity;
import main.Java.entities.Player;
import main.Java.object.SuperObject;
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
        int EntityLeftWorldX = entity.GetX() + entity.solidArea.x;
        int EntityRightWorldX = entity.GetX() + entity.solidArea.x + entity.solidArea.width;

        int EntityTopWorldY = entity.GetY() + entity.solidArea.y;
        int EntityBottomWorldY = entity.GetY() + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = EntityLeftWorldX / gp.tileSize;
        int entityRightCol = EntityRightWorldX / gp.tileSize;
        int entityTopRow = EntityTopWorldY / gp.tileSize;
        int entityBottomRow = EntityBottomWorldY / gp.tileSize;

        Tile tile_1 = null, tile_2 = null;

        Tile[][] room = gp.getRoomMatrix();

        switch (entity.direction) {
            case "up":
                entityTopRow = (EntityTopWorldY - entity.GetSpeed()) / gp.tileSize;
                tile_1 = room[entityTopRow][entityLeftCol];
                tile_2 = room[entityTopRow][entityRightCol];
                break;
            case "down":
                entityBottomRow = (EntityBottomWorldY + entity.GetSpeed()) / gp.tileSize;
                tile_1 = room[entityBottomRow][entityLeftCol];
                tile_2 = room[entityBottomRow][entityRightCol];
                break;
            case "left":
                entityLeftCol = (EntityLeftWorldX - entity.GetSpeed()) / gp.tileSize;
                tile_1 = room[entityTopRow][entityLeftCol];
                tile_2 = room[entityBottomRow][entityRightCol];
                break;
            case "right":
                entityRightCol = (EntityRightWorldX + entity.GetSpeed()) / gp.tileSize;
                tile_1 = room[entityTopRow][entityRightCol];
                tile_2 = room[entityBottomRow][entityRightCol];
                break;
            default:
                throw new AssertionError();
        }

        if (tile_1.Collision == true || tile_2.Collision == true) {
            entity.collisionOn = true;
        }

//        System.out.println("LeftWorldX: " + EntityLeftWorldX + " RightWorldX: " + EntityRightWorldX);
//        System.out.println("TopWorldY: " + EntityTopWorldY + " BottomWorldY: " + EntityBottomWorldY);
//        System.out.println("--------------------------------------------------------------------");
    }
    
    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;

        for (int i = 0; i < target.length; i++) {

            if (target[i] != null) {

                entity.solidArea.x += entity.GetX();
                entity.solidArea.y += entity.GetY();

                target[i].solidArea.x += target[i].GetX();
                target[i].solidArea.y += target[i].GetY();

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

                if (entity.solidArea.intersects(target[i].solidArea)) {
                    if (target[i] != entity) {
                        entity.collisionOn = true;
                        index = i;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
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
}
