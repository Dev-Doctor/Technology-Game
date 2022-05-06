/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package main.Java.object;

import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;

public class key extends SuperObject{

    public key() {
        name = "key";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/objects/key.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
        
        collision = true;
        solidArea = new Rectangle(8 , 20, 44, 20);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
    
}
