package productoConsumidorSimple;

public class Productor implements Runnable {
	
	 private final Buffer buffer;

	    public Productor(Buffer buffer) {
	        this.buffer = buffer;
	    }

	    @Override
	    public void run() {
	        try {
	            for (int i = 1; i <= 5; i++) {
	                buffer.producir(i);
	                Thread.sleep(300); // simular tiempo de producción
	            }
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        } finally {
	            buffer.terminarProduccion();
	            System.out.println("Productor: ha terminado de producir.");
	        }
	    }
	
}

/*Produce 5 elementos (1 a 5).

Duerme entre producciones para simular trabajo.

Al final, llama a terminarProduccion() para avisar al consumidor que no habrá más datos.*/