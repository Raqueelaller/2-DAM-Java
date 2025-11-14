package ejercicio2;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		Contador c = new Contador();
		
		Hilo hilo1 = new Hilo("hilo 1",c,10);
		Hilo hilo2 = new Hilo("hilo 2",c,10);
		//Hilo hilo3 = new Hilo("hilo 3", c, 10);
		
		Thread proces1 = new Thread(hilo1);
		Thread proces2 = new Thread(hilo2);
		//Thread proces3 = new Thread(hilo3);
		
		proces1.start();
		proces2.start();
		//proces3.start();
		
		proces1.join();
		proces2.join();
		//proces3.join();
		
		System.out.println("el contador ha sido incrementado en "+c.getContador());
		
	}

}
