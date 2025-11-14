package comunicacionProcesosHiloEnHijo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChildProcess {
	 public static void main(String[] args) {

	        try {
	            // Leemos del padre usando la entrada estándar
	            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	            String recibido = br.readLine();  // El padre escribe una sola línea

	            // Creamos un hilo que procesará el mensaje
	            WorkerThread worker = new WorkerThread(recibido);

	            Thread t = new Thread(worker);
	            t.start();   // arrancamos el hilo
	            t.join();    // esperamos a que termine

	            // Enviamos el resultado al padre usando salida estándar
	            System.out.println("Procesado: " + worker.getResultado());

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
