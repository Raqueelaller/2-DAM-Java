package pizzeria;

public class Cliente implements Runnable {
	
	 // El cliente también necesita acceso al mismo almacén
    private final Almacen almacen;

    // Constructor
    public Cliente(Almacen almacen) {
        this.almacen = almacen;
    }

    // Método que se ejecuta cuando empieza el hilo del cliente
    @Override
    public void run() {
        try {
            // Variable para guardar la pizza que el cliente toma del estante
            String pizza;

            // Mientras el almacén devuelva una pizza (no null) seguimos comiendo
            while ((pizza = almacen.sacarPizza()) != null) {
                // Mensaje de que el cliente está comiendo
                System.out.printf("[%s] 😋 Disfrutando de %s%n",
                        Thread.currentThread().getName(), pizza);

                // Simulamos el tiempo que tarda en comer la pizza
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            // Si el hilo es interrumpido, lo detenemos correctamente
            Thread.currentThread().interrupt();
        } finally {
            // Cuando ya no quedan pizzas:
            System.out.println("Cliente: ya no quedan pizzas, se va feliz.");
        }
    }
}
