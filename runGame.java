package pong;

import java.awt.*;
import java.awt.event.KeyEvent;
import utilities.GDV5;

import static pong.Interface.*;

public class runGame {
    static int gameStatus = 0;
    static int maxScore = 3;
    static int scoreL = 0;
    static int scoreR = 0;
    static int reset = 1;
    static boolean isSinglePlayer = true;
    static int speedX = (int) (Math.random() * 6 + 6);
    static int speedY = (int) (Math.random() * 3 + 1);
    static int paddleHeight = 150;
    static int paddleWidth = 20;
    static int paddleSpeed = 10;
    static int prevSpeedX = speedX;
    static int prevSpeedY = speedY;

    static int prevSpeedXCalc = speedX;
    static int prevSpeedYCalc = speedY;
    static int numBounces;

    static int hitArea;
    static int prevCordY = GDV5.getMaxWindowY() / 2;
    static int prevCordX = GDV5.getMaxWindowX() / 2;
    public static void startGame() {
        if(gameStatus ==0){
            Interface.blinker++;
        }


        if (gameStatus == 1) {
            if (Interface.countDown > 0){
                countDown--;
            }
            if(Interface.countDown < 60) {
                //sets ball in motion
                Interface.ball1.velocity(speedX, speedY);

            }
            if (GDV5.KeysPressed[KeyEvent.VK_ESCAPE]) {
                gameStatus = 0;
            }

            //if ball out of Y-bounds, reverses Y speed.
            if (Interface.ball1.getY() + Interface.ball1.getHeight() >= GDV5.getMaxWindowY()) {
                speedY *= -1;
            }
            if (Interface.ball1.getY() <= 0) {
                speedY *= -1;
            }




            //if ball out of X bounds, adds score, resets ball.

            if (Interface.ball1.getX() <= -20 || (Interface.ball1.getX() >= GDV5.getMaxWindowX() + 20)) {
                prevSpeedX = speedX;
                prevSpeedY = speedY;
                Interface.ball1.setLocation(GDV5.getMaxWindowX() / 2, GDV5.getMaxWindowY() / 2);
                if (speedX > 0) {
                    Interface.scoreL += 1;
                } else {
                    Interface.scoreR += 1;
                }

                speedX = 0;
                speedY = 0;

                reset = 0;
            }


            //if reset is 0, you can reset ball with SPACEBAR
            //random Y speed
            if (reset == 0) {
                if (GDV5.KeysPressed[KeyEvent.VK_SPACE]) {

                    //random Y Speed
                    double negPos = Math.random();
                    if (negPos < 0.5) {
                        speedY = -(int) (Math.random() * 3 + 1);
                    } else {
                        speedY = (int) (Math.random() * 3 + 1);
                    }

                    //sets a random X speed, positive or negative based on who scored the ball.
                    if (prevSpeedX < 0) {
                        speedX = (int) (Math.random() * 6 + 6);

                    } else {
                        speedX = -(int) (Math.random() * 6 + 6);
                    }
                    reset = 1;
                    prevSpeedXCalc = speedX;
                    prevSpeedYCalc = speedY;
                    prevCordY = (int) Interface.ball1.getCenterY();
                    prevCordX = (int) Interface.ball1.getCenterX();
                }
            }
            //right Paddle controls
            if (Interface.paddleR.getY() > 0) {
                if (GDV5.KeysPressed[KeyEvent.VK_UP]) {
                    Interface.paddleR.up(paddleSpeed);
                }
            }
            if (Interface.paddleR.getY() + Interface.paddleR.getHeight() < GDV5.getMaxWindowY()) {
                if (GDV5.KeysPressed[KeyEvent.VK_DOWN]) {
                    Interface.paddleR.down(paddleSpeed);
                }
            }





            //left paddle AUTO control for SINGLE PLAYER
            if(isSinglePlayer) {
                //calculates the final Y intercept of the ball
                if (speedX < 0) {
                    numBounces = 0;
                    int finalY = (((prevCordX -(int)Interface.paddleL.getWidth()) / Math.abs(prevSpeedXCalc)) * prevSpeedYCalc) + prevCordY;
                    if (finalY < 0) {
                        numBounces = (Math.abs(finalY) / GDV5.getMaxWindowY());
                        numBounces += 1;
                    } else if (finalY > GDV5.getMaxWindowY()) {
                        numBounces = (Math.abs(finalY - GDV5.getMaxWindowY()) / GDV5.getMaxWindowY());
                        numBounces += 1;
                    }

                    if (numBounces % 2 == 0 && numBounces > 0) {
                        if (prevSpeedYCalc < 0) {
                            finalY = GDV5.getMaxWindowY() - (Math.abs(finalY) % GDV5.getMaxWindowY());
                        } else {
                            finalY %= GDV5.getMaxWindowY();
                        }
                    } else if (numBounces % 2 == 1) {
                        if (prevSpeedYCalc < 0) {
                            finalY = (Math.abs(finalY) % GDV5.getMaxWindowY());
                        } else {
                            finalY = GDV5.getMaxWindowY() - (finalY % GDV5.getMaxWindowY());
                        }
                    }


                    //1 - hit Top, 2- hit Middle, 3- hit Bottom
                    if (hitArea == 2) {
                        if (Interface.paddleL.getCenterY() < finalY) {
                            if (Interface.paddleL.getY() + Interface.paddleL.getHeight() < GDV5.getMaxWindowY()) {
                                Interface.paddleL.down(paddleSpeed);
                            }
                        }
                        if (Interface.paddleL.getCenterY() > finalY) {
                            if (Interface.paddleL.getY() > 0) {
                                Interface.paddleL.up(paddleSpeed);
                            }
                        }
                    } else if (hitArea == 3) {
                        if (Interface.paddleL.getY() + (Interface.paddleL.getHeight() * 4 / 7) < finalY) {
                            if (Interface.paddleL.getY() + Interface.paddleL.getHeight() < GDV5.getMaxWindowY()) {
                                Interface.paddleL.down(paddleSpeed);
                            }
                        }
                        if (Interface.paddleL.getY() + (Interface.paddleL.getHeight() * 4 / 7) >finalY) {
                            if (Interface.paddleL.getY() > 0) {
                                Interface.paddleL.up(paddleSpeed);
                            }

                        }
                    } else {
                        if (Interface.paddleL.getY() + Interface.paddleL.getHeight() *  2/ 7 < finalY) {
                            if (Interface.paddleL.getY() + Interface.paddleL.getHeight() < GDV5.getMaxWindowY()) {
                                Interface.paddleL.down(paddleSpeed);
                            }

                        }
                        if (Interface.paddleL.getY() + Interface.paddleL.getHeight() * 2 / 7 > finalY) {
                            if (Interface.paddleL.getY() > 0) {
                                Interface.paddleL.up(paddleSpeed);
                            }

                        }
                    }
                }

                //centers the Paddle to the middle of the screen after hitting the ball
                if (speedX > 0) {
                        if (Interface.paddleL.getCenterY() > GDV5.getMaxWindowY() / 2) {
                            Interface.paddleL.up(paddleSpeed);
                        }
                        if (Interface.paddleL.getCenterY() < GDV5.getMaxWindowY() / 2) {
                            Interface.paddleL.down(paddleSpeed);
                        }

                    }

            }






            //for multiplayer
            if(!isSinglePlayer) {

            //left paddle controls for multiplayer
            if (Interface.paddleL.getY() > 0) {
                if (GDV5.KeysPressed[KeyEvent.VK_W]) {
                    Interface.paddleL.up(paddleSpeed);
                }
            }
            if (Interface.paddleL.getY() + Interface.paddleL.getHeight() < GDV5.getMaxWindowY()) {
                if (GDV5.KeysPressed[KeyEvent.VK_S]) {
                    Interface.paddleL.down(paddleSpeed);
                }
            }
		         

            }
                //Ball physics in contact with left paddle


                if (Interface.ball1.intersects(Interface.paddleL)) {
                    speedX = Math.abs(speedX);
                    speedX += 0.5;



                    if (Interface.ball1.getCenterY() >= Interface.paddleL.getY() + Interface.paddleL.getHeight() * 2 / 3) {
                        speedY += (int) (Math.abs(speedX) + Math.abs(speedY))/4;
                    } else if (Interface.ball1.getCenterY() >= Interface.paddleL.getY() + Interface.paddleL.getHeight() * 1 / 3) {
                        speedX += 1;
                    } else {
                        speedY -= (int) (Math.abs(speedX) + Math.abs(speedY))/4;
                    }
                }



                //Ball physics in contact with right paddle.

                if (Interface.ball1.intersects(Interface.paddleR)) {

                    speedX = -Math.abs(speedX);
                    speedX -= 0.5;



                        if (Interface.ball1.getCenterY() >= Interface.paddleR.getY() + Interface.paddleR.getHeight() * 2 / 3) {
                            speedY += (int) (Math.abs(speedX) + Math.abs(speedY))/4;
                        } else if (Interface.ball1.getCenterY() >= Interface.paddleR.getY() + Interface.paddleR.getHeight() * 1 / 3) {
                            speedX -= 1;
                        } else {
                            speedY -= (int) (Math.abs(speedX) + Math.abs(speedY))/4;
                        }

                    if(isSinglePlayer) {
                        prevSpeedXCalc = speedX;
                        prevSpeedYCalc = speedY;
                        prevCordY = (int) Interface.ball1.getCenterY();
                        prevCordX = (int) Interface.ball1.getCenterX();
                        hitArea = (int) (Math.random() * 3 + 1);
                    }

                }

                if (Interface.scoreL == maxScore) {
                    gameStatus = 2;
                }
                if (Interface.scoreR == maxScore) {
                    gameStatus = 3;
                }

            }


        }
    }


