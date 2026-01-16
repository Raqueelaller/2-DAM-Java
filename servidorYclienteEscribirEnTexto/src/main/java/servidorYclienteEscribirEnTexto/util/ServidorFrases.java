package servidorYclienteEscribirEnTexto.util;

// IMPORTS DE RED
import java.net.ServerSocket;
import java.net.Socket;

// IMPORTS DE IO
import java.io.*;

// IMPORTS DE CODIFICACIÓN
import java.nio.charset.StandardCharsets;

// IMPORTS DE LISTAS
import java.util.ArrayList;
import java.util.List;

/*
 SERVIDOR TCP QUE ENVÍA FRASES:

 FUNCIONAMIENTO:
 - Carga frases desde src/main/resources/frases.txt
 - Cada cliente recibe UNA frase
 - Frases rotan secuencialmente
 - Compatible con JAR Maven
*/

public class ServidorFrases {

    private static final int PUERTO = 6001;

    // Lista compartida entre hilos
    private static List<String> frases;

    // Índice actual de frase
    private static int indiceActual = 0;

    // ============================
    // MAIN
    // ============================
    public static void main(String[] args) {

        // Cargar frases al arrancar servidor
        cargarFrasesDesdeResources();

        System.out.println("Servidor frases iniciado");
        System.out.println("Puerto: " + PUERTO);

        try (ServerSocket server = new ServerSocket(PUERTO)) {

            while (true) {

                Socket cliente = server.accept();

                new Thread(() -> manejarCliente(cliente)).start();
            }

        } catch (IOException e) {
            System.err.println("Error servidor: " + e.getMessage());
        }
    }

    // ============================
    // ATENDER CLIENTE
    // ============================
    private static void manejarCliente(Socket socket) {

        try (
            PrintWriter salida = new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8),
                true
            )
        ) {

            // Obtener frase sincronizada
            String frase = obtenerSiguienteFrase();

            // Enviar frase
            salida.println(frase);

        } catch (IOException e) {
            System.err.println("Error cliente: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }

    // ============================
    // CARGAR FRASES DESDE RESOURCES
    // ============================
    private static void cargarFrasesDesdeResources() {

        frases = new ArrayList<>();

        try {

            // Leer recurso desde el classpath del JAR
            InputStream is = ServidorFrases.class
                    .getResourceAsStream("/frases.txt");

            if (is == null) {
                frases.add("ERROR: frases.txt no encontrado");
                return;
            }

            BufferedReader br = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8)
            );

            String linea;
            while ((linea = br.readLine()) != null) {

                if (!linea.trim().isEmpty()) {
                    frases.add(linea);
                }
            }

            System.out.println("Frases cargadas desde resources");

        } catch (Exception e) {
            frases.add("Error cargando frases");
        }
    }

    // ============================
    // MÉTODO SINCRONIZADO
    // ============================
    private static synchronized String obtenerSiguienteFrase() {

        if (frases.isEmpty()) {
            return "Sin frases";
        }

        String frase = frases.get(indiceActual);

        // Avance circular
        indiceActual = (indiceActual + 1) % frases.size();

        return frase;
    }
}
