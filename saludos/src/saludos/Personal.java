package saludos;

public class Personal extends Thread {
	
	public String nombre;
	public boolean rango;
	
	private static final Saludo s = new Saludo();
	private static  boolean bandera = false;
	
	public Personal(String nombre,Saludo s,boolean rango) {
		super();
		this.nombre = nombre;
		this.rango = rango;
	
	}
	
	public Personal() {
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isRango() {
		return rango;
	}

	public void setRango(boolean rango) {
		this.rango = rango;
	}

	public void llegada()throws InterruptedException {
		synchronized(s) {
			System.out.println(nombre+" llegó");
			if(rango==true) {
				
				System.out.println(nombre+" dice: buenos días Empleados");
				bandera=true;
				s.notifyAll();
		} else {
			while(bandera==false) {
				s.wait();
			}
			System.out.println(nombre+" dice: buenos días jefe");
		}
			
	}
		

	}
	
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		long duracion= (long) Math.random();
		try {
			llegada();
			
			
		}catch(InterruptedException e) {
			System.out.println(e.getMessage());
		}

	}
	
	
	
	
	

}
