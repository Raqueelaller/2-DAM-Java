package ejemplo1;


import java.util.concurrent.Semaphore;
import java.util.Random;

// Clase principal
public class PrincipalFilosofo {

    private static final int NUM_FILOSOFOS = 5;          // Número de filósofos
    private static final int RONDAS = 3;                 // Número de rondas de comida
    private static final Semaphore[] tenedores = new Semaphore[NUM_FILOSOFOS]; // Semáforos de tenedores
    private static final Random random = new Random();

    public static void main(String[] args) {

        // Inicializo los semáforos, cada tenedor disponible inicialmente
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            tenedores[i] = new Semaphore(1);
        }

        // Creo y lanzo los hilos de cada filósofo
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            String nombre = "Filosofo " + (i + 1);
            Thread filosofo = new Thread(new Filosofo(nombre, i));
            filosofo.start();
        }
    }

    // Método que simula el proceso de comer de un filósofo
    public static void comer(String nombre, int posicion) {

        int izquierda = posicion;                      // Tenedor de la izquierda
        int derecha = (posicion + 1) % NUM_FILOSOFOS; // Tenedor de la derecha

        for (int ronda = 1; ronda <= RONDAS; ronda++) {

            try {
                // El filósofo piensa un tiempo aleatorio
                System.out.println(nombre + " está pensando. (Ronda " + ronda + ")");
                Thread.sleep(random.nextInt(2000) + 1000);

                System.out.println(nombre + " quiere comer. (Ronda " + ronda + ")");

                // Para evitar deadlock, el último filósofo coge primero el tenedor derecho
                if (posicion == NUM_FILOSOFOS - 1) {
                    tenedores[derecha].acquire();
                    tenedores[izquierda].acquire();
                } else {
                    tenedores[izquierda].acquire();
                    tenedores[derecha].acquire();
                }

                // El filósofo está comiendo
                System.out.println(nombre + " está comiendo. (Ronda " + ronda + ")");
                Thread.sleep(random.nextInt(2000) + 1000);

                // El filósofo termina de comer y suelta los tenedores
                tenedores[izquierda].release();
                tenedores[derecha].release();
                System.out.println(nombre + " ha terminado de comer y suelta los tenedores. (Ronda " + ronda + ")");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(nombre + " fue interrumpido.");
            }
        }

        // Mensaje final cuando termina todas las rondas
        System.out.println(nombre + " ha terminado todas sus rondas de comida.");
    }
}
