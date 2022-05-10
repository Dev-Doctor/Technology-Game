package main.Java;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean wPressed, sPressed, aPressed, dPressed, ePressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
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
        
        if (k == KeyEvent.VK_ESCAPE) {
            if (gp.gameState == gp.playState) {
                gp.gameState = gp.pauseState;
            }else if (gp.gameState == gp.pauseState) {
                gp.gameState = gp.playState;
            }
        }
    }

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
    }

}
