package main.java;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import main.Java.world.Tile;
import main.Java.world.World;
import main.java.entities.Player;
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

    public CollisionChecker collisionChecker = new CollisionChecker(this);
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
        pl = new Player(this, keyHandler);
        world = new World(this);
        world.LoadRoom(); // TEMPORARY
        
        matrix = new Tile[MaxRowsTiles][MaxColTiles];
        matrix = world.GetTileMatrix(); // TEMPORARY
    }

    @Override
    public void run() {
        while (gameThread != null) {
            update();
            
            repaint();
            try {
                sleep(16); // 33 = 30 FPS x second | 16 = 60 FPS x second
            } catch (InterruptedException ex) {
                Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void update() {
        pl.update();
    }
    
    public void paintComponent(Graphics gra) {
        super.paintComponent(gra);
        Graphics2D gra2 = (Graphics2D) gra;

        tileManager.DrawMap(matrix, gra2);
        pl.draw(gra2);
        
        gra2.dispose();
    }
    
    
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
}
