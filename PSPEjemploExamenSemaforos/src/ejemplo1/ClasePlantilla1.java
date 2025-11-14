package ejemplo1;

import java.util.concurrent.Semaphore;
import java.util.Random;

// 🔹 Clase que implementa Runnable
public class ClasePlantilla1 implements Runnable {

    private String nombre;
    private static final int MAX_RECURSOS = 3; // Máximo número de usuarios a la vez
    private static final Semaphore semaforo = new Semaphore(MAX_RECURSOS);
    private static final Random random = new Random();

    // 🔹 Constructor solo con el nombre
    public ClasePlantilla1(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {

        // 🔹 Varias rondas de uso del recurso
        for (int ronda = 1; ronda <= 3; ronda++) {
            try {
                System.out.println(nombre + " quiere usar el recurso. Ronda " + ronda);

                // 🔹 Intento ocupar un recurso
                semaforo.acquire();

                System.out.println(nombre + " está usando el recurso. Ronda " + ronda);

                // 🔹 Simular tiempo usando el recurso
                Thread.sleep(random.nextInt(2000) + 1000);

                System.out.println(nombre + " ha terminado de usar el recurso. Ronda " + ronda);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                // 🔹 Libero el recurso
                semaforo.release();
            }

            // 🔹 Tiempo de espera opcional antes de la siguiente ronda
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println(nombre + " ha completado todas sus rondas.");
    }
}

