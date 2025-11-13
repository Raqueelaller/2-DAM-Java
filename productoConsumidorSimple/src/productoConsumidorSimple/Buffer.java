package productoConsumidorSimple;

import java.util.LinkedList;
import java.util.Queue;

public class Buffer {
	 private final Queue<Integer> cola = new LinkedList<>();
	    private final int capacidad;
	    private boolean produccionTerminada = false;

	    public Buffer(int capacidad) {
	        this.capacidad = capacidad;
	    }

	    // Método sincronizado: añadir un elemento
	    public synchronized void producir(int valor) throws InterruptedException {
	        while (cola.size() == capacidad) {
	            wait(); // Esperar si está lleno
	        }

	        cola.add(valor);
	        System.out.println(Thread.currentThread().getName() + " produjo: " + valor);
	        notifyAll(); // Avisar a consumidores
	    }

	    // Método sincronizado: sacar un elemento
	    public synchronized Integer consumir() throws InterruptedException {
	        while (cola.isEmpty()) {
	            if (produccionTerminada) return null; // fin
	            wait(); // Esperar si está vacío
	        }

	        int valor = cola.poll();
	        System.out.println(Thread.currentThread().getName() + " consumió: " + valor);
	        notifyAll(); // Avisar a productores
	        return valor;
	    }

	    public synchronized void terminarProduccion() {
	        produccionTerminada = true;
	        notifyAll();
	    }

}
/*Tiene una cola (Queue<Integer>) de capacidad fija.

producir() → añade un número si hay espacio, o espera si está lleno.

consumir() → saca un número si hay alguno, o espera si está vacío.

wait() y notifyAll() controlan la coordinación entre hilos.
*/