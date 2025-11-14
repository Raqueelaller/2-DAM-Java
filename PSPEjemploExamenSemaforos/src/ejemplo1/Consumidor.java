package ejemplo1;


import java.util.Random;

// 🔹 Clase que consume valores
public class Consumidor implements Runnable {

    private Buffer buffer;
    private Random random = new Random();

    public Consumidor(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) { // 🔹 Consumo 10 elementos
            try {
                buffer.consumir();

                // 🔹 Simulo tiempo de consumo
                Thread.sleep(random.nextInt(1500));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

