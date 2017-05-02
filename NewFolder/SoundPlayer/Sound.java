/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SoundPlayer;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author MacBook
 */
public class Sound {
   private AudioInputStream soundStream; 
   private String soundFile;
   private Clip clip;
   private int type;//1 for sounds that needs to be played all the time
                    // 2 for sounds that only need to be played once
   
   
   public Sound(int type, String soundFile){
       this.soundFile = soundFile;
       this.type = type;
       try{
           soundStream = AudioSystem.getAudioInputStream(Sound.class.getResource(soundFile));
           clip = AudioSystem.getClip();
           clip.open(soundStream);
       }
       catch(Exception e){
           System.out.println(e.getMessage() + " No sound documents are fouond in sound player");
       }
       if(this.type == 1){
           Runnable myRunnable = new Runnable(){
               public void run(){
                   while(true){
                       clip.start();
                       clip.loop(clip.LOOP_CONTINUOUSLY);
                       try {
                           Thread.sleep(10000);
                       } catch (InterruptedException ex) {
                           Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
                       }
                    }
               }
           };
           Thread thread = new Thread(myRunnable);
           thread.start();
       }
   }
   
   public void play(){
       clip.start();
   }
   public void stop(){
       clip.stop();
   }
}

