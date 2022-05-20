package main.Java;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean wPressed, sPressed, aPressed, dPressed, ePressed, spacePressed, fPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

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

    private void pauseState(int k) {
        if (k == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
    }

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
