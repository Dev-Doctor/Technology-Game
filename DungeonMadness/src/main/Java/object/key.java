/**
 * @author Jifrid
 * @version 1.0
 * @file key.java
 *
 * @brief CUT CONTENT: The key object file
 *
 */
package main.Java.object;

import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;

/** 
* @class key
* 
* @brief CUT CONTENT: The key object class
* @see main.Java.object.SuperObject
*/ 
public class key extends SuperObject{

    public key() {
        name = "key";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/main/resources/assets/textures/objects/key.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
        
        solidArea = new Rectangle(8 , 20, 44, 20);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
    
}
