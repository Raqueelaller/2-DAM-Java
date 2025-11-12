package banyos;

import java.util.concurrent.Semaphore;

public class Persona extends Thread {
	
	public String nombre;
	
	private static final Semaphore banyo =new Semaphore(3);

	public Persona(String nombre) {
		
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	@Override
	public void run() {
		long tiempoUso = (long)(Math.random()*3000);
		
			try {
				
				banyo.acquire();
				System.out.println(nombre+" entró al baño");
				Thread.sleep(tiempoUso);
			}catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			} finally {
			System.out.println(nombre+" Salió del baño");
			banyo.release();// El hilo libera el permiso
			
			}

		
			
		}
		
	}
	


