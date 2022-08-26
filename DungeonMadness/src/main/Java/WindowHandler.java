/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package main.Java;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import main.Java.packets.Packet01Disconnect;

public class WindowHandler implements WindowListener{
    
    GamePanel gp;

    public WindowHandler(GamePanel gp) {
        this.gp = gp;
        this.gp.window.addWindowListener(this);
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        Packet01Disconnect packet = new Packet01Disconnect(this.gp.GetPlayer().getUsername());
        packet.writeData(this.gp.socketClient);
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

}
