package ejercicio;

public class Raton extends Thread{

	private String nombre;
	private int tiempo;
	
	public Raton(String nombre, int tiempo) {
		this.nombre=nombre;
		this.tiempo=tiempo;
	}
	
	public void run() {
		try {
			System.out.printf("%s empieza la merienda %n",nombre);
			Thread.sleep(1000*tiempo);
			System.out.printf("%s termino de comer %n", nombre);
			
		}catch(InterruptedException x) {
			System.out.println(x.getMessage());
		}
	}
}
