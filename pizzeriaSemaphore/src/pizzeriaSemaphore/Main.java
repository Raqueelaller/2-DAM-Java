package pizzeriaSemaphore;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
        // Número máximo de pizzas que caben en el estante
        final int CAPACIDAD = 4;

        // Número total de pizzas que el cocinero hará
        final int NUM_PIZZAS = 8;

        // Creamos el almacén, que ya incluye sus semáforos
        Almacen almacen = new Almacen(CAPACIDAD);

        // Creamos los hilos de productor y consumidor
        Thread cocinero = new Thread(new Cocinero(almacen, NUM_PIZZAS), "Cocinero");
        Thread cliente  = new Thread(new Cliente(almacen), "Cliente");

        System.out.println(" Pizzería abierta (versión semáforos)");

        // Arrancamos los hilos
        cocinero.start();
        cliente.start();

        // Esperamos a que ambos terminen
        cocinero.join();
        cliente.join();

        System.out.println("Pizzería cerrada. Todas las pizzas fueron servidas.");

	}

}
