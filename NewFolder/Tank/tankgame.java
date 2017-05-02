/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tank;

import GameCore.GameEvents;
import GameCore.KeyControl;
import Gamecore.TankExplosion;
import HealthUp.PowerUp;
import HealthUp.TankCollison;
import Wall.Wall;
import SoundPlayer.Sound;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JFrame;
import java.util.Observer;
import java.awt.image.ImageObserver;


/**
 *
 * @author MacBook
 */
public class tankgame extends JApplet implements Runnable {
    public static Image bigExp,smallExp;
    public static ArrayList<Wall> wall_list;
    public static ArrayList<TankExplosion> explosion;
    public static ArrayList<PowerUp> powerup;
    private Thread thread;
    public BufferedImage bimg,miniBuf, leftImg,rightImg,gDisplayBuf;
    
    Image background, wall1, wall2, tankRed, tankBlue,powerUp,miniMap;
    Graphics2D g2,gMini,gDisplay;
    Wall testW;
    public Tank mytank,enemytank;
    GameEvents gameEvent1,gameEvent2;
    KeyControl key1,key2;
    int frameCount;
    int w = 1200, h = 1200;
    int[][] map;
   
    TankCollison CD;
    Sound sp;

    public void init() {
        setFocusable(true);
        try {
            background = ImageIO.read(tankgame.class.getResource("Resources/Background.png"));
            wall1 = ImageIO.read(tankgame.class.getResource("Resources/Wall1.png"));
            wall2 = ImageIO.read(tankgame.class.getResource("Resources/Wall2.png"));
            tankBlue = ImageIO.read(tankgame.class.getResource("Resources/Tank1_strip60.png"));
            tankRed = ImageIO.read(tankgame.class.getResource("Resources/Tank2_strip60.png"));
            bigExp = ImageIO.read(tankgame.class.getResource("Resources/Explosion_large_strip7.png"));
            smallExp = ImageIO.read(tankgame.class.getResource("Resources/Explosion_small_strip6.png"));
            powerUp = ImageIO.read(tankgame.class.getResource("Resources/Weapon_strip4.png"));
            //System.out.println(wall1.getWidth(this) + " " + wall1.getHeight(this));
        } catch (Exception e) {
            System.out.println("No resource are found in init()");
        }

        mytank = new Tank(tankBlue, 1, 280, 738, 6, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        enemytank= new Tank(tankRed,1,878,738,6,KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        gameEvent1 = new GameEvents();
        gameEvent2 = new GameEvents();
        //gameEvent1.equals(mytank);
        gameEvent1.addObserver(mytank);
        gameEvent2.addObserver(enemytank);
        key1 = new KeyControl(gameEvent1);
        key2 = new KeyControl(gameEvent2);
        addKeyListener(key1);
        addKeyListener(key2);
        CD = new TankCollison(gameEvent1, gameEvent2);
        wall_list = new ArrayList<Wall>(2000);
        explosion = new ArrayList<TankExplosion>(200);
        powerup = new ArrayList<PowerUp>(10);
        map = readMap("map.csv", 37, 38,wall_list,powerup);
        sp = new Sound(1,"Resources/background.wav");
        
    }

    public void drawBackGroundWithTileImage() {
        int TileWidth = background.getWidth(this);
        int TileHeight = background.getHeight(this);

        int NumberX = (int) (w / TileWidth);
        int NumberY = (int) (h / TileHeight);

        for (int i = -1; i <= NumberY; i++) {
            for (int j = 0; j <= NumberX; j++) {
                g2.drawImage(background, j * TileWidth,
                        i * TileHeight, TileWidth,
                        TileHeight, this);
                
            }
        }

    }
    
    public void drawMap(){
        for(int i = 0; i < wall_list.size(); i++){
            wall_list.get(i).draw(g2, this);
        }
        for(int i = 0; i < powerup.size(); i++){
            powerup.get(i).draw(g2, this);
        }
    }
    
    public void updateMap(){
        for(int i = 0; i < wall_list.size(); i++){
            wall_list.get(i).update();
        }
        for(int i = 0; i < powerup.size(); i++){
            powerup.get(i).update();
        }
    }
    public int[][] readMap(String fileName, int height, int width, ArrayList<Wall> w,ArrayList<PowerUp> p) {

        Scanner fileScanner;
        int map[][] = new int[height][width];
       
        try {
            fileScanner = new Scanner(tankgame.class.getResourceAsStream(fileName));
            fileScanner.useDelimiter(",|\\n|\\r");
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    String s = fileScanner.next();
                    //System.out.print(s);
                    if (s.equals("")) {
                        j--;
                        continue;
                    }
                    map[i][j] = Integer.parseInt(s);
                    //System.out.println(map[i][j]);
                    if(map[i][j] == 1){
                        w.add(new Wall(wall1,32*j,32*i,0,false));
                        //System.out.println(32*j + " "+ 32*i);
                    }
                    if(map[i][j] == 2){
                        w.add(new Wall(wall2,32*j,32*i,0,true));
                        //System.out.println(32*j + " "+ 32*i);
                    }
                    if(map[i][j] == 3){
                        p.add(new PowerUp(powerUp,32*j,32*i,0,1));
                        //System.out.println(32*j + " "+ 32*i);
                    }
                    if(map[i][j] == 4){
                        p.add(new PowerUp(powerUp,32*j,32*i,0,2));
                        //System.out.println(32*j + " "+ 32*i);
                    }
                    if(map[i][j] == 5){
                        p.add(new PowerUp(powerUp,32*j,32*i,0,3));
                        //System.out.println(32*j + " "+ 32*i);
                    }
                }
            }
            /*for(int ii = 0; ii < height; ii++){
             for(int jj = 0; jj < width; jj++) {
             System.out.print(map[ii][jj]);
             }
             System.out.println();
             }*/
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

    public void paint(Graphics g) {
        if (bimg == null) {
            Dimension windowSize = getSize();
            bimg = (BufferedImage) createImage(w,h);
            gDisplayBuf = (BufferedImage) createImage(640,480);
            g2 = bimg.createGraphics();
            gDisplay = gDisplayBuf.createGraphics();
            
        }
        
        drawDemo();
        g.drawImage(gDisplayBuf, 0, 0, this);
        
    }

    public void drawDemo() {
        String content = ""+mytank.getScore();
        String content1 = ""+enemytank.getScore();
        Font stringFont = new Font( "SansSerif", Font.PLAIN, 18 ); 
        g2.setFont(stringFont); 
        g2.setColor(Color.white);
        
        if(mytank.getBoom()){
            stringFont = new Font( "SansSerif", Font.BOLD, 40); 
            gDisplay.setFont( stringFont ); 
            gDisplay.setColor(Color.white);
            gDisplay.drawString(" PLAYER2 WON! ", 170, 220);
            sp.stop();;
         }else if(enemytank.getBoom()){
            stringFont = new Font( "SansSerif", Font.BOLD, 40 ); 
            gDisplay.setFont( stringFont );
            gDisplay.setColor(Color.white);
            gDisplay.drawString("PALYER1 WON!", 170, 220);
            sp.stop();
         }
         else{
        //updating the gameObjects by first checking the collisions
        CD.TankVSTank(mytank, enemytank);
        CD.TankBulletVSWall(mytank, enemytank);
        CD.TankVSTankBullet(mytank, enemytank);
        CD.TankVSPowerUp(mytank, enemytank);
        updateMap();
        for(int i = 0; i < mytank.getBulletList().size();i++){
            if(mytank.getBulletList().get(i).getShow())
                mytank.getBulletList().get(i).update(w,h);
        }
        for(int i = 0; i < enemytank.getBulletList().size();i++){
            if(enemytank.getBulletList().get(i).getShow())
                enemytank.getBulletList().get(i).update(w,h);
        }
         for(int i = 0; i< explosion.size(); i++){
                if(explosion.get(i).getFinished()) {
                    explosion.remove(i);
                    i --;
                }
                else{
                    explosion.get(i).update();
                }
            }
                
        //draw the gameObjects after updating
        drawBackGroundWithTileImage();
        drawMap();
        mytank.draw(g2, this);
        enemytank.draw(g2, this);
        for(int i = 0; i < mytank.getBulletList().size(); i++){
                mytank.getBulletList().get(i).draw(g2,this);
        }
        for(int i = 0; i < enemytank.getBulletList().size(); i++){
                enemytank.getBulletList().get(i).draw(g2,this);
        }
        for(int i = 0; i < explosion.size(); i++){
                explosion.get(i).draw(g2, this);
        }
        g2.drawString(content, mytank.getX()-5, enemytank.getY()-5);
        g2.drawString(content1, enemytank.getX()-5, enemytank.getY()-5);
        //cut the iamge into left and right
        leftImg = bimg.getSubimage(mytank.getX()-150, mytank.getY()-240, 316, 480);
        rightImg = bimg.getSubimage(enemytank.getX()-150, enemytank.getY()-240, 316, 480);
        
        //get scaled image
        BufferedImage temp = bimg.getSubimage(150, 250, 900, 710);
        miniMap = temp.getScaledInstance(120, 100, Image.SCALE_SMOOTH);
       //put all three map segments into one buffered iamge
        gDisplay.drawImage(leftImg, 0, 0, this);
        gDisplay.drawImage(rightImg,317,0,this);
        gDisplay.drawImage(miniMap, 260,300,this);
       }  
    }

    public void start() {
        System.out.println();
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }

    public void run() {

        Thread me = Thread.currentThread();
        while (thread == me) {
            repaint();
            try {
                thread.sleep(25);
            } catch (InterruptedException e) {
                break;
            }

        }
    }

    public static void main(String argv[]) {
        final tankgame demo = new tankgame();
        demo.init();
        JFrame f = new JFrame("TankWar");
        f.addWindowListener(new WindowAdapter() {
        });
        f.getContentPane().add("Center", demo);
        f.pack();
        f.setSize(new Dimension(640, 480));
        f.setVisible(true);
        f.setResizable(false);
        demo.start();
    }
}
