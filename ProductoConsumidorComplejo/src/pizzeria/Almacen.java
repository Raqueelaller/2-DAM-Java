package pizzeria;

import java.util.LinkedList;
import java.util.Queue;

public class Almacen {
	 // Cola donde se guardan las pizzas (primero que entra, primero que sale)
    private final Queue<String> estante = new LinkedList<>();

    // Capacidad máxima del estante
    private final int capacidad;

    // Esta variable indica si el cocinero ha terminado de producir pizzas
    private boolean produccionTerminada = false;

    // Constructor: se llama cuando hacemos "new Almacen(...)"
    public Almacen(int capacidad) {
        this.capacidad = capacidad;
    }

    // --------------------------------------------------------
    // Método synchronized: añadir una pizza al estante
    // --------------------------------------------------------
    // "synchronized" significa que solo un hilo puede ejecutar este método
    // a la vez (evita que dos hilos modifiquen la lista al mismo tiempo).
    public synchronized void ponerPizza(String pizza) throws InterruptedException {
        // Si el estante está lleno, el cocinero debe esperar.
        while (estante.size() == capacidad) {
            // "wait()" detiene este hilo hasta que alguien llame a "notifyAll()"
            wait();
        }

        // Añadimos la pizza al estante
        estante.add(pizza);

        // Mostramos un mensaje en pantalla
        System.out.printf("[%s] → Añadida %s (total: %d/%d)%n",
                Thread.currentThread().getName(), pizza, estante.size(), capacidad);

        // Despertamos a los hilos que estaban esperando (por ejemplo, el cliente)
        notifyAll();
    }

    // --------------------------------------------------------
    // Método synchronized: sacar una pizza del estante
    // --------------------------------------------------------
    public synchronized String sacarPizza() throws InterruptedException {
        // Si el estante está vacío...
        while (estante.isEmpty()) {
            // ...y el cocinero ya terminó, significa que no habrá más pizzas.
            if (produccionTerminada) return null;

            // Si no ha terminado, esperamos a que el cocinero ponga más pizzas.
            wait();
        }

        // Sacamos (quitamos) la primera pizza de la cola
        String pizza = estante.poll();

        // Mostramos qué pizza se consumió y cuántas quedan
        System.out.printf("[%s] ← Recoge %s (restan: %d)%n",
                Thread.currentThread().getName(), pizza, estante.size());

        // Despertamos a los hilos que puedan estar esperando (por ejemplo, el cocinero)
        notifyAll();

        // Devolvemos la pizza para que el cliente la “coma”
        return pizza;
    }

    // --------------------------------------------------------
    // Método para avisar que el cocinero ha terminado de producir pizzas
    // --------------------------------------------------------
    public synchronized void terminarProduccion() {
        produccionTerminada = true;
        // Despertamos a los consumidores que podrían seguir esperando
        notifyAll();
    }

}
