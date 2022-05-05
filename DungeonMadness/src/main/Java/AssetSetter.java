/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package main.Java;

import main.Java.enemies.Ogre;
import main.Java.entities.testNPC;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
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