package pizzeria;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Almacen pizzeria = new Almacen();
		
		Thread comprador = new Thread(new Cliente(pizzeria));
		Thread cocinero = new Thread(new Cocinero(pizzeria));
		
		comprador.start();
		cocinero.start();
	}

}
