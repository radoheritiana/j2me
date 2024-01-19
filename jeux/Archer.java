package jeux;

import javax.microedition.lcdui.Graphics;
import java.util.Vector;


public class Archer extends GraphicsElement {
    private int velocity = 15;

    private Vector arrows = new Vector();
    private int max_fire = 2;

    public Archer(int h) {
        super(-10, h/2);
    }

    public void paint(Graphics g) {
        g.setColor(0x00ff00);
        g.drawArc(this.x, this.y, 30, 40, -90, 180);
        g.setColor(0x000000);
        g.fillRect(this.x+15, this.y+20, 30, 2);
        g.setColor(0x00aaff);
        g.fillRect(this.x+15, this.y, 1, 40);

        
        for (int i = 0; i < arrows.size(); i++) {
            Arrow arrow = (Arrow) arrows.elementAt(i);
            arrow.paint(g);
        }
        
    }

    public Vector getArrows(){
        return this.arrows;
    }

    public void fire(){
        if (this.arrows.size() <= this.max_fire){
            this.arrows.addElement(new Arrow(this.x, this.y + 20));
        }
    }

    public void removeArrow(int indice){
        this.arrows.removeElementAt(indice);
    }

    public void moveUp() {
        this.y -= this.velocity;
    }

    public void moveDown() {
        this.y += this.velocity;
    }
}
