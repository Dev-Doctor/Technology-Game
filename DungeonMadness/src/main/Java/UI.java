/**
* @author  Jifrid, DevDoctor
* @version 1.0
* @file UI.java 
* 
* @brief Manages the GUI of the game
*
* Manages the GUI of the game in the different states of it
*/
package main.Java;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import main.Java.world.Floor;

/** 
* @class UI
* 
* @brief Manages the GUI of the game 
* 
* Manages the GUI of the game in the different states of it
*/ 
public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font arial80;
    Font arial40;
    Font arial25;
    int commandNum = 0;

    public UI(GamePanel gp) {
        this.gp = gp;
        arial80 = new Font("Arial", Font.PLAIN, 80);
        arial40 = new Font("Arial", Font.PLAIN, 40);
        arial25 = new Font("Arial", Font.PLAIN, 25);
    }

    /**
     * @brief draw during the PlayState
     * 
     * Calls the other draw methods if it isn't in PlayState anymore
     * @param g2 
     */
    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(arial25);
        g2.setColor(Color.WHITE);

        if (gp.gameState == gp.playState) {
            g2.drawString("Health:" + gp.GetPlayer().getHealth() + "/" + gp.GetPlayer().getMaxHealth(), 10, 30);
            g2.drawString("Explored Rooms: " + gp.GetPlayer().getRoomExplored() + "/" + gp.GetWorld().GetCurrentFloor().tot_rooms, 10, 70);
            g2.drawString("Kills: " + gp.GetPlayer().getEnemyKilled(), 10, 110);
            DrawMinimap();
        }
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
        if (gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }
    }
    
    /**
     * @brief draw during the GameOver state
     * 
     * Draws the game over GUI on the screen
     */
    public void drawGameOverScreen(){
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, DefaultValues.WindowWidth, DefaultValues.WindowHeight);
        
        String text = "GAME OVER";
        int x = getXforCenteredText(text) - 165;
        int y = DefaultValues.tileSize * 4;
        g2.setFont(arial80);
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
        
        g2.setFont(arial40);
        
        text = "Retry";
        x = getXforCenteredText(text);
        y = DefaultValues.tileSize * 6;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 25, y);
        }
        
        text = "Quit";
        x = getXforCenteredText(text);
        y += 80;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 25, y);
        }
    }

    /**
     * @brief draw during the Paused state
     * 
     * Draws the paused GUI on the screen
     */
    public void drawPauseScreen() {
//        JButton jb = new JButton("mute");
        String text = "PAUSED";
        
        g2.setFont(arial40);
//        jb.setBounds(200, 200, 25, 10);
        int x = getXforCenteredText(text);
        int y = DefaultValues.WindowHeight / 2;
//        gp.add(jb);
        
        
        g2.drawString(text, x, y);
        g2.setFont(arial25);
        g2.drawString("Explored Floors:" + (gp.GetWorld().getFloor_number() - 1), 10, 30);
        g2.drawString("All Explored Rooms: " + gp.GetPlayer().TotRoomExplored, 10, 70);
    }

    /**
     * @brief Calculate a X 
     * 
     * Calculate the X cord to draw the String in the center
     * @param text the String that needs to be centered
     * @return the X cord to draw the String in the center
     */
    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return DefaultValues.WindowWidth / 2 - length / 2;
    }
    
    /**
     * @brief Draws the minimap
     * 
     * Draws the minimap in the right corner of the game screen
     */
    public void DrawMinimap() {
        int[] base_pos = new int[]{970, 20};
        int roomsize = 30;
        int cycle_x = 0;
        int cycle_y = 0;

        Floor f = gp.GetWorld().GetCurrentFloor();
        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.white);
        g2.drawRect(base_pos[0], base_pos[1], roomsize * 5, roomsize * 5);
        for (int r = f.getCurr_room_cords()[0] - 2; r < f.getCurr_room_cords()[0] + 3; r++) {
            boolean inBoundsR = (r >= 0) && (r < f.getSize());
            for (int c = f.getCurr_room_cords()[1] - 2; c < f.getCurr_room_cords()[1] + 3; c++) {
                boolean inBoundsC = (c >= 0) && (c < f.getSize());
                if (inBoundsR && inBoundsC) {
                    if (f.getRoomsMatrix()[r][c].isExplored()) {
                        g2.setColor(Color.blue);
                    } else {
                        if (f.getRoomsMatrix()[r][c].GetValue() != 0) {
                            g2.setColor(Color.red);
                        } else {
                            g2.setColor(Color.gray);
                        }
                    }
                    if (r == f.getCurr_room_cords()[0] && c == f.getCurr_room_cords()[1]) {
                        g2.setColor(Color.yellow);
                    }
                } else {
                    g2.setColor(Color.gray);
                }
                g2.fillRect(base_pos[0] + (roomsize * cycle_x), base_pos[1] + (roomsize * cycle_y), roomsize, roomsize);
                g2.setColor(Color.white);
                g2.drawRect(base_pos[0] + (roomsize * cycle_x), base_pos[1] + (roomsize * cycle_y), roomsize, roomsize);
                cycle_x++;
            }
            cycle_y++;
            cycle_x = 0;
        }
    }
}
