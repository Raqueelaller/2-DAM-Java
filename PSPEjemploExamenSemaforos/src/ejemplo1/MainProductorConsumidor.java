package ejemplo1;


public class MainProductorConsumidor {

    public static void main(String[] args) {

        // 🔹 Creo el buffer compartido donde el productor pondrá productos y el consumidor los cogerá
        Buffer buffer = new Buffer(5); // tamaño máximo del buffer

        // 🔹 Creo el productor y el consumidor
        Thread productor = new Thread(new Productor(buffer));
        Thread consumidor = new Thread(new Consumidor(buffer));

        // 🔹 Arranco los hilos
        productor.start();
        consumidor.start();
    }
}
