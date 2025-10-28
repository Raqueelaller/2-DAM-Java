package hilos1;

import java.util.Iterator;

public class TareaHilo implements Runnable{

	private String nombreHilo;
	private int numero;
	
	
	public TareaHilo (String nombre, int numero) {
		this.nombreHilo=nombre;
		System.out.println("creando"+nombreHilo);
		this.numero=numero;
	}
		
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Ejecutando"+nombreHilo);
		
		
		try {
			//el hilo realiza una tarea sencilla:contar
			for (int i = 1; i <=5; i++) {
				System.out.println("hilo: "+nombreHilo+", contador: "+i);
				// hace una pequeña pausa para asimilar el trabajo y mostrar concurencia
				Thread.sleep(numero); // pausa de un número aleatorio que damos desde el principal.
				
			}
			}catch(InterruptedException x) {
				System.out.println("el hilo "+nombreHilo+ "fue interrumpido.");
			}
		System.out.println("Terminando"+nombreHilo);
			
		}
	
		
	}
	
	
	



