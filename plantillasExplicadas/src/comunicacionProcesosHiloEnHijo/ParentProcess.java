package comunicacionProcesosHiloEnHijo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ParentProcess {
	public static void main(String[] args) {

        try {
            // Lanzamos el proceso hijo: "java ChildProcess"
            // (se asume que todas las clases están compiladas en la misma carpeta)
            ProcessBuilder pb = new ProcessBuilder("java", "comunicacionProcesosHiloEnHijo.ChildProcess");
            Process p = pb.start();

            // Escribimos al hijo por su entrada estándar
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(p.getOutputStream()));

            writer.write("Hola hijo");
            writer.newLine();
            writer.flush();  // obligatorio, o el hijo no recibe nada

            // Leemos la respuesta del hijo
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));

            String respuesta = reader.readLine();

            System.out.println("Padre recibe: " + respuesta);

            // Esperamos a que el hijo termine
            p.waitFor();

            System.out.println("Hijo finalizado.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
