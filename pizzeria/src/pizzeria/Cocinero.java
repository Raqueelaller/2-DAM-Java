package pizzeria;

import java.util.ArrayList;
import java.util.List;

public class Cocinero implements Runnable {
	
	
	public String nombre;
	
	public static List<String> pizzas = new ArrayList<>();
	
	private Almacen almacen;

	public Cocinero(String nombre) {
		super();
		this.nombre = nombre;
	}
	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void crearPizza(String pizza) {
			pizzas.add(pizza);
	}
	
	public void run() {
		
		
		
	}
	

}
