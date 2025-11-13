package pizzeria;

import java.util.concurrent.ThreadLocalRandom;

public class Cliente implements Runnable{
	
	
	private Almacen recibir;
	
	public Cliente(Almacen recibir) {
		this.recibir=recibir;
	}
	

	@Override
	public void run() {
		for(String receivedMessage = recibir.comprar();
				!"".equals(receivedMessage);
				receivedMessage=recibir.comprar()) {
			System.out.println("El cliente está comprando 1 pizza");
			System.out.println(receivedMessage);
			try {
				Thread.sleep(ThreadLocalRandom.current().nextInt(1000,
						5000));
			}catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				System.err.println("Thread Interrupted");
				}

			
		}
	}
}
