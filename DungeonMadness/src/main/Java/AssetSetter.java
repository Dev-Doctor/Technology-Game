/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package main.Java;

import main.Java.enemies.Ogre;
import main.Java.entities.testNPC;
import main.Java.object.chest;
import main.Java.object.key;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }
    
    public void setObject(){
        gp.obj[0] = new key();
        gp.obj[0].SetX(3*gp.tileSize);
        gp.obj[0].SetY(2*gp.tileSize);
        
        gp.obj[2] = new key();
        gp.obj[2].SetX(280);
        gp.obj[2].SetY(350);
        
        gp.obj[1] = new chest();
        gp.obj[1].SetX(2*gp.tileSize);
        gp.obj[1].SetY(2*gp.tileSize);
    }
    
    public void setNPC(){
        gp.npc[0] = new testNPC(gp);
        gp.npc[0].SetX(500);
        gp.npc[0].SetY(400);
    }
    
    public void setEnemy(){
        
        gp.enemy[0] = new Ogre(gp);
        gp.enemy[0].SetX(200);
        gp.enemy[0].SetY(400);
        
        gp.enemy[1] = new Ogre(gp);
        gp.enemy[1].SetX(280);
        gp.enemy[1].SetY(400);
        
        gp.enemy[2] = new Ogre(gp);
        gp.enemy[2].SetX(350);
        gp.enemy[2].SetY(400);
     
    }
}