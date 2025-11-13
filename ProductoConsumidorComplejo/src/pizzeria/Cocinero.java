package pizzeria;

import java.util.Random;

public class Cocinero implements Runnable  {

	 // El cocinero necesita acceso al almacén para poner pizzas
    private final Almacen almacen;

    // Cuántas pizzas debe preparar
    private final int numPizzas;

    // Objeto para generar tiempos aleatorios
    private final Random rnd = new Random();

    // Constructor
    public Cocinero(Almacen almacen, int numPizzas) {
        this.almacen = almacen;
        this.numPizzas = numPizzas;
    }

    // Este método se ejecuta cuando empieza el hilo (al hacer start())
    @Override
    public void run() {
        try {
            // El cocinero prepara numPizzas pizzas
            for (int i = 1; i <= numPizzas; i++) {

                // Simulamos el tiempo de preparación (entre 300 y 700 ms)
                Thread.sleep(300 + rnd.nextInt(400));

                // Creamos el nombre de la pizza
                String pizza = "Pizza-" + i;

                // Llamamos al método del almacén para poner la pizza
                almacen.ponerPizza(pizza);
            }
        } catch (InterruptedException e) {
            // Si el hilo es interrumpido, detenemos su ejecución correctamente
            Thread.currentThread().interrupt();
        } finally {
            // Cuando ya no quedan más pizzas por hacer:
            almacen.terminarProduccion();
            System.out.println("Cocinero: terminó de preparar todas las pizzas.");
        }
    }
}
