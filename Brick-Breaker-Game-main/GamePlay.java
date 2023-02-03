package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements ActionListener, KeyListener{



    @Override
    public void keyTyped(KeyEvent e) {



    }

    private  boolean play=false;
    private  int score=0;
    private  int totalBricks=21;
    private  Timer timer;
    private  int delay=8;
    private  int ballposX=120;
    private  int ballposY=350;

    private int ballXdir=-3;
    private int ballYdir=-3;
    private int playerX=350;

    private MapGenerator mapGenerator;


    public GamePlay(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        timer=new Timer(delay,this);
        timer.start();
        mapGenerator=new MapGenerator(3,7);


    }

    public void paint(Graphics g){

        // set black canvas color
        g.setColor(Color.black);
        g.fillRect(1,1,692,592);

        // for borders
        g.setColor(Color.orange);
        g.fillRect(0,0,692,3);// top borders
        g.fillRect(0,3,3,592);// left border
        g.fillRect(691,3,3,592);// right border

        // paddle
        g.setColor(Color.red);
        g.fillRect(playerX,550,100,8);

        //ball
        g.setColor(Color.green);
        g.fillOval(ballposX,ballposY,20,20);


        // bricks
        mapGenerator.draw((Graphics2D )g);

        // score
        g.setColor(Color.green);
        g.setFont(new Font("serif",Font.BOLD,30));
        g.drawString("Score : "+score,550,30);

        // game over
        if(ballposY>=570){
            play=false;
            ballXdir=0;
            ballYdir=0;

            g.setColor(Color.red);
            g.setFont((new Font("serif",Font.BOLD,40)));
            g.drawString("GameOver!!, Score : "+score,150,300);

            g.setColor(Color.green);
            g.setFont((new Font("serif",Font.BOLD,40)));
            g.drawString("Press Enter to Restart!! : ",150,350);
        }


        if(totalBricks<=0){
            play=false;
            ballXdir=0;
            ballYdir=0;

            g.setColor(Color.green);
            g.setFont((new Font("serif",Font.BOLD,40)));
            g.drawString("You Won!! : "+score,150,300);

            g.setColor(Color.green);
            g.setFont((new Font("serif",Font.BOLD,40)));
            g.drawString("Press Enter to Restart!! : ",150,350);


        }
        }




    private  void moveLeft(){
        play=true;
        playerX-=20;
    }

    private void moveRight(){
        play=true;
        playerX+=20;
    }
    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode()==KeyEvent.VK_LEFT){

            if(playerX<=0){
                playerX=0;
            }else {
                moveLeft();
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){

            if(playerX>=600){
                playerX=600;  // width plus paddle width 600 +100=700
            }else {
                moveRight();
            }
        }

        if(e.getKeyCode()==KeyEvent.VK_ENTER){
          if(!play){
              score=0;
              totalBricks=21;
              ballposX=120;
              ballposY=350;
              ballXdir=-5;
              ballYdir=-5;
              playerX=320;
              mapGenerator=new MapGenerator(3,7);
          }
        }

        repaint();

    }



    @Override
    public void actionPerformed(ActionEvent e) {

        if(play){

            if(ballposX<=0){
                ballXdir*=-1;
            }
            if(ballposY<=0){
                ballYdir*=-1;
            }
            if(ballposX>=670){
                ballXdir*=-1;
            }


            Rectangle ballRectangle=new Rectangle(ballposX,ballposY,20,20);
            Rectangle paddleRectangle=new Rectangle(playerX,550,100,8);
            if(ballRectangle.intersects(paddleRectangle)){
                ballYdir*=-1;
            }


            A:for(int i=0;i<mapGenerator.map.length;i++){
                for(int j=0;j<mapGenerator.map[i].length;j++){
                    if(mapGenerator.map[i][j]>0){
                        int width=mapGenerator.brickwidth;
                        int height=mapGenerator.brickheight;
                        int brickXpos=80+j*width;
                        int brickYpos=50+i*height;

                        Rectangle brecRectangle=new Rectangle(brickXpos,brickYpos,width,height);
                        if(ballRectangle.intersects(brecRectangle)){
                            mapGenerator.setBrick(0,i,j);
                            totalBricks--;
                            score+=5;

                            if(ballposX+19<=brickXpos || ballposX+1>=brickXpos+width){
                                ballXdir=ballXdir*-1;
                            }
                            else {
                                ballYdir= -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }
            ballposX+=ballXdir;
            ballposY+=ballYdir;


        }
        repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
