package pizzeria;

public class Almacen {
	
	
	private String pizza;
	private boolean crear = true;
	
	public synchronized String comprar() {
		while(crear) {
			try {
				wait();
			}catch(InterruptedException e) {
				Thread.currentThread().interrupt();
				System.out.println(e.getMessage());
			}
		}
		crear=true;
		String returnPizza= pizza;
		notifyAll();
		return returnPizza;
				
	}
	
	public synchronized  void crear(String pizza) {
		while(!crear) {
			try {
				wait();
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
				System.out.println(e.getMessage());
			}
		}
		crear=false;
		this.pizza=pizza;
		notifyAll();
	}
	
	
}
