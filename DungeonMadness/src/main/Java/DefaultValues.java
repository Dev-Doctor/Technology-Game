/**
* @author  DevDoctor
* @version 1.0
* @file DefaultValues.java 
* 
* @brief Stores values
* 
* stores the default and costant values used by every class
* 
*/
package main.Java;

import java.util.concurrent.ThreadLocalRandom;

/** @author DevDoctor */
public class DefaultValues {
    
    /** Resolution in pixels 32x32 */
    public static final int TileResolution = 32; //32x32 pixel
    
    /** scale to increase the resolution */
    public static final int scale = 2;

    /** actual size of every single tile on the screen */
    public static final int tileSize = TileResolution * scale;
    /** amount of tile rows on the screen */
    public static final int MaxRowsTiles = 12;
    /** amount of tile columns on the screen */
    public static final int MaxColTiles = 18;

    /** height of the screen in pixels */
    public static final int WindowHeight = MaxRowsTiles * tileSize;
    /** width of the screen in pixels */
    public static final int WindowWidth = MaxColTiles * tileSize;
    
    /** CHEAT: show the hitboxes */
    public static boolean showHitboxes = false;
    /** CHEAT: player immortal */
    public static boolean isImmortal = false;
    /** CHEAT: deactivate the "AI" */
    public static boolean deactiveEI = false;

    /** spawn point player cord X */
    public static final int PlDefaultX = tileSize * (MaxColTiles / 2) - tileSize / 2;
    /** spawn point player cord Y */
    public static final int PlDefaultY = tileSize * (MaxRowsTiles / 2) - tileSize / 2; // Default Player Y cord
    
    /** themes location */
    public static final String themes_location = "src/main/"; // TEMPORARY
    
    
    /**
     * @param int mn: min value
     * @param int mx: max value
     * @return a random value between mn and mx
     */
    static public int Random(int mn, int mx) {
        return ThreadLocalRandom.current().nextInt(mn, mx + 1);
    }
}
