package pizzeria;

import java.util.LinkedList;
import java.util.Queue;

public class Almacen {
    private final Queue<String> estante = new LinkedList<>();

    private final int capacidad;

    private boolean produccionTerminada = false;

    public Almacen(int capacidad) {
        this.capacidad = capacidad;
    }

    public synchronized void ponerPizza(String pizza) throws InterruptedException {
        while (estante.size() == capacidad) {
            wait();
        }

        estante.add(pizza);

        System.out.printf("[%s] añade %s%n",
                Thread.currentThread().getName(), pizza, estante.size(), capacidad);

        notifyAll();
    }


    public synchronized String sacarPizza() throws InterruptedException {
        while (estante.isEmpty()) {
            if (produccionTerminada) return null;

            wait();
        }

        String pizza = estante.poll();

        System.out.printf("[%s] recoge %s %n",
                Thread.currentThread().getName(), pizza, estante.size());

        notifyAll();

        return pizza;
    }


    public synchronized void terminarProduccion() {
        produccionTerminada = true;
        notifyAll();
    }

}
