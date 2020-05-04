package Handler;

public class Alumno extends Thread{
    String nombre_hilo;
    Saludo avisar;
    boolean bandera;

    public Alumno (String nombre_hilo, boolean bandera, Saludo avisar) {
        this.nombre_hilo = nombre_hilo;
        this.avisar = avisar;
        this.bandera = bandera;
    }

    public void run () {
        try {
            if (bandera) {
                avisar.saludoNotifica(nombre_hilo);
            } else {
                avisar.saludoEspera(nombre_hilo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
