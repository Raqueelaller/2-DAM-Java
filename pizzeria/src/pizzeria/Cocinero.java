package pizzeria;

import java.util.Random;

public class Cocinero implements Runnable  {

    private final Almacen almacen;

    private final int numPizzas;

    private final Random rnd = new Random();

    public Cocinero(Almacen almacen, int numPizzas) {
        this.almacen = almacen;
        this.numPizzas = numPizzas;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= numPizzas; i++) {

                Thread.sleep(300 + rnd.nextInt(400));

                String pizza = "Pizza-" + i;

                almacen.ponerPizza(pizza);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            almacen.terminarProduccion();
            System.out.println("Cocinero: terminó de preparar todas las pizzas.");
        }
    }
}
