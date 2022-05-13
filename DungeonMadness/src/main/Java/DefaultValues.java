package main.Java;

import java.util.concurrent.ThreadLocalRandom;

/** @author DevDoctor */
public class DefaultValues {
    
    
    public static final int TileResolution = 32; //32x32 pixel
    public static final int scale = 2;

    public static final int tileSize = TileResolution * scale; //actual tile size
    public static final int MaxRowsTiles = 12;
    public static final int MaxColTiles = 18;

    public static final int WindowHeight = MaxRowsTiles * tileSize;
    public static final int WindowWidth = MaxColTiles * tileSize;
    
    public static boolean showHitboxes = false;
    public static boolean isImmortal = true;
    public static boolean deactiveEI = false;

    public static final int PlDefaultX = tileSize * (MaxColTiles / 2) - tileSize / 2; // Default Player X cord
    public static final int PlDefaultY = tileSize * (MaxRowsTiles / 2) - tileSize / 2; // Default Player Y cord
    
    public static final String themes_location = "src/main/"; // TEMPORARY
    
    static public int Random(int mn, int mx) {
        return ThreadLocalRandom.current().nextInt(mn, mx + 1);
    }
}
