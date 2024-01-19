package jeux;

import javax.microedition.lcdui.Graphics;

public class Apple extends GraphicsElement {

    private int w;
    private int h;
    private int p;
    private int angle;

    public Apple(int _x, int _y) {
        super(_x, _y);
        this.w = 20;
        this.h = 20;
        this.p = 0;
        this.angle = 360;
    }

    public int getRadius(){
        return this.w;
    }

    public void paint(Graphics g){
        g.setColor(0xff0000);
        g.fillArc(this.x, this.y, this.w, this.h, this.p, this.angle);
    }
    
}
