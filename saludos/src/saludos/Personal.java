package saludos;

public class Personal extends Thread {
	
	public String nombre;
	public boolean rango;
	
	private static final Object monitor = new Object();
	
	public Personal(String nombre, boolean rango) {
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

	public void llegadaEmpleado()throws InterruptedException {
		synchronized(monitor) {
			System.out.println(nombre+" llegó");
			monitor.wait();
			System.out.println(nombre+" dice: buenos días jeefe");
		}
		
	}
	
	public void llegadaJefe() throws InterruptedException {
		synchronized(monitor) {
			System.out.println(nombre+" llegó");
			System.out.println(nombre+" dice: buenos días Empleados");
			monitor.notifyAll();
		}
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		long duracion= (long) Math.random();
		try {
			if(isRango()==false) {
				Thread.sleep(duracion);
				llegadaEmpleado();
			}
			else {
				Thread.sleep(duracion);
				llegadaJefe();
			}
			
		}catch(InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	
	
	

}
