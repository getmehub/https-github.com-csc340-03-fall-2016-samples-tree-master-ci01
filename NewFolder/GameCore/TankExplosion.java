/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameCore;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

/**
 *
 * @author MacBook
 */
public class TankExplosion{
    private Image img;
    private int x,y;
    private int count, numOfSub;
    private boolean finished;
    private int type;//1 for small explosions, the other number for big ones
    
    public TankExplosion(int x, int y,int numOfSub, Image img){
        this.x = x;
        this.y = y;
        this.count = -1;
        this. finished = false;
        this.img = img;
        this.numOfSub = numOfSub;
    }
    
    public boolean getFinished(){
        return this.finished;
    }
    public void update(){
        if(count < this.numOfSub-1){
            count ++;
        }else{
            finished = true;
        }
        
    }
    
    public void draw(Graphics g,ImageObserver obs) {
         if (!finished) {
             g.drawImage(img, x, y, x + img.getHeight(obs), y + img.getHeight(obs), img.getHeight(obs) * count, 0, img.getHeight(obs) * count +img.getHeight(obs), img.getHeight(obs), obs);
         }
     }
}
