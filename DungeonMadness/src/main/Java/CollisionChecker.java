package main.Java;

import main.Java.entities.Entity;
import main.Java.world.Tile;

/** @author Jifrid - DevDoctor */
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
}
