/**
* @author  DevDoctor, Jifrid
* @version 1.0
* @file GamePanel.java 
* 
* @brief The main file of the program
* 
* manages and stores everything
* 
*/
package main.Java;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import main.Java.world.Tile;
import main.Java.world.World;
import main.Java.entities.Player;
import main.Java.multiplayer.GameClient;
import main.Java.multiplayer.GameServer;
import main.Java.object.SuperObject;
import main.Java.packets.Packet00Login;
import main.Java.entities.PlayerMP;
import main.Java.world.TileManager;

/** 
* @class GamePanel
* 
* @brief Main class of the program 
*/ 
public class GamePanel extends JPanel implements Runnable {

    /**The collision checker for all the classes*/
    public CollisionChecker collisionChecker;
    /**The tilemanager for all the classes*/
    TileManager tileManager = new TileManager(this);
    /**The main GUI*/
    public UI ui;
    /**Number of FPS*/
    private final int FPS = 60;

    /**The thread of this class*/
    Thread gameThread;
    /**The KeyHandler for all the classes*/
    public KeyHandler keyHandler = new KeyHandler(this);
    /**The SoundManager for all the classes*/
    SoundManager soundManager;
    
    private GameServer socketServer;
    private GameClient socketClient;

    /**The Player*/
    public Player pl;
    /**Contains all the objects of the current room*/
    public ArrayList<SuperObject> obj = new ArrayList<SuperObject>();

    /**The current gamestate*/
    public int gameState;
    /**The value when the game is in the state of playing*/
    public final int playState = 1;
    /**The value when the game is in the state of paused*/
    public final int pauseState = 2;
    /**The value when the game is in the state of gameover*/
    public final int gameOverState = 3;
    
    /**The theme of the current dungeon*/
    public String theme = "default";
    
    /**The current world*/
    World world;

    public GamePanel() {
        this.setPreferredSize(new Dimension(DefaultValues.WindowWidth, DefaultValues.WindowHeight));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        this.collisionChecker = new CollisionChecker(this);
        
        if (JOptionPane.showConfirmDialog(this, "Run server") == 0) {
            socketServer = new GameServer(this);
            socketServer.start();
        }
        
        pl = new PlayerMP(null, -1, this, keyHandler, JOptionPane.showInputDialog("Enter username"));
        Packet00Login loginPacket = new Packet00Login(pl.getUsername());
        if (socketServer != null) {    
            socketServer.addConnection((PlayerMP)pl, loginPacket);
        }
        world = new World(this, theme);
        world.LoadDungeon();
        
        socketClient = new GameClient(this,"localhost");
        socketClient.start();
        loginPacket.writeData(socketClient);
    }

    /**
    * Sets the game state to play
    */
    void setupGame() {
        gameState = playState;
        ui = new UI(this);
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        float drawCount = 0;

        soundManager = new SoundManager();
        soundManager.PlayMusic();

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                //System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
    }
    
    
    /**
    * Update the player and world
    */
    private void update() {
        if (gameState != playState) {
            return;
        }
        // UPDATE PLAYER
            pl.update();
        
        // UPDATE ENEMIES
        world.GetCurrentFloor().UpdateRoomEnemies();
        world.GetCurrentFloor().UpdateRoomProjectiles();
//        for (int i = 0; i < npc.length; i++) {
//            if (npc[i] != null) {
//                npc[i].update();
//            }
//        }
//        
//        for (int i = 0; i < enemy.length; i++) {
//            if (enemy[i] != null) {
//                enemy[i].update();
//            }
//        }
    }
    
    
    /**
    * Paint the screen
    * @param Graphics
    */
    public void paintComponent(Graphics gra) {
        super.paintComponent(gra);
        Graphics2D gra2 = (Graphics2D) gra;
        // FIRST DRAW
        if (world.DrawMap) {
            tileManager.DrawMap(world.GetCurrentRoom(), gra2);
//            world.DrawMap = false;
        }

        for (int i = 0; i < obj.size(); i++) {
            if (obj.get(i) != null) {
                obj.get(i).draw(gra2, this);
            }
        }
        // DRAW ENEMIES
        world.GetCurrentFloor().DrawRoomEnemies(gra2);
        
        //DRAW PROJECTILES
        world.GetCurrentFloor().DrawRoomProjectiles(gra2);
        
        // DRAW PLAYER
            pl.draw(gra2);

        // DRAW GUI
        ui.draw(gra2);

        // LAST DRAW
        gra2.dispose();
    }
    
    public void restart() {
        
    }
    
    /**
    * Start the main thread
    */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public Player GetPlayer() {
        return pl;
    }

    public Tile[][] GetRoomMatrix() {
        return world.GetCurrentRoom().getMatrix();
    }

    public World GetWorld() {
        return world;
    }

    public TileManager GetTileManager() {
        return tileManager;
    }
    
    public SoundManager GetSoundManager() {
        return soundManager;
    }
}
