package productorConsumidorSemaphore;

public class ClienteSemaphore implements Runnable {

    private final AlmacenSemaphore almacen;
    private final int totalPizzas;

    public ClienteSemaphore(AlmacenSemaphore almacen, int totalPizzas) {
        this.almacen = almacen;
        this.totalPizzas = totalPizzas;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < totalPizzas; i++) {

                // Sacamos una pizza del buffer (bloquea si está vacío)
                String pizza = almacen.sacar();

                // Simulamos tiempo comiendo
                Thread.sleep(300);

                System.out.println("[CLIENTE] Come " + pizza);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Cliente terminado.");
    }

}
