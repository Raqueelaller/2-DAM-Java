package productorConsumidorSemaphore;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class AlmacenSemaphore {
	
	  // Cola interna que guarda los items.
    private final Queue<String> estante = new LinkedList<>();

    // Número máximo de items que caben.
    private final int capacidad;

    // Semáforo que indica cuántos huecos libres quedan.
    private final Semaphore espaciosLibres;

    // Semáforo que indica cuántos items hay disponibles para consumir.
    private final Semaphore itemsDisponibles;

    // Semáforo para garantizar exclusión mutua al acceder a la cola.
    private final Semaphore mutex;

    public AlmacenSemaphore(int capacidad) {
        this.capacidad = capacidad;
        this.espaciosLibres = new Semaphore(capacidad); // al inicio todo vacío
        this.itemsDisponibles = new Semaphore(0);
        this.mutex = new Semaphore(1); // solo un hilo en la cola a la vez
    }

    // Método del productor
    public void poner(String item) throws InterruptedException {

        // Esperamos hasta que haya espacio disponible
        espaciosLibres.acquire();

        // Entramos en la sección crítica
        mutex.acquire();

        estante.add(item);
        System.out.println("[ALMACÉN] Añadido: " + item + " (size=" + estante.size() + ")");

        // Salimos de sección crítica
        mutex.release();

        // Avisamos al consumidor de que ya hay item disponible
        itemsDisponibles.release();
    }

    // Método del consumidor
    public String sacar() throws InterruptedException {

        // Esperamos hasta que haya algún item disponible
        itemsDisponibles.acquire();

        // Zona crítica para manipular la cola
        mutex.acquire();

        String item = estante.poll();
        System.out.println("[ALMACÉN] Sacado: " + item + " (size=" + estante.size() + ")");

        mutex.release();

        // Liberamos un hueco
        espaciosLibres.release();

        return item;
    }
}
