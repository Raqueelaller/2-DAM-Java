package productorConsumidorSemaphore;

public class CocineroSemaphore implements Runnable {

    private final AlmacenSemaphore almacen;
    private final int numPizzas;

    public CocineroSemaphore(AlmacenSemaphore almacen, int numPizzas) {
        this.almacen = almacen;
        this.numPizzas = numPizzas;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= numPizzas; i++) {

                String pizza = "Pizza-" + i;

                // simula tiempo de preparación
                Thread.sleep(200);

                // intenta poner la pizza en el almacén
                almacen.poner(pizza);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Cocinero terminado.");
    }

}
