package ejercicio1;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		Hilo hilo1 = new Hilo("hilo 1: ",1,5);
		Hilo hilo2 = new Hilo("hilo 2: ",6,10);
		
		Thread proces1 = new Thread(hilo1);
		Thread proces2 = new Thread(hilo2);
		
		proces1.start();
		proces2.start();
		
		proces1.join();
		proces2.join();
				
		
	}

}
