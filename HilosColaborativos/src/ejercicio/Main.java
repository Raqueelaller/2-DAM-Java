package ejercicio;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Vendedor juan = new Vendedor("juan",2);
		Vendedor antonio = new Vendedor("antonio",2);
		Vendedor adolfo = new Vendedor("adolfo",2);
		 
		//2. crear los objetos Thread, pasando la tarea (Runnable) y el nombre
			
		Thread t1 = new Thread(juan);
		Thread t2 = new Thread(antonio);
		Thread t3 = new Thread(adolfo);
				
				//4.iniciar la ejecución de los hilos
				//LLAMAR A start() ES FUNDAMENTAL, NO a run()
				t1.start();
				t2.start();
				t3.start();
								
				  // 5. Esperar a que terminen todos los hilos
		        try {
		            t1.join();
		            t2.join();
		            t3.join();
		        } catch (InterruptedException e) {
		            System.out.println("El hilo principal fue interrumpido.");
		        }
		        
		    

		    }

	}


