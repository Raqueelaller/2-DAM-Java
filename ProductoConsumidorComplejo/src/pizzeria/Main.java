package pizzeria;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		 // Capacidad del estante (máximo número de pizzas que caben a la vez)
        final int CAPACIDAD = 4;

        // Número total de pizzas que el cocinero va a preparar
        final int NUM_PIZZAS = 8;

        // Creamos el almacén (el estante compartido entre cocinero y cliente)
        Almacen almacen = new Almacen(CAPACIDAD);

        // Creamos los dos hilos:
        // - uno para el cocinero (productor)
        // - otro para el cliente (consumidor)
        Thread cocinero = new Thread(new Cocinero(almacen, NUM_PIZZAS), "Cocinero");
        Thread cliente  = new Thread(new Cliente(almacen), "Cliente");

        // Mensaje inicial
        System.out.println("🍕 Pizzería abierta 🍕");

        // Arrancamos los hilos (empiezan a ejecutarse de forma paralela)
        cocinero.start();
        cliente.start();

        // Esperamos a que ambos terminen antes de cerrar la pizzería
        cocinero.join();
        cliente.join();

        // Mensaje final
        System.out.println("🏁 Pizzería cerrada. Todas las pizzas fueron servidas.");

	}

}
