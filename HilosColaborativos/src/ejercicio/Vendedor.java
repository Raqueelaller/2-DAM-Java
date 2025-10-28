package ejercicio;

public class Vendedor implements Runnable {

	private String nombre;
	private static int numeroBoletos=10;
	private int tiempo;
	
	public Vendedor(String nombre, int tiempo) {
		this.nombre=nombre;
		this.tiempo=tiempo;
	}
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("vendiendo..."+nombre);
		try {
			Thread.sleep(1000*tiempo);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for(int i=0; i<numeroBoletos;i++) {
			this.numeroBoletos=numeroBoletos-1;
			System.out.printf("quedan: %d, vendido por %s %n",numeroBoletos, nombre);
		}
		System.out.println("Terminando de vender"+nombre);
	}
	
	

}
