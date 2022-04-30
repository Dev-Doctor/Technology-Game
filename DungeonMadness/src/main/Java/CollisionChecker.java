package main.Java;

import main.java.entities.Entity;

public class CollisionChecker{

    GamePanel gp;
    
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }    
    
    public void checkTile(Entity entity){
        int entityLeftX = entity.GetX() + entity.solidArea.x;
        int entityRightX = entity.GetX() + entity.solidArea.x + entity.solidArea.width;
        int entityTopY = entity.GetY() + entity.solidArea.y;
        int entityBottomY = entity.GetY() + entity.solidArea.y + entity.solidArea.height;
    
        int entityLeftCol = entityLeftX/gp.tileSize;
        int entityRightCol = entityRightX/gp.tileSize;
        int entityTopRow = entityTopY/gp.tileSize;
        int entityBottomRow = entityBottomY/gp.tileSize;
        
        int tileNum1, tileNum2;
        
        switch(entity.direction){
            case "up":
                entityTopRow = (entityTopY - entity.GetSpeed())/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileManager.tile[tileNum1].Collision == true || gp.tileManager.tile[tileNum2].Collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomY + entity.GetSpeed())/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileManager.tile[tileNum1].Collision == true || gp.tileManager.tile[tileNum2].Collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftX - entity.GetSpeed())/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileManager.tile[tileNum1].Collision == true || gp.tileManager.tile[tileNum2].Collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightX + entity.GetSpeed())/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileManager.tile[tileNum1].Collision == true || gp.tileManager.tile[tileNum2].Collision == true) {
                    entity.collisionOn = true;
                }
                break;
        }
    }
}
