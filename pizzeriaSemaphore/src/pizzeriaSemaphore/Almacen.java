package pizzeriaSemaphore;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Almacen {
	
	// Cola donde se guardan las pizzas
    private final Queue<String> estante = new LinkedList<>();

    // Capacidad máxima del estante
    private final int capacidad;

    // Indicador para avisar al cliente de que ya no habrá más pizzas
    private boolean produccionTerminada = false;

    // -----------------------------
    // Semáforos importantes:
    // -----------------------------

    // "espaciosLibres" controla cuántos huecos quedan en el buffer.
    // Empieza en "capacidad". Cada vez que el cocinero pone una pizza, se reduce.
    private final Semaphore espaciosLibres;

    // "pizzasDisponibles" controla cuántas pizzas hay para consumir.
    // Empieza en 0. Cada vez que se pone una pizza, aumenta.
    private final Semaphore pizzasDisponibles;

    // Este semáforo actúa como exclusión mutua (mutex), impidiendo que
    // dos hilos modifiquen el estante a la vez.
    private final Semaphore mutex;

    // Constructor del almacén
    public Almacen(int capacidad) {
        this.capacidad = capacidad;

        // Semáforo con tantos permisos como capacidad del estante
        espaciosLibres = new Semaphore(capacidad);

        // Semáforo con 0 pizzas al inicio
        pizzasDisponibles = new Semaphore(0);

        // Solo un hilo puede tocar el estante a la vez (mutex clásico)
        mutex = new Semaphore(1);
    }

    // --------------------------------------------------------
    // Método para poner una pizza en el estante (Productor)
    // --------------------------------------------------------
    public void ponerPizza(String pizza) throws InterruptedException {

        // 1) El cocinero intenta coger un “hueco libre”.
        // Si no hay huecos, se quedará esperando aquí automáticamente.
        espaciosLibres.acquire();

        // 2) Ahora pedimos permiso para entrar en la zona crítica (el estante)
        mutex.acquire();

        // *** ZONA CRÍTICA ***
        estante.add(pizza);
        System.out.printf("[%s] → Añadida %s (total: %d/%d)%n",
                Thread.currentThread().getName(), pizza,
                estante.size(), capacidad);
        // *** FIN ZONA CRÍTICA ***

        // 3) Permitimos que otros hilos toquen el estante
        mutex.release();

        // 4) Indicar que hay una pizza nueva disponible para consumir
        pizzasDisponibles.release();
    }

    // --------------------------------------------------------
    // Método para sacar una pizza del estante (Consumidor)
    // --------------------------------------------------------
    public String sacarPizza() throws InterruptedException {

        // Si ya terminó la producción y ya no hay pizzas, salimos
        if (produccionTerminada && pizzasDisponibles.availablePermits() == 0)
            return null;

        // 1) El cliente espera hasta que haya alguna pizza disponible
        pizzasDisponibles.acquire();

        // 2) Entramos a la zona crítica
        mutex.acquire();

        // *** ZONA CRÍTICA ***
        String pizza = estante.poll();
        System.out.printf("[%s] ← Recoge %s %n",
                Thread.currentThread().getName(), pizza);
        // *** FIN ZONA CRÍTICA ***

        // 3) Salimos de la zona crítica
        mutex.release();

        // 4) Liberamos un espacio vacío del estante
        espaciosLibres.release();

        // Devolvemos la pizza consumida
        return pizza;
    }

    // --------------------------------------------------------
    // Aviso del cocinero: ya no habrá más pizzas
    // --------------------------------------------------------
    public void terminarProduccion() {
        produccionTerminada = true;
    }

}
