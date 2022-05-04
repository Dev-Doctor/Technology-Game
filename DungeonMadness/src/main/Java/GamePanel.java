package main.Java;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import main.Java.world.Tile;
import main.Java.world.World;
import main.Java.entities.Player;
import main.Java.world.TileManager;

/** @author DevDoctor */
public class GamePanel extends JPanel implements Runnable {

    public final int TileResolution = 32; //32x32 pixel
    public final int scale = 2;

    public final int tileSize = TileResolution * scale; //actual tile size
    public final int MaxRowsTiles = 12;
    public final int MaxColTiles = 18;

    public final int WindowHeight = MaxRowsTiles * tileSize;
    public final int WindowWidth = MaxColTiles * tileSize;

    public CollisionChecker collisionChecker;
    private final int FPS = 60;
    
    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    Player pl;
    World world;
    Tile[][] matrix; // TEMPORARY
    
    TileManager tileManager = new TileManager(this);
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(WindowWidth, WindowHeight));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        
        this.collisionChecker = new CollisionChecker(this);
        this.matrix = new Tile[MaxRowsTiles][MaxColTiles];
        
        pl = new Player(this, keyHandler);
        world = new World(this);
        
        /** !!! TEMPORARY !!! **/
        world.LoadRoom();
        matrix = world.GetTileMatrix();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        float drawCount = 0;
        
        while(gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            
            if(delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            
            if(timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
            
        }
    }

    private void update() {
        pl.update();
    }
    
    public void paintComponent(Graphics gra) {
        super.paintComponent(gra);
        Graphics2D gra2 = (Graphics2D) gra;
        // FIRST DRAW
        if(world.DrawMap) {
            tileManager.DrawMap(matrix, gra2);
//            world.DrawMap = false;
        }
        pl.draw(gra2);
        
        // LAST DRAW
        
        gra2.dispose();
    }
    
    
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    public Tile[][] getRoomMatrix() {
        return matrix;
    }
}
