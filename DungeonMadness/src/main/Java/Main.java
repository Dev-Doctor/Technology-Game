package main.java;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author DevDoctor
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame window = new JFrame("My Game");
		GamePanel gamePanel = new GamePanel();
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
    }
    
}
