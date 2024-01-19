package jeux;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import java.io.InputStream;
import java.util.Random;
import java.util.Vector;


public class MyCanvas extends Canvas implements Runnable {

    private boolean runner = false;
    private boolean isPaused = false;
    private boolean gameOver = false;
    private Apple apple;
    private Apple[] liApples = new Apple[6];
    private Vector apples = new Vector();
    private Random random = new Random();
    private Archer archer;
    private Player firePlayer;

    final int[] X_POS = {80, 140, 200};

    String s_score = "Score: 0";
    int score = 0; 

    String s_time = "Time: 60";
    int timeLimit = 60;
    long currentTime = 60;

    Chronometer chronometer = new Chronometer();

    int last_random_x = 0;

    public MyCanvas() {

        for(int i=0; i < 6; i++) {
            apples.addElement(new Apple(generateRandomX(), -generateRandomY()));
        }


        archer = new Archer(getHeight());
    }

    protected void paint(Graphics g) {
        g.setColor(0xffffff);
        g.fillRect(0, 0, getWidth(), getHeight());


        if(gameOver) {
            g.setColor(0x000000);
            g.drawString("Jeu termine!", (getWidth()/2) - 30, getHeight()/3, Graphics.TOP|Graphics.LEFT);
            g.drawString("Votre Score: " + score, (getWidth()/2) - 40, (getHeight()/3) + 30, Graphics.TOP|Graphics.LEFT);
            g.drawString("Meuilleur score: " + String.valueOf(HighScoreManager.getHighScore()), (getWidth()/2) - 45, (getHeight()/3) + 60, Graphics.TOP|Graphics.LEFT);
        } 
        else {
            if (isPaused){
                g.setColor(0x000000);
                g.drawString("Pause", (getWidth()/2) - 15, getHeight()/2, Graphics.TOP|Graphics.LEFT);
            }
            else {
                archer.paint(g);    
                for (int i = 0; i < apples.size(); i++) {
                    Apple apple = (Apple) apples.elementAt(i);
                    apple.paint(g);
                }
            }
            //on dessine le panneau qui contient le score et le temps
            g.setColor(0x44aa99);
            g.fillRect(0, 0, getWidth(), 25);
            //on dessine le score et le temps
            g.setColor(0xffffff);
            g.drawString(s_time, 5, 5, Graphics.TOP|Graphics.LEFT);        
            g.drawString(s_score, 190, 5, Graphics.TOP|Graphics.LEFT);
        }
    }

    public void run() {

        while (runner) {
            try {

                for (int i = 0; i < apples.size(); i++) {

                    Apple apple = (Apple) apples.elementAt(i);
                    
                    Vector arrows = archer.getArrows();
                    for (int j=0; j < arrows.size(); j++) {
                        Arrow arrow = (Arrow) arrows.elementAt(j);
                        if(arrow.collide(apple)){
                            //on augmente le score
                            score++;
                            //on supprime la fleche
                            archer.removeArrow(j);

                            //on detruit le pomme
                            apples.removeElementAt(i);

                            apples.addElement(new Apple(generateRandomX(), -generateRandomY()));
                            continue;
                        }
                    }

                    if (apple.getY() < getHeight()) {
                        apple.setY(apple.getY() + 3);
                    }

                    // si l'element depasse le frontière de y
                    if(apple.getY() >= getHeight()) {
                        apples.removeElementAt(i);
                        apples.addElement(new Apple(generateRandomX(), -generateRandomY()));
                    }
                }

                Vector arrows = archer.getArrows();
                for(int i=0; i < arrows.size(); i++) {
                    Arrow arrow = (Arrow) arrows.elementAt(i);
                    if(arrow.getX() < getWidth()){
                        arrow.setX(arrow.getX() + 15);
                    } else {
                        archer.removeArrow(i);
                    }
                }

                //on redefinit le score
                s_score = "Score: " + score;
                
                //on redefinit le temps
                currentTime = timeLimit - chronometer.getSecondes();
                s_time = "Temps: " + String.valueOf(currentTime);

                // game over
                if(currentTime <= 1) {
                    chronometer.stop();
                    int highScore = HighScoreManager.getHighScore();
                    if (score > highScore) {
                        HighScoreManager.saveHighScore(score);
                    }
                    gameOver = true;
                }

                repaint();
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void start() {
        Thread t = new Thread(this);
        t.start();
        runner = true;
        chronometer.start();
    }

    public void stop() {
        chronometer.stop();
        runner = false;
    }

    public void pause() {
        isPaused = true;
        repaint();
        chronometer.pause();
        runner = false;
    }

    public void play() {
        isPaused = false;
        repaint();
        chronometer.resume();
        runner = true;
    }

    protected void keyPressed(int keyCode) {


        if(runner) {
            if (keyCode == -1) {//up
                if(archer.getY() > 30) {
                    archer.moveUp();
                }
            } 
            
            if(keyCode == -2) {//down
                if(archer.getY() < getHeight() - 50) {
                    archer.moveDown();
                }
            }

            if (keyCode == -5) {
                archer.fire();
            }
        }
    }

    protected void keyRepeated(int keyCode) {
        if(runner){
            if (keyCode == -1) {//up
                if(archer.getY() > 30) {
                    archer.moveUp();
                }
            } 
            
            if(keyCode == -2) {//down
                if(archer.getY() < getHeight() - 50) {
                    archer.moveDown();
                }
            }
        }
    }

    public int generateRandomX(){
        int indice = random.nextInt(3);

        while (indice == last_random_x) {
            indice = random.nextInt(3);
        }

        last_random_x = indice;

        return X_POS[indice];
    }

    private int generateRandomY(){
        int min = 0;
        int max = 300;
        int step = 100;

        // Calculer la plage effective en prenant en compte le pas
        int effectiveRange = (max - min) / step;

        // Générer un nombre aléatoire dans la plage effective
        int randomNumber = this.random.nextInt(effectiveRange + 1);

        // Appliquer le pas pour obtenir le nombre final
        int result = min + randomNumber * step;
        return result;
    }

    private void playFireSound() {
        try {
          InputStream in = getClass().getResourceAsStream("/fire.wav");
          Player player = Manager.createPlayer(in, "audio/x-wav");
          player.start();
        }
        catch (Exception e) {
          e.printStackTrace();
        }
    }
}
