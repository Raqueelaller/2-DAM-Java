package procesos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Child {
	 public static void main(String[] args) {

	        try {
	            // Lector para recibir lo que envía el padre
	            BufferedReader br = new BufferedReader(
	                    new InputStreamReader(System.in)
	            );

	            // Leemos el mensaje enviado por el padre
	            String mensajeRecibido = br.readLine();

	            // ------------------------------------------------------
	            // Creamos un WorkerThread (archivo independiente)
	            // ------------------------------------------------------
	            WorkerThread worker = new WorkerThread(mensajeRecibido);

	            // Lanzamos el hilo
	            Thread hilo = new Thread(worker);
	            hilo.start();

	            // Esperamos a que el hilo termine
	            hilo.join();

	            // Obtenemos el resultado
	            String resultado = worker.getResultado();

	            // Lo enviamos al proceso padre
	            System.out.println(resultado);

	        } catch (IOException | InterruptedException e) {
	            e.printStackTrace();
	        }
	    }

}


