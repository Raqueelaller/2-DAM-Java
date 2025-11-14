package ejemplo1;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ComerPrincipalRondas {

    // Solo 3 pitufos pueden comer al mismo tiempo
    private static final int MAX_COMEDORES = 3;

    // Contador de pitufos que están comiendo ahora mismo
    private static int pitufosComiendo = 0;

    // Cola de pitufos que esperan su turno
    private static final List<String> cola = new LinkedList<>();

    private static final Random random = new Random();

    public static void main(String[] args) {
        int numPitufos = 10;       // Número de pitufos
        int rondas = 2;             // Número de veces que cada pitufo comerá

        // Creo los hilos de cada pitufo
        for (int i = 1; i <= numPitufos; i++) {
            String nombre = "Pitufo " + i;
            Thread pitufo = new Thread(new PitufoRondas(nombre, rondas));
            pitufo.start();
        }
    }

    // Método que controla la acción de comer
    public static void comer(String nombre, int rondas) {
        for (int r = 1; r <= rondas; r++) { // Bucle para varias rondas
            try {
                // Simulo tiempo de llegada aleatorio antes de unirse a la cola
                int llegada = random.nextInt(3000); // 0 a 3 segundos
                System.out.println(nombre + " se va a acercar al frasco en " + llegada + " ms.");
                Thread.sleep(llegada);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(nombre + " fue interrumpido antes de llegar al frasco.");
            }

            synchronized (cola) {
                System.out.println(nombre + " llega al frasco y se apunta a la cola para la ronda " + r + ".");
                cola.add(nombre);

                // Espero mi turno y a que haya plazas disponibles
                while (!cola.get(0).equals(nombre) || pitufosComiendo >= MAX_COMEDORES) {
                    try {
                        cola.wait(); // Espero a que me toque
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println(nombre + " fue interrumpido mientras esperaba.");
                    }
                }

                // Ya es mi turno: salgo de la cola y empiezo a comer
                cola.remove(0);
                pitufosComiendo++;
                System.out.println(nombre + " está comiendo en la ronda " + r + ". Pitufos comiendo: " + pitufosComiendo);
            }

            // Simulo tiempo comiendo
            try {
                Thread.sleep(random.nextInt(3000) + 1000); // 1 a 4 segundos
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(nombre + " fue interrumpido mientras comía.");
            }

            synchronized (cola) {
                // Terminé de comer, libero la plaza y notifico a los demás
                pitufosComiendo--;
                System.out.println(nombre + " ha terminado la ronda " + r + ". Pitufos comiendo: " + pitufosComiendo);
                cola.notifyAll(); // Despierto a todos los pitufos que esperan
            }

            // Comentario: Si quisiera que los pitufos intenten comer de nuevo más tarde, podría usar un sleep extra aquí
        }
    }
}

