package ejercicio1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Pitufo extends Thread {
	
	private static final Semaphore comedor =new Semaphore(3);
	
	private static int contador=0;
	
	public final String[] comida ={"berenjenas rellenas","Fabada","ajo blanco casero","ensalada tropical"};
	public final String[] postre = {"sandia", "melon", "natillas", "tiramisú", "helado"}; 
	
	
	public String nombre;

	public Pitufo(String nombre) {
		
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String almuerza() {
		return comida[numAleatorio(4)];
		
	}
	
	public String tomaPostre() {
		return postre[numAleatorio(5)];
	}
	public int numAleatorio(int numero) {
		
		int numAleatorio = (int)(Math.random()*numero);
		
		return numAleatorio;
	}
	
	
	@Override
	public void run() {
		long tiempoUso = (long)(Math.random()*3000+500);
		
		
			try {
				
				comedor.acquire();
				contador=contador+1;
				System.out.println(nombre+" entró al comedor y almuerza "+almuerza());
				System.out.println("Hay ahora mismo "+contador + " pitufos en el comedor");
				System.out.println("");
				Thread.sleep(tiempoUso);
				System.out.println(nombre+ " come de postre "+tomaPostre());
				
				
				Thread.sleep(tiempoUso);
				
			}catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			} finally {
				contador=contador-1;
				System.out.println(nombre+" Salió del comedor quedando "+contador+" pitufos");
			comedor.release();// El hilo libera el permiso
			
			
			
			
			}

		
			
		}
	

}
