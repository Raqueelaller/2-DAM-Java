package ejercicio1;

import java.util.Iterator;

public class Hilo implements Runnable {
	
	public String nombreHilo;
	public int empieza;
	public int acaba;
	

	public Hilo(String nombreHilo, int empieza, int acaba) {
		super();
		this.nombreHilo = nombreHilo;
		this.empieza = empieza;
		this.acaba = acaba;
	}

	public String getNombreHilo() {
		return nombreHilo;
	}

	public void setNombreHilo(String nombreHilo) {
		this.nombreHilo = nombreHilo;
	}


	public int getEmpieza() {
		return empieza;
	}

	public void setEmpieza(int empieza) {
		this.empieza = empieza;
	}

	public int getAcaba() {
		return acaba;
	}

	public void setAcaba(int acaba) {
		this.acaba = acaba;
	}

	@Override
	public void run() {
		
		try {
			for(int i=getEmpieza(); i<(getAcaba()+1);i++) {
				
				System.out.println("el hilo: "+ getNombreHilo()+ "número: "+i);
				Thread.sleep(1000);
			}
			
		}catch(InterruptedException x) {
			System.out.println(x.getMessage());
		}
		
		
	}
	
	
	

}
