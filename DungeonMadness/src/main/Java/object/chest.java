/**
 * @author Jifrid
 * @version 1.0
 * @file chest.java
 *
 * @brief CUT CONTENT: The chest object file
 *
 */
package main.Java.object;

import java.io.IOException;
import javax.imageio.ImageIO;

/** 
* @class chest
* 
* @brief CUT CONTENT: The chest object class
* @see main.Java.object.SuperObject
*/ 
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
