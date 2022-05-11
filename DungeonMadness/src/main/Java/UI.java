/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package main.Java;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UI {
    
    GamePanel gp;
    Graphics2D g2;
    Font arial40;

    public UI(GamePanel gp) {
        this.gp = gp;
        arial40 = new Font("Arial", Font.PLAIN, 40);
    }
    
    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(arial40);
        g2.setColor(Color.WHITE);

        if (gp.gameState == gp.playState) {
            //g2.drawString("Key = " + gp.pl.nKey, 50, 50);
        }
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
    }
    
    public void drawPauseScreen(){
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = DefaultValues.WindowHeight/2;
        g2.drawString(text, x, y);
    }
    
    public int getXforCenteredText(String text){
        int length=(int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return DefaultValues.WindowWidth/2 - length/2;
    }
}
