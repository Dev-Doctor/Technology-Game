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
    
    public int checkEntity(Entity entity, Entity[] target){
        int index = 999;
        
        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                
                entity.solidArea.x = entity.GetX() + entity.solidArea.x;
                entity.solidArea.y = entity.GetY() + entity.solidArea.y;
                
                target[i].solidArea.x = target[i].GetX() + target[i].solidArea.x;
                target[i].solidArea.y = target[i].GetY() + target[i].solidArea.y;
                
                switch(entity.direction){
                case "up":
                    entity.solidArea.y -= entity.speed;
                    if (entity.solidArea.intersects(target[i].solidArea)) {
                        entity.collisionOn = true;
                        index = i;
                    }
                    break;
                case "down":
                    entity.solidArea.y += entity.speed;
                    if (entity.solidArea.intersects(target[i].solidArea)) {
                        entity.collisionOn = true;
                        index = i;
                    }
                    break;
                case "left":
                    entity.solidArea.x -= entity.speed;
                    if (entity.solidArea.intersects(target[i].solidArea)) {
                        entity.collisionOn = true;
                        index = i;
                    }
                    break;
                case "right":
                    entity.solidArea.x += entity.speed;
                    if (entity.solidArea.intersects(target[i].solidArea)) {
                        entity.collisionOn = true;
                        index = i;
                        break;
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
    
    public void checkPlayer(Entity entity){
        entity.solidArea.x = entity.GetX() + entity.solidArea.x;
                entity.solidArea.y = entity.GetY() + entity.solidArea.y;
                
                gp.pl.solidArea.x = gp.pl.GetX() + gp.pl.solidArea.x;
                gp.pl.solidArea.y = gp.pl.GetY() + gp.pl.solidArea.y;
                
                switch(entity.direction){
                case "up":
                    entity.solidArea.y -= entity.speed;
                    if (entity.solidArea.intersects(gp.pl.solidArea)) {
                        entity.collisionOn = true;
                    }
                    break;
                case "down":
                    entity.solidArea.y += entity.speed;
                    if (entity.solidArea.intersects(gp.pl.solidArea)) {
                        entity.collisionOn = true;
                    }
                    break;
                case "left":
                    entity.solidArea.x -= entity.speed;
                    if (entity.solidArea.intersects(gp.pl.solidArea)) {
                        entity.collisionOn = true;
                    }
                    break;
                case "right":
                    entity.solidArea.x += entity.speed;
                    if (entity.solidArea.intersects(gp.pl.solidArea)) {
                        entity.collisionOn = true;
                        break;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.pl.solidArea.x = gp.pl.solidAreaDefaultX;
                gp.pl.solidArea.y = gp.pl.solidAreaDefaultY;
    }
}
