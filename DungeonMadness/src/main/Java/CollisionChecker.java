/**
* @author  Jifrid, DevDoctor
* @version 1.0
* @file CollisionChecker.java 
* 
* @brief Manage collisions
* 
* Manage all the collisions for all the entities, objects and tiles
* 
*/
package main.Java;

import java.util.ArrayList;
import main.Java.entities.Entity;
import main.Java.world.Tile;

/** 
* @class CollisionChecker
* 
* @brief Manage collisions
* 
* Manage all the collisions for all the entities, objects and tiles
*/ 
public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }
    
    /**
     * @brief check collisions with world
     * 
     * Check the collisions with the world for the entity passed as parameter.
     * If the entity is an enemy or the player he collides with the wall.
     * If it is a projectile it will be destroyed
     * Contains the magic souts!!!
     * @param entity the entity to check
     */
    synchronized public void checkTile(Entity entity) {
        
        /* MAGIC SOUT */
        System.out.println("");
        /* END OF MAGIC SOUT */
        
        int EntityLeftWorldX = entity.GetX() + entity.solidArea.x;
        int EntityRightWorldX = entity.GetX() + entity.solidArea.x + entity.solidArea.width;

        int EntityTopWorldY = entity.GetY() + entity.solidArea.y;
        int EntityBottomWorldY = entity.GetY() + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = EntityLeftWorldX / DefaultValues.tileSize;
        int entityRightCol = EntityRightWorldX / DefaultValues.tileSize;
        int entityTopRow = EntityTopWorldY / DefaultValues.tileSize;
        int entityBottomRow = EntityBottomWorldY / DefaultValues.tileSize;

        /* OTHER MAGIC SOUT */
        System.out.println("");
        /* END OF OTHER MAGIC SOUT */
        
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
    }

    /**
     * @brief check the passed entity collisions with other enemies
     * 
     * check the passed entity collisions with all the other entities in the current room
     * @param entity the entity to check
     * @param target all the enemies in the current room
     * @return the position of the current colliding entity in the ArrayList
     */
    synchronized public int checkEntity(Entity entity, ArrayList<Entity> target) {
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
    /**
     * @brief check the passed Entity collisions with the objects
     * 
     * check the Player collisions with all the objects
     * If the object has tile collision, the Player collides with the object and returns the position of the object in the ArrayList
     * If it hasn't, it just returns the position of the object in the ArrayList
     * @param player check the player
     * @return the position of the object in the ArrayList
     */
    synchronized public int checkObject(Entity player) {
        int index = 999;

        for (int i = 0; i < gp.obj.size(); i++) {

            if (gp.obj.get(i) != null) {

                player.solidArea.x += player.GetX();
                player.solidArea.y += player.GetY();

                gp.obj.get(i).solidArea.x += gp.obj.get(i).GetX();
                gp.obj.get(i).solidArea.y += gp.obj.get(i).GetY();

                switch (player.direction) {
                    case "up":
                        player.solidArea.y -= player.speed;
                        break;
                    case "down":
                        player.solidArea.y += player.speed;
                        break;
                    case "left":
                        player.solidArea.x -= player.speed;
                        break;
                    case "right":
                        player.solidArea.x += player.speed;
                        break;
                }

                if (player.solidArea.intersects(gp.obj.get(i).solidArea)) {
                    if (gp.obj.get(i).collision) {
                        player.collisionOn = true;
                    }

                    index = i;
                }

                player.solidArea.x = player.solidAreaDefaultX;
                player.solidArea.y = player.solidAreaDefaultY;
                gp.obj.get(i).solidArea.x = gp.obj.get(i).solidAreaDefaultX;
                gp.obj.get(i).solidArea.y = gp.obj.get(i).solidAreaDefaultY;
            }

        }
        return index;
    }

    /**
     * @brief check the passed Entity collisions with the player
     * 
     * check the passed Entity collisions with the player.
     * If it collide it activates the collisions
     * @param entity entity to check
     * @return if the entity is colliding with the player returns true else false
     */
    synchronized public boolean checkPlayer(Entity entity) {

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
    
    /***
     * @brief Check the collisions with the "doors"
     * 
     * Check the collisions with the "exits" from the room.
     * If the entity that is colliding is a player it changes the room to the next one in that direction
     * else if the entity is a projectile it destroys it
     * @param entity entity to check
     */
    synchronized public void checkBorder(Entity entity) {
        entity.solidArea.x += entity.GetX();
        entity.solidArea.y += entity.GetY();
        
       if (entity.type == 3 && (entity.solidArea.intersects(gp.world.GetCurrentRoom().hit_top)
                || entity.solidArea.intersects(gp.world.GetCurrentRoom().hit_right)
                || entity.solidArea.intersects(gp.world.GetCurrentRoom().hit_bottom)
                || entity.solidArea.intersects(gp.world.GetCurrentRoom().hit_left))) {
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
