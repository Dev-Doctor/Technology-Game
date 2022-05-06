/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package main.Java.object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.Java.GamePanel;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int[] position = new int[2];
    public Rectangle solidArea = new Rectangle(0, 0, 64, 64);
    public int solidAreaDefaultX = 0, solidAreaDefaultY = 0;
    
    public void draw(Graphics2D gra2, GamePanel gp){
        gra2.drawImage(image, position[0], position[1], gp.tileSize, gp.tileSize, null);
        gra2.setColor(Color.green);
        gra2.drawRect(position[0] + solidArea.x, position[1] + solidArea.y, solidArea.width, solidArea.height);
    }

    public String getName() {
        return name;
    }
    
    public int GetX() {
        return position[0];
    }
    
    public int GetY() {
        return position[1];
    }

    public void SetX(int x) {
        position[0] = x;
    }
    
    public void SetY(int y) {
        position[1] = y;
    }
}
