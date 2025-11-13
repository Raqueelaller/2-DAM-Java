package productoConsumidorSimple;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		 Buffer buffer = new Buffer(3); // capacidad máxima del buffer

	        Thread productor = new Thread(new Productor(buffer), "Productor");
	        Thread consumidor = new Thread(new Consumidor(buffer), "Consumidor");

	        System.out.println("Inicio del programa...");

	        productor.start();
	        consumidor.start();

	        try {
	            productor.join();
	            consumidor.join();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	        System.out.println("Fin del programa.");
	    

	}

}
/*main() crea el buffer y los dos hilos, los arranca y espera a que terminen.

Los mensajes en consola te muestran el orden de producción y consumo.*/