package procesos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Parent {
	
	 public static void main(String[] args) {

	        try {
	            // Lanzamos el hijo usando el nombre completo del paquete
	            ProcessBuilder pb = new ProcessBuilder(
	                    "java",
	                    "procesos.Child"
	            );

	            Process childProcess = pb.start();

	            // Enviar mensaje al hijo
	            BufferedWriter haciaHijo = new BufferedWriter(
	                    new OutputStreamWriter(childProcess.getOutputStream())
	            );

	            haciaHijo.write("Hola hijo");
	            haciaHijo.newLine();
	            haciaHijo.flush();

	            // Leer respuesta del hijo
	            BufferedReader desdeHijo = new BufferedReader(
	                    new InputStreamReader(childProcess.getInputStream())
	            );

	            String respuesta = desdeHijo.readLine();
	            System.out.println("El hijo respondió: " + respuesta);

	            childProcess.waitFor();
	            System.out.println("Proceso hijo finalizado.");

	        } catch (IOException | InterruptedException e) {
	            e.printStackTrace();
	        }
	    }

}
