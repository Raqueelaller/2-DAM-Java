package pizzeria;

public class Pincipal {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
         int CAPACIDAD = 4;

         int NUM_PIZZAS = 8;

        Almacen almacen = new Almacen(CAPACIDAD);

        Thread cocinero = new Thread(new Cocinero(almacen, NUM_PIZZAS), "Cocinero");
        Thread cliente  = new Thread(new Cliente(almacen), "Cliente");

        System.out.println(" Pizzería abierta ");

        cocinero.start();
        cliente.start();

        cocinero.join();
        cliente.join();

        System.out.println(" Pizzería cerrada. Todas las pizzas fueron servidas.");

	}

}
