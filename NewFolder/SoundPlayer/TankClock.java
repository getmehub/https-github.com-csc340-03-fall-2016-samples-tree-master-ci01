/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SoundPlayer;

import java.util.Observable;

/**
 *
 * @author MacBook
 */
/*Game clock ticks on every frame and notifies observers to update*/
public class TankClock extends Observable {
	private int startTime;
	private int frame;
	
	public TankClock(){
		startTime = (int) System.currentTimeMillis();
		frame = 0;
	}
		
	public void tick(){
		frame++;
		setChanged();
		this.notifyObservers();
	}
	
	public int getFrame(){
		return this.frame;
	}
	
	public int getTime(){
		return (int)System.currentTimeMillis()-startTime;
	}
}