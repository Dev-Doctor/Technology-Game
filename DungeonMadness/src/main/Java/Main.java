/**
* @author  DevDoctor
* @version 1.0
* @file Main.java 
* 
* @brief set the basic settings of the main thread and the window
*
* it does absolutely nothing but start and set the basic settings of the main thread and the window
*/
package main.Java;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/** 
* @class Main
* 
* @brief Main class of the game
*/ 
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame window = new JFrame("My Game");
		GamePanel gamePanel = new GamePanel(window);
		ImageIcon img = new ImageIcon("G:\\Programming\\java\\EclipseWorkspace\\TecnologyGame\\src\\main\\resources\\assets\\textures\\test.png");
                
                window.setIconImage(img.getImage());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Dungeon Madness");
		window.add(gamePanel);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
                
		gamePanel.startGameThread();
                gamePanel.setupGame();
                
    }
    
}
