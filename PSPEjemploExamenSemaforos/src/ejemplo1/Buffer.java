package ejemplo1;

import java.util.LinkedList;
import java.util.Queue;

// 🔹 Clase que representa el buffer compartido
public class Buffer {

    private Queue<Integer> cola = new LinkedList<>();
    private int capacidad;

    public Buffer(int capacidad) {
        this.capacidad = capacidad;
    }

    // 🔹 Método para que el productor agregue un producto
    public synchronized void producir(int valor) throws InterruptedException {
        // 🔹 Si el buffer está lleno, espero
        while (cola.size() == capacidad) {
            wait();
        }

        // 🔹 Agrego el producto al buffer
        cola.add(valor);
        System.out.println("Producido: " + valor + " | Tamaño buffer: " + cola.size());

        // 🔹 Notifico al consumidor que hay un nuevo producto
        notify();
    }

    // 🔹 Método para que el consumidor tome un producto
    public synchronized int consumir() throws InterruptedException {
        // 🔹 Si el buffer está vacío, espero
        while (cola.isEmpty()) {
            wait();
        }

        // 🔹 Tomo el producto del buffer
        int valor = cola.poll();
        System.out.println("Consumido: " + valor + " | Tamaño buffer: " + cola.size());

        // 🔹 Notifico al productor que hay espacio disponible
        notify();

        return valor;
    }
}

