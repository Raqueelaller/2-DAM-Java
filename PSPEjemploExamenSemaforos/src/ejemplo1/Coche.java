package ejemplo1;

public class Coche implements Runnable {

    private String nombre;

    public Coche(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        usarParking();
    }

    private void usarParking() {
        try {
            System.out.println(nombre + " quiere entrar al parking.");

            Parking.semaforo.acquire();
            System.out.println(nombre + " está aparcado.");

            Thread.sleep(Parking.random.nextInt(3000));

            System.out.println(nombre + " sale del parking.");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

        } finally {
            Parking.semaforo.release();
        }
    }
}
