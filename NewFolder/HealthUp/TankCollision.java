/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HealthUp;

import Bullet.Bullet;
import Bullet.TankBullet;
import GameCore.GameEvents;
import Tank.Tank;
import Tank.tankgame;
import Wall.Wall;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author MacBook
 */
public class TankCollision{
    GameEvents gameEvent1, gameEvent2;
    
    TankCollision(GameEvents ge1, GameEvents ge2){
        this.gameEvent1 = ge1;
        this.gameEvent2 = ge2;
    }
   
    public void TankVSTank(Tank mytank, Tank enemytank){
        Rectangle tank1Box = new Rectangle(mytank.getX(), mytank.getY(), mytank.getWidth(), mytank.getHeight());
        Rectangle tank2Box = new Rectangle(enemytank.getX(), enemytank.getY(), enemytank.getWidth(), enemytank.getHeight());
        if(tank1Box.intersects(tank2Box)){
            this.gameEvent1.setValue("Collision" + " "+ 50);
            this.gameEvent2.setValue("Collision" + " "+ 50);
            //System.out.println("tank1 and tank2 collision");
        }
    }
    public void TankBulletVSWall(Tank mytank, Tank enemytank){
        TankBullet bullet;
        Wall wall;
        ArrayList<TankBullet> tank1Bullet =mytank.getBulletList();
        ArrayList<TankBullet> tank2Bullet = enemytank.getBulletList();
        for(int i  = 0; i < tank1Bullet.size(); i++){
            bullet = tank1Bullet.get(i);
            Rectangle bulletBox = new Rectangle(bullet.getX(),bullet.getY(),bullet.getWidth(),bullet.getHeight());
            for(int j = 0; j < tankgame.wall_list.size(); j++){
                wall = tankgame.wall_list.get(j);
                Rectangle wallBox = new Rectangle(wall.getX(),wall.getY(),wall.getWidth(),wall.getHeight());
                //check collision, 
                if(bulletBox.intersects(wallBox)&&!mytank.getBoom()&& !wall.isDisapeared()){
                    tank1Bullet.remove(bullet);//remove bullet from list 
                    if(wall.getShootable())
                        wall.setBeShoot(true);
                   
                }
            }
        }
        for(int i  = 0; i < tank2Bullet.size(); i++){
            bullet = tank2Bullet.get(i);
            Rectangle bulletBox = new Rectangle(bullet.getX(),bullet.getY(),bullet.getWidth(),bullet.getHeight());
            for(int j = 0; j < tankgame.wall_list.size(); j++){
                wall = tankgame.wall_list.get(j);
                Rectangle wallBox = new Rectangle(wall.getX(),wall.getY(),wall.getWidth(),wall.getHeight());
                //check collision, 
                if(bulletBox.intersects(wallBox)&&!enemytank.getBoom() && !wall.isDisapeared()){
                    tank2Bullet.remove(bullet);//remove bullet from list 
                    if(wall.getShootable())
                        wall.setBeShoot(true);
                }
            }
        }
    }
    
    public void TankVSTankBullet(Tank mytank, Tank enemytank){
        Bullet bullet;
        Rectangle tank1Box = new Rectangle(mytank.getX(), mytank.getY(),mytank.getWidth(), mytank.getHeight());
        Rectangle tank2Box = new Rectangle(enemytank.getX(), enemytank.getY(), enemytank.getWidth(), enemytank.getHeight());
        ArrayList<TankBullet> tank1Bullet = mytank.getBulletList();
        ArrayList<TankBullet> tank2Bullet = enemytank.getBulletList();
        
        for(int i = 0; i < tank2Bullet.size(); i++){//check collision between mytank with the bullets of enemytank
            bullet = tank2Bullet.get(i);
            Rectangle bullet2Box= new Rectangle(bullet.getX(),bullet.getY(),bullet.getWidth(),bullet.getHeight());
            if(tank1Box.intersects(bullet2Box) && !mytank.getBoom()){
               
                if(!mytank.isInShield()){
                    this.gameEvent1.setValue("Collision"+ " "+ bullet.getDamge());
                    enemytank.addScore(bullet.getDamge());
                }
                tank2Bullet.remove(i);
            }
        }
        
        for(int i = 0; i < tank1Bullet.size(); i++){
            bullet = tank1Bullet.get(i);
            Rectangle bullet1Box= new Rectangle(bullet.getX(),bullet.getY(),bullet.getWidth(),bullet.getHeight());
            if(tank2Box.intersects(bullet1Box) && !enemytank.getBoom()){
                if(!enemytank.isInShield()){
                    this.gameEvent2.setValue("Collision"+ " "+ bullet.getDamge());
                    mytank.addScore(bullet.getDamge());
                }
                tank1Bullet.remove(i);
            }
        }
    }
    
    public void TankVSPowerUp(Tank mytank, Tank enemytank){
        PowerUp power;
        Rectangle tank1Box = new Rectangle(mytank.getX(), mytank.getY(), mytank.getWidth(), mytank.getHeight());
        Rectangle tank2Box = new Rectangle(enemytank.getX(),enemytank.getY(), enemytank.getWidth(), enemytank.getHeight());
        //check collosion between power up and mytank
        for(int i = 0; i < tankgame.powerup.size(); i++){
            power = tankgame.powerup.get(i);
            Rectangle powerBox = new Rectangle(power.getX(),power.getY(),power.getWidth(),power.getHeight());
            if(powerBox.intersects(tank1Box) && !mytank.getBoom() && power.getShow()){
                power.setCollected(true);
                this.gameEvent1.setValue("PowerUp"+" " + power.getType());
            }
            if(powerBox.intersects(tank2Box) && !enemytank.getBoom() && power.getShow()){
                power.setCollected(true);
                this.gameEvent2.setValue("PowerUp"+" " + power.getType());
            }
        }
        
    }
}