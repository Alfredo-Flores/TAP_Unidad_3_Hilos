package Handler;

import Views.MainView;

import javax.swing.*;

public class Sprite implements Runnable {
    private int index = 0;
    private volatile boolean running = true;
    private volatile boolean paused = false;
    private final Object pauseLock = new Object();

    public void stop() {
        running = false;

        resume();
    }

    public void pause() {
        paused = true;
    }

    public boolean isPaused() {
        return paused;
    }

    public void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
    }

    @Override
    public void run() {
        while (running) {
            synchronized (pauseLock) {
                if (!running) {
                    break;
                }
                if (paused) {
                    try {
                        synchronized (pauseLock) {
                            pauseLock.wait();
                        }
                    } catch (InterruptedException ex) {
                        break;
                    }
                    if (!running) {
                        break;
                    }
                }
            }

            try {
                MainView.sprite.setIcon(new ImageIcon(new ImageIcon("src/Views/assets/sprite/" + this.index + ".gif").getImage()));
                this.index++;
                if (this.index == 14) this.index = 0;
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
