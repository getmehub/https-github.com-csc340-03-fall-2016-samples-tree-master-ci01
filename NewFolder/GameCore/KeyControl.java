/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameCore;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author MacBook
 */
public class KeyControl extends KeyAdapter {
    private GameEvents gameEvents;
    
    public KeyControl(){
        
    }
    public KeyControl(GameEvents ge){
        this.gameEvents = ge;
    }
    
    public void keyPressed(KeyEvent e) {
        gameEvents.setValue(e);
    }
    public void keyReleased(KeyEvent e){
        gameEvents.setValue(e);
    }
    
}