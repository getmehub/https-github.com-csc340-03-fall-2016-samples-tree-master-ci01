/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HealthUp;

import Tank.GameObj;
import Tank.Tank;
import Tank.tankgame;
import java.awt.Graphics2D;
import java.awt.Image;

/**
 *
 * @author MacBook
 */

public class PowerUp extends CarePackageAbstract{

	public PowerUp(Image img, int x, int y, int speed) {
		super(img, x, y, speed);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Checking if it collide with the player plane.
	 */
        @Override
	public void update() {
		y+=Yspeed;
		if(tankgame.mytank.collision(x, y, width, height)){
			tankgame.mytank.oneUp();
			tankgame.mytank.setScore(100);
			System.out.println("one up ");
			show = false;
		}
		else if(tankgame.enemytank.collision(x, y, width, height)){
			tankgame.enemytank.oneUp();
			tankgame.enemytank.setScore(100);
			show = false;
		}
	}

  
}
