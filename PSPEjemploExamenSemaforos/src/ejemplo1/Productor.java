package ejemplo1;


import java.util.Random;

// 🔹 Clase que produce valores
public class Productor implements Runnable {

    private Buffer buffer;
    private Random random = new Random();

    public Productor(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) { // 🔹 Produzco 10 elementos
            try {
                buffer.producir(i);

                // 🔹 Simulo tiempo de producción
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
