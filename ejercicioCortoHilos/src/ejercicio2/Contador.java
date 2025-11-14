package ejercicio2;

public class Contador {
	
	public int contador=0;

	public int getContador() {
		return contador;
	}

	public void setContador(int contador) {
		this.contador = contador;
	}
	
	
	public synchronized void imcrementarContador() {
		setContador(getContador()+1);
		
		
	}

}
