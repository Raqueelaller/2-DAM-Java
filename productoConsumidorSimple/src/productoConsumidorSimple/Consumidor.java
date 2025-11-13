package productoConsumidorSimple;

public class Consumidor implements Runnable {
	
	private final Buffer buffer;

    public Consumidor(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            Integer valor;
            while ((valor = buffer.consumir()) != null) {
                Thread.sleep(500); // simular tiempo de consumo
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("Consumidor: ha terminado de consumir.");
        }
    }
	

}
/*Extrae elementos mientras consumir() no devuelva null.

Cuando el productor termina, el consumidor acaba su bucle de forma natural.*/
