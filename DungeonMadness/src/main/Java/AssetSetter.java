/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package main.Java;

import main.Java.object.chest;
import main.Java.object.heart;
import main.Java.object.key;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }
    
    public void setObject(){
        gp.obj[0] = new key();
        gp.obj[0].SetX(3*DefaultValues.tileSize);
        gp.obj[0].SetY(2*DefaultValues.tileSize);
        
        gp.obj[2] = new key();
        gp.obj[2].SetX(280);
        gp.obj[2].SetY(350);
        
        gp.obj[1] = new chest();
        gp.obj[1].SetX(2*DefaultValues.tileSize);
        gp.obj[1].SetY(2*DefaultValues.tileSize);
        
        gp.obj[2] = new heart();
        gp.obj[2].SetX(5*DefaultValues.tileSize);
        gp.obj[2].SetY(5*DefaultValues.tileSize);
        
    }
    
}