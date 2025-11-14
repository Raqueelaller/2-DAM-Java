package ejemplo1;

import java.util.concurrent.Semaphore;
import java.util.Random;

public class Parking {

    // Semáforo con 5 plazas disponibles
    public static final Semaphore semaforo = new Semaphore(5);
    public static final Random random = new Random();

    public static void main(String[] args) {
        
        int coches = 15;

        // Crear y arrancar los hilos
        for (int i = 0; i < coches; i++) {

            String nombre = "Coche " + (i + 1);

            Coche coche = new Coche(nombre);  // Crear objeto Runnable
            Thread hilo = new Thread(coche); // Pasarlo al hilo

            hilo.start(); // Arrancar el hilo
        }
    }
}
