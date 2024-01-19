package jeux;

import javax.microedition.lcdui.Graphics;


public class Arrow extends GraphicsElement {

    private int longueur = 30;
    private int largeur = 2;

    public Arrow(int x, int y){
        super(x, y);
    }

    public void paint(Graphics g) {
        g.setColor(0x000000);
        g.fillRect(this.x, this.y, longueur, largeur);
    }

    public boolean collide(Apple apple){
        boolean is_collide = false;

        // Vérifier si les segments verticaux du rectangle et du cercle se chevauchent
        if (
            this.x + this.longueur >= apple.getX() && this.x <= apple.getX() + apple.getRadius()
        ) {

            // Vérifier si les segments horizontaux du rectangle et du cercle se chevauchent
            if (
                this.y + this.largeur >= apple.getY() && this.y + this.largeur <= apple.getY() + apple.getRadius()
            ) {
                is_collide = true;
            }
        }

        return is_collide;
    }
    
}
