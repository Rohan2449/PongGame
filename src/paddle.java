package src;

import java.awt.Rectangle;

public class paddle extends Rectangle {
    public paddle(int x, int y ,int width, int height) {
        super(x,y, height ,width);
    }
    public void up(int paddleSpeed) {
        translate(0,-1 * paddleSpeed);
    }
    public void down(int paddleSpeed) {
        translate(0,paddleSpeed);
    }




}
