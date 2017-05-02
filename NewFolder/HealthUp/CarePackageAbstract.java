/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HealthUp;

import Tank.GameObj;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

/**
 *
 * @author MacBook
 */
public abstract class CarePackageAbstract extends GameObj{
	boolean show = false;
	public CarePackageAbstract(Image img, int x, int y, int Yspeed) {
		super(img, x, y, Yspeed);
		show = true;
	}
	/**
	 * Get overridden by the child class, can't use this this class directly anyway.
	 */
	public void update() {
	}
	public boolean isShow(){
		return show;
	}
	/**
	 * This method is for drawing the items.
	 * @param obs
	 * @param g
	 */
	public void draw(ImageObserver obs, Graphics2D g) {
		if(show)
			g.drawImage(img, x, y, obs);
    }
}
