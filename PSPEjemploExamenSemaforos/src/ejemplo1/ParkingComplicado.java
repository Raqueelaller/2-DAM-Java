package ejemplo1;
//En este caso tengo un contador manual de plazas libres, intentos multiples y un tiempo 
//de llegada aleatorio


import java.util.concurrent.Semaphore;
import java.util.Random;

public class ParkingComplicado {

	public static final Semaphore semaforo = new Semaphore(5, true); // 5 plazas, modo justo
	public static final Random random = new Random();

	// Contadores compartidos
	public static int cochesEntrados = 0;
	public static int plazasLibres = 5;

	public static void main(String[] args) {

		int coches = 15; // total de coches que intentarán entrar

		for (int i = 0; i < coches; i++) {

			String nombre = "Coche " + (i + 1);

			Coche coche = new Coche(nombre); 
			Thread hilo = new Thread(coche);

			// Tiempo de llegada aleatorio antes de intentar entrar
			try {
				Thread.sleep(random.nextInt(2000)); // 0-2 segundos
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}

			hilo.start();
		}
	}
}


