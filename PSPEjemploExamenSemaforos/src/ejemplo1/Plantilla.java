package ejemplo1;


import java.util.concurrent.Semaphore;
import java.util.Random;

public class Plantilla {

    // 🔹 Número máximo de recursos disponibles (baños, platos, tenedores, plazas de parking)
    private static final int RECURSOS = 3;

    // 🔹 Semáforo que controla cuántos hilos pueden usar el recurso a la vez
    private static final Semaphore semaforo = new Semaphore(RECURSOS);

    // 🔹 Generador de números aleatorios para tiempos
    private static final Random random = new Random();

    public static void main(String[] args) {

        int NUM_HILOS = 10; // Número de hilos (personas, pitufos, coches, filósofos)
        Thread[] listaHilos = new Thread[NUM_HILOS];

        // 🔹 Crear todos los hilos
        for (int i = 0; i < NUM_HILOS; i++) {

            String nombre = "Usuario " + (i + 1); // Cambiar por Coche, Pitufo, Filósofo, etc.

            // 🔹 Creo el hilo usando Runnable clásico
            listaHilos[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    usarRecurso(nombre);
                }
            });

            // 🔹 Tiempo de llegada aleatorio entre hilos
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // 🔹 Arrancar todos los hilos
        for (int i = 0; i < NUM_HILOS; i++) {
            listaHilos[i].start();
        }
    }

    // 🔹 Método que simula el uso de un recurso compartido
    private static void usarRecurso(String nombre) {

        // 🔹 Varias rondas de uso (opcional)
        for (int ronda = 1; ronda <= 3; ronda++) { // Cambiar número de rondas si se quiere

            try {
                System.out.println(nombre + " quiere usar el recurso. Ronda " + ronda);

                // 🔹 Intento ocupar un recurso (si hay disponibles, continúa; si no, espera)
                semaforo.acquire();

                System.out.println(nombre + " está usando el recurso. Ronda " + ronda);

                // 🔹 Simular tiempo usando el recurso
                Thread.sleep(random.nextInt(2000) + 1000);

                System.out.println(nombre + " ha terminado de usar el recurso. Ronda " + ronda);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 🔹 Mantengo la interrupción si ocurre
            } finally {
                // 🔹 Libero el recurso para que otro hilo pueda usarlo
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
