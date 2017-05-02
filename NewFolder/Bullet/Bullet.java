/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bullet;

import Tank.GameObj;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

/**
 *
 * @author MacBook
 */
public class Bullet extends GameObj {
    int damage,Xspeed;
    boolean show;
    
    public Bullet(Image img, int x, int y, int damage, int Xspeed,int Yspeed){
        super(img,x,y,Yspeed);
        this.damage = damage;
        this.Xspeed = Xspeed;
        this.show = true;
        this.width = 32;
    }
         
    public int getDamge(){
        return this.damage;
    }
    public boolean getShow(){
        return this.show;
    }
    public void setShow(boolean s){
        this.show = s;
    }
    public void update(int w, int h){
        if(y < h-40 && y > 0 && x > 0 && x < w-40 && show){
            x = x + Xspeed;
            y = y + Yspeed;
        }
        else{
            this.show = false;
        }
    }
    public void draw(Graphics g,ImageObserver obs) {
        if(show)  
            g.drawImage(img, x, y, obs);
     }
}
