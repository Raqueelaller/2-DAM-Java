package ejemplo1;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Banco {

    private static int ventanillasLibres = 2;           // Ventanillas disponibles
    private static final List<String> cola = new LinkedList<>();  // Cola de clientes
    private static final Random random = new Random();

    public static void main(String[] args) {
        int numClientes = 10;

        // Crear hilos para cada cliente
        for (int i = 1; i <= numClientes; i++) {
            String nombre = "Cliente " + i;

            // Crear objeto ClienteRunnable
            Cliente cliente = new Cliente(nombre);

            // Crear hilo y arrancarlo
            Thread t = new Thread(cliente);
            t.start();
        }
    }

    // Método que atiende al cliente (estático, compartido por todos)
    public static void atenderCliente(String nombre) {
        synchronized (cola) {
            System.out.println(nombre + " llega al banco.");
            cola.add(nombre);

            // Esperar hasta que sea su turno y haya ventanilla libre
            while (!cola.get(0).equals(nombre) || ventanillasLibres == 0) {
                try {
                    cola.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            cola.remove(0);
            ventanillasLibres--;
            System.out.println(nombre + " está siendo atendido. Ventanillas libres: " + ventanillasLibres);
        }

        // Simular tiempo de atención
        try {
            Thread.sleep(random.nextInt(2000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Liberar ventanilla y avisar a los siguientes
        synchronized (cola) {
            ventanillasLibres++;
            System.out.println(nombre + " ha terminado. Ventanillas libres: " + ventanillasLibres);
            cola.notifyAll();
        }
    }
}
