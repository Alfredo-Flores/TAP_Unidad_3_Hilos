package Handler;

import Views.MainView;

import javax.swing.*;

public class Cronometro implements Runnable {
    long seconds = 0;
    private volatile boolean running = true;
    private volatile boolean paused = false;
    private final Object pauseLock = new Object();

    public double getSeconds(long seconds) {
        return ((seconds % 86400) % 3600) % 60;
    }

    public double getMinutes(long seconds) {
        return ((seconds % 86400) % 3600) / 60;
    }

    public double getHours(long seconds) {
        return (seconds % 86400) / 3600;
    }

    public void updateClock() {
        int sec = (int) getSeconds(this.seconds);
        int min = (int) getMinutes(this.seconds);
        int hour = (int) getHours(this.seconds);

        String clockupdated = String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec);

        MainView.reloj.setText(clockupdated);
    }

    public void registerClock() {
        int sec = (int) getSeconds(this.seconds);
        int min = (int) getMinutes(this.seconds);
        int hour = (int) getHours(this.seconds);

        String clockupdated = String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec);

        MainView.modeloreloj.addElement(clockupdated);
        MainView.listareloj.setModel(MainView.modeloreloj);
    }

    public void stop() {
        MainView.modeloreloj = new DefaultListModel<String>();
        MainView.listareloj.setModel(new DefaultListModel<String>());
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
                Thread.sleep(1000);
                this.seconds += 1;
                updateClock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
