package src;

import java.awt.Color;
import java.awt.Graphics2D;
import utilities.GDV5;

public class PongRunner extends GDV5 {



    public static void main(String[] args) {
        PongRunner p1 = new PongRunner();

        p1.setBackground(Color.white);
        p1.setTitle("");
        p1.start();

    }

    @Override
    public void update() {

        runGame.startGame();

    }


    @Override
    public void draw(Graphics2D win) {
        Interface.gameInterface(win);

    }
}
