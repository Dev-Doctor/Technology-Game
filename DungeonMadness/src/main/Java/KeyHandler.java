/**
* @author  DevDoctor, Jifrid
* @version 1.0
* @file KeyHandler.java 
* 
* @brief Handles the keys inputs
*
*/
package main.Java;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/** 
* @class KeyHandler
* 
* @brief Handles the keys inputs
*/ 
public class KeyHandler implements KeyListener {

    GamePanel gp;
    /**The values for the relative pressed key, true if it's pressed else false*/
    public boolean wPressed, sPressed, aPressed, dPressed, ePressed, spacePressed, fPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    
    /**
     * @brief it handle the key pressed event
     * 
     * Regulate the pressed keys in the various scenarios
     * @param e pressed key event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        if (gp.gameState == gp.playState) {
            playState(k);
        } else if (gp.gameState == gp.pauseState) {
            pauseState(k);
        } else if (gp.gameState == gp.gameOverState) {
            gameOverState(k);
        }
    }

    /**
     * @brief it handle the key release event
     * 
     * Regulate the release keys
     * @param e release key event
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int k = e.getKeyCode();
        if (k == KeyEvent.VK_W) {
            wPressed = false;
        }
        if (k == KeyEvent.VK_S) {
            sPressed = false;
        }
        if (k == KeyEvent.VK_A) {
            aPressed = false;
        }
        if (k == KeyEvent.VK_D) {
            dPressed = false;
        }
        if (k == KeyEvent.VK_E) {
            ePressed = false;
        }
        if (k == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
        if (k == KeyEvent.VK_F) {
            fPressed = false;
        }
    }
    
    /**
     * @brief it handles the keys when the game is playing
     * 
     * it handles the keys when the game is in the state of playing
     * @param k pressed key code
     */
    private void playState(int k) {
        if (k == KeyEvent.VK_W) {
            wPressed = true;
        }
        if (k == KeyEvent.VK_S) {
            sPressed = true;
        }
        if (k == KeyEvent.VK_A) {
            aPressed = true;
        }
        if (k == KeyEvent.VK_D) {
            dPressed = true;
        }
        if (k == KeyEvent.VK_E) {
            ePressed = true;
        }

        if (k == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }

        if (k == KeyEvent.VK_F) {
            fPressed = true;
        }

        if (k == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.pauseState;
        }
    }

    /**
     * @brief it handles the keys when the game is paused
     * 
     * it handles the keys when the game is in the state of pause
     * @param k pressed key code
     */
    private void pauseState(int k) {
        if (k == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
    }
    
    /**
     * @brief it handles the keys when the game is over
     * 
     * it handles the keys when the game is in the state of gameover
     * @param k pressed key code
     */
    private void gameOverState(int k) {
        if (k == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }
        }
        if (k == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 1) {
                gp.ui.commandNum = 0;
            }
        }
        if (k == KeyEvent.VK_SPACE) {
            if (gp.ui.commandNum == 0) {
                gp.gameState =  gp.playState;
                gp.restart();
            }else{
                System.exit(0);
            }
        }
    }

}
