package pizzeria;

public class Cliente implements Runnable {
	
    private final Almacen almacen;

    // Constructor
    public Cliente(Almacen almacen) {
        this.almacen = almacen;
    }

    @Override
    public void run() {
        try {
            String pizza;

            while ((pizza = almacen.sacarPizza()) != null) {
                System.out.printf("[%s]  Disfrutando de %s%n",
                        Thread.currentThread().getName(), pizza);

                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("Cliente: ya no quedan pizzas.");
        }
    }
}
