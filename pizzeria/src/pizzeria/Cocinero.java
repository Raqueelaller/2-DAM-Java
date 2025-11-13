package pizzeria;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Cocinero implements Runnable {
	
	
	private Almacen cocinar;

	public Cocinero (Almacen cocinar) {
		this.cocinar=cocinar;
	}
	
	
	public void run() {
		String pizzas[]= {
				"margarita", "cuatro quesos", "Carbonara",""
		};
		
		for (String pizza : pizzas) {
			System.out.println("se está creando 1 pizza"+pizza);
			cocinar.crear(pizza);
			
			try {
				Thread.sleep(ThreadLocalRandom.current().nextInt(1000,5000));
			}catch(InterruptedException e) {
				Thread.currentThread().interrupt();
				System.out.println(e.getMessage());
			}
		}
		
		
	}
	

}
