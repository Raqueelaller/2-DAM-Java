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

		boolean quedanBoletos=true;
				
		while(quedanBoletos == true) {
			synchronized (Vendedor.class) {
				
			      if (numeroBoletos > 0) {
	                    numeroBoletos--;
	                    System.out.printf("Quedan: %d, vendido por %s%n", numeroBoletos, nombre);
	                } else {
	                    quedanBoletos = false;
	                }
	            }

	            // Simulamos el tiempo de venta
	            try {
	                Thread.sleep(500);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }

	        System.out.println("Terminando de vender " + nombre);
	    }
		
		
	}
	
	


