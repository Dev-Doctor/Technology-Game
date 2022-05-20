/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package main.Java.object;

import java.io.IOException;
import javax.imageio.ImageIO;

public class chest extends SuperObject{

    public chest() {
        name = "chest";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/objects/chest.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
        
        collision = true;
    }
    
}
