package Handler;

import javax.swing.*;

public class Saludo {
    boolean bandera = false;

    public Saludo() {
    }

    public synchronized void saludoEspera(String nombre_hilo) {
        try {
            if (bandera == false) {
                wait();
            }

            JOptionPane.showMessageDialog(null, "Buenos dias, soy " + nombre_hilo);
        } catch (InterruptedException ex) {
            System.out.println("Algo anda mal");
        }
    }

    public synchronized void saludoNotifica(String nombre_hilo) {
        bandera = true;
        JOptionPane.showMessageDialog(null, "Buenos dias, soy " + nombre_hilo);
        notifyAll();
    }
}

