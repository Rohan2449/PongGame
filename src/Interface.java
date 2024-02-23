package src;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.awt.Image;


import utilities.GDV5;

import javax.swing.*;


public class Interface {
    static String gameName = "PADDLE PONG";
    static int scoreL;
    static int scoreR;
    static int countDown;
    static int blinker;
    
    static Font fgtSS = new Font("Founders Grotesk Text", Font.BOLD, 30);
    static Font fgtS =new Font("Founders Grotesk Text",Font.BOLD, 40);
    static Font kufi = new Font("KufiStandardGK", Font.PLAIN, 60);
    
    static ball ball1 = new ball(15);
    static paddle paddleL = new paddle(0, (GDV5.getMaxWindowY() -runGame.paddleHeight) / 2, runGame.paddleHeight, runGame.paddleWidth);
    static paddle paddleR = new paddle(GDV5.getMaxWindowX() - runGame.paddleWidth, (GDV5.getMaxWindowY()-runGame.paddleHeight) / 2, runGame.paddleHeight, runGame.paddleWidth);


    public static void drawCenteredString(String str, int desiredX,int desiredY, Graphics2D win){

        FontMetrics fm = win.getFontMetrics();
        int x = desiredX - fm.stringWidth(str) /2;
        int y = desiredY + fm.getHeight()/2 ;

        win.drawString(str, x,y);
        
    }//hello
    public static void gameInterface(Graphics2D win)  {
        Locale locale = new Locale.Builder().setLanguageTag("ar-SA-u-nu-arab").build();
        DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance(locale);
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);


        win.setColor(Color.black);
        win.fill(Interface.ball1);
        win.setFont(fgtS);

        if(runGame.gameStatus ==0) {
            if(GDV5.KeysPressed[KeyEvent.VK_DOWN] || GDV5.KeysPressed[KeyEvent.VK_S]){
                blinker = 20;
                runGame.isSinglePlayer = false;
            }
            if(GDV5.KeysPressed[KeyEvent.VK_UP] || GDV5.KeysPressed[KeyEvent.VK_W]){
                blinker = 20;

                runGame.isSinglePlayer = true;
            }

        	win.setFont(fgtS);
            win.setColor(Color.black);
            drawCenteredString(gameName, GDV5.getMaxWindowX()/2, 100, win);
            win.setFont(fgtSS);
            if(!runGame.isSinglePlayer) {
                drawCenteredString("W", 80, GDV5.getMaxWindowY() / 4, win);
                drawCenteredString("S", 80, GDV5.getMaxWindowY() * 3 / 4, win);
            }
            AffineTransform orig = win.getTransform();
            win.rotate(Math.PI, GDV5.getMaxWindowX()/2, GDV5.getMaxWindowY()/2);
            drawCenteredString("V", 80, GDV5.getMaxWindowY()*3/4 - win.getFontMetrics().getHeight()/2, win);
            drawCenteredString("V", GDV5.getMaxWindowX() - 80, GDV5.getMaxWindowY()*3/4, win);
            win.setFont(fgtS);
            win.setTransform(orig);

            drawCenteredString("SPACE", GDV5.getMaxWindowX()/2, GDV5.getMaxWindowY()*7/8, win);

            win.setFont(fgtS);
                if (runGame.isSinglePlayer) {
                    if((blinker/35)%2==1) {
                        drawCenteredString("1 PLAYER", GDV5.getMaxWindowX() / 2, 200, win);
                    }
                    drawCenteredString("2 PLAYER", GDV5.getMaxWindowX() / 2, 250, win);

                }
                if (!runGame.isSinglePlayer) {
                    if((blinker/35)%2==1) {
                        drawCenteredString("2 PLAYER", GDV5.getMaxWindowX() / 2, 250, win);
                    }
                    drawCenteredString("1 PLAYER", GDV5.getMaxWindowX() / 2, 200, win);

                }

            if (GDV5.KeysPressed[KeyEvent.VK_SPACE]) {
                countDown = 4* 60;
                runGame.gameStatus = 1;
            }

        }


        else{
        	win.setFont(kufi);
            drawCenteredString("" +  scoreL, GDV5.getMaxWindowX()/4, 200, win);
            drawCenteredString("" + scoreR, GDV5.getMaxWindowX()*3/4, 200, win);
            win.setFont(fgtS);

            if(countDown >= 60){        	
            	win.setFont(kufi);

                win.drawString("" + countDown/60, GDV5.getMaxWindowX() / 2, 200);
                win.setFont(fgtS);
                drawCenteredString("SCORE " + (runGame.maxScore) , GDV5.getMaxWindowX()/2, GDV5.getMaxWindowY()*7/8, win);

            }
            if(runGame.reset == 0 &&(!(scoreL == runGame.maxScore || scoreR == runGame.maxScore))) {
                drawCenteredString("SPACE", GDV5.getMaxWindowX()/2, GDV5.getMaxWindowY()*7/8, win);

            	
            }

            win.setColor(Color.darkGray);
            win.fill(paddleL);
            win.setColor(Color.lightGray);
            win.fill(paddleR);
            win.setColor(Color.black);
            if (runGame.gameStatus == 2) {

                drawCenteredString("WINNER LEFT", GDV5.getMaxWindowX() / 2, GDV5.getMaxWindowY() / 2 - 100, win);
                win.drawString("ESC", 75,75 + win.getFontMetrics().getHeight()/2);

                if (GDV5.KeysPressed[KeyEvent.VK_ESCAPE]) {
                    runGame.gameStatus = 0;
                    Interface.scoreL = 0;
                    Interface.scoreR = 0;
                }

            }
            if (runGame.gameStatus == 3) {
                drawCenteredString("RIGHT WINNER", GDV5.getMaxWindowX() / 2, GDV5.getMaxWindowY() / 2 - 100, win);

                win.drawString("ESC", 75,75 + win.getFontMetrics().getHeight() / 2);

                if (GDV5.KeysPressed[KeyEvent.VK_ESCAPE]) {
                    runGame.gameStatus = 0;
                    Interface.scoreL = 0;
                    Interface.scoreR = 0;
                }

            }


        }
    }
}
