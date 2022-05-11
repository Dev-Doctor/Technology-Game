package main.Java;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import main.Java.entities.Entity;
import main.Java.world.Tile;
import main.Java.world.World;
import main.Java.entities.Player;
import main.Java.object.SuperObject;
import main.Java.world.TileManager;

/** @author DevDoctor */
public class GamePanel extends JPanel implements Runnable {
    public CollisionChecker collisionChecker;
    TileManager tileManager = new TileManager(this);
    AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    private final int FPS = 60;
    
    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler(this);
    
    public Player pl;
    public SuperObject obj[] = new SuperObject[10];
    public Entity npc[] = new Entity[10];
    public Entity enemy[] = new Entity[10];
    
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    
    World world;
    Tile[][] matrix; // TEMPORARY
    
    
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(DefaultValues.WindowWidth, DefaultValues.WindowHeight));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        
        this.collisionChecker = new CollisionChecker(this);
        this.matrix = new Tile[DefaultValues.MaxRowsTiles][DefaultValues.MaxColTiles];
        
        pl = new Player(this, keyHandler);
        
        world = new World(this);
        world.LoadDungeon("default");
        
        /** !!! TEMPORARY !!! **/
        matrix = world.GetCurrentRoom().getMatrix();
    }
    
    void setupGame() {
//        aSetter.setObject();
//        aSetter.setNPC();
        aSetter.setEnemy();

        gameState = playState;
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
        if (gameState == pauseState) {
            return;
        }
        
        pl.update();
        
        for (int i = 0; i < npc.length; i++) {
            if (npc[i] != null) {
                npc[i].update();
            }
        }
        
        for (int i = 0; i < enemy.length; i++) {
            if (enemy[i] != null) {
                enemy[i].update();
            }
        }
    }
    
    public void paintComponent(Graphics gra) {
        super.paintComponent(gra);
        Graphics2D gra2 = (Graphics2D) gra;
        // FIRST DRAW
        if(world.DrawMap) {
            tileManager.DrawMap(world.GetCurrentRoom(), gra2);
//            world.DrawMap = false;
        }
        
        for (int i = 0; i < npc.length; i++) {
            if (npc[i] != null) {
                npc[i].draw(gra2);
            }
        }
        
        for (int i = 0; i < enemy.length; i++) {
            if (enemy[i] != null) {
                enemy[i].draw(gra2);
            }
        }
        
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(gra2, this);
            }
        }
        
        pl.draw(gra2);
        
        ui.draw(gra2);
        
        // LAST DRAW
        
        gra2.dispose();
    }
    
    
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    public Player GetPlayer() {
        return pl;
    }
    
    public Tile[][] GetRoomMatrix() {
        return matrix;
    }

    public World GetWorld() {
        return world;
    }
    
    public TileManager GetTileManager() {
        return tileManager;
    }
}
