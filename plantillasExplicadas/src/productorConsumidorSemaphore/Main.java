package productorConsumidorSemaphore;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		 // Capacidad del almacén (número máximo de pizzas almacenables).
        int capacidad = 3;

        // Total de pizzas que se van a producir y consumir.
        int totalPizzas = 8;

        // Creamos el almacén con semáforos.
        AlmacenSemaphore almacen = new AlmacenSemaphore(capacidad);

        // Creamos los hilos productor y consumidor.
        Thread cocinero = new Thread(new CocineroSemaphore(almacen, totalPizzas));
        Thread cliente = new Thread(new ClienteSemaphore(almacen, totalPizzas));

        // Arrancamos los hilos.
        cocinero.start();
        cliente.start();

        // Esperamos a que ambos terminen.
        cocinero.join();
        cliente.join();

        System.out.println("Fin.");
	}

}
