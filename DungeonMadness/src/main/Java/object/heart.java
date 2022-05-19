/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package main.Java.object;

import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;

public class heart extends SuperObject {
    public heart() {
        name = "heart";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/objects/heart.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
        
        solidArea = new Rectangle(8 , 8, 52, 48);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
