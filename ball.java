package pong;

import java.awt.Rectangle;

public class ball extends Rectangle  {

    public ball(int length) {
        super(1200/2,800/2, length,length);


    }


    public void velocity(int xS, int yS) {
        this.x += xS;
        this.y += yS;
    }

}
