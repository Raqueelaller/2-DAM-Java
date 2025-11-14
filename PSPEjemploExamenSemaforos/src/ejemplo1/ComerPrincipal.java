package ejemplo1;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ComerPrincipal {

    private static final int MAX_COMEDORES = 3; // Solo 3 pitufos pueden comer a la vez
    private static int pitufosComiendo = 0;     // Contador de pitufos comiendo
    private static final List<String> cola = new LinkedList<>(); // Cola de espera
    private static final Random random = new Random();

    public static void main(String[] args) {
        int numPitufos = 10;

        for (int i = 1; i <= numPitufos; i++) {
            String nombre = "Pitufo " + i;

            // Creamos el objeto Runnable
            Pitufo pitufoRunnable = new Pitufo(nombre);

            // Creamos el hilo
            Thread pitufo = new Thread(pitufoRunnable);

            // Arrancamos el hilo
            pitufo.start();
        }
    }

    // Método que controla el comer del pitufo
    public static void comer(String nombre) {
        synchronized (cola) {
            System.out.println(nombre + " llega al frasco de sopa.");
            cola.add(nombre);

            // Esperar turno y disponibilidad
            while (!cola.get(0).equals(nombre) || pitufosComiendo >= MAX_COMEDORES) {
                try {
                    cola.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            cola.remove(0);
            pitufosComiendo++;
            System.out.println(nombre + " está comiendo. Pitufos comiendo: " + pitufosComiendo);
        }

        // Simular tiempo comiendo
        try {
            Thread.sleep(random.nextInt(3000) + 1000); // 1 a 4 segundos
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Terminar de comer
        synchronized (cola) {
            pitufosComiendo--;
            System.out.println(nombre + " ha terminado de comer. Pitufos comiendo: " + pitufosComiendo);
            cola.notifyAll();
        }
    }
}
