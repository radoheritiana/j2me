package jeux;

import java.util.Timer;
import java.util.TimerTask;

public class Chronometer {
    private Timer timer;
    private long elapsedTime;
    private boolean isRunning;

    public Chronometer() {
        timer = new Timer();
        elapsedTime = 0;
        isRunning = false;
    }

    public void start() {
        if (!isRunning) {
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    elapsedTime += 1000;
                }
            }, 0, 1000);
            isRunning = true;
        }
    }

    public void pause() {
        if (isRunning) {
            timer.cancel();
            timer = new Timer();
            isRunning = false;
        }
    }

    public void resume() {
        if (!isRunning) {
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    elapsedTime += 1000;
                }
            }, 0, 1000);
            isRunning = true;
        }
    }

    public void stop() {
        timer.cancel();
        isRunning = false;
    }

    public long getSecondes() {
        return elapsedTime / 1000 % 60;
    }
}
