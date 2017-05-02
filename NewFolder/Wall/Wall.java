/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Wall;

import GameCore.TankExplosion;
import SoundPlayer.Sound;
import Tank.GameObj;
import Tank.tankgame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

/**
 *
 * @author MacBook
 */
public class Wall extends GameObj{
    private boolean shootable;
    private boolean beShoot;
    private int disappearTime = 150;
    private int count;
    private int health;
    private String soundFileName;
    private Sound sp;
    
    public Wall(Image img, int x, int y, int speed, boolean shootable){
        super(img,x,y,Yspeed);
        this.shootable = shootable;
        this.beShoot = false;
        this.count = 0;
        this.health = 10;
        
    }
    public boolean isDisapeared(){
        return (this.beShoot && this.shootable);//a wall is disappeared only when it is shootbale and gets shoot
    }
    public boolean getBeShoot(){
        return this.beShoot;
    }
    public boolean getShootable(){
        return this.shootable;
    }
    public void setBeShoot(boolean s){
        this.soundFileName = "Resources/Explosion_small.wav";
        this.sp = new Sound(2,soundFileName);
        tankgame.explosion.add(new TankExplosion(x,y,6,tankgame.smallExp));
        sp.play();
        this.beShoot = s;
        this.count = 0;//set count as 0 if the wall has bean shoot
    }
    public void reduceHealth(int h){
        health -= h;
    }
    
    public void update(){
        if(this.beShoot == true && this.count < this.disappearTime){
            this.count++;//update the diappear time
        }
        else{
            this.beShoot = false;
            this.count = 0;
        }
    }
    
    public void draw(Graphics g, ImageObserver obs){
        if(this.isDisapeared() == false) 
            g.drawImage(img, x, y, obs);
    
    }

    
}
