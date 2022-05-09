/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.Java;

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

    public static final int PlDefaultX = tileSize * (MaxColTiles / 2) - tileSize / 2; // Default Player X cord
    public static final int PlDefaultY = tileSize * (MaxRowsTiles / 2) - tileSize / 2; // Default Player Y cord
}
