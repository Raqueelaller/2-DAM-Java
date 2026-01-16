package servidorYclienteEscribirEnTexto.util;

// IMPORTS DE RED
import java.net.ServerSocket;
import java.net.Socket;

// IMPORTS DE ENTRADA/SALIDA
import java.io.*;

// IMPORTS DE CODIFICACIÓN
import java.nio.charset.StandardCharsets;

/*
 SERVIDOR TCP QUE GUARDA TEXTO:

 FUNCIONAMIENTO:
 - Escucha conexiones TCP en un puerto
 - Por cada cliente crea un hilo
 - Recibe líneas de texto
 - Guarda las líneas en un archivo entrada.txt
 - Cuando recibe "fin" termina la conexión

 ES IDEAL PARA:
 - Ejercicios de sockets
 - Comunicación cliente-servidor
 - Escritura en ficheros
*/

public class ServidorGuardarTxt {

    // Puerto donde escucha el servidor
    private static final int PUERTO = 6000;

    // Archivo donde se guardarán los textos recibidos
    private static final String ARCHIVO = "entrada.txt";

    // ============================
    // MÉTODO MAIN
    // ============================
    public static void main(String[] args) {

        System.out.println("Servidor guardando texto en archivo TXT");
        System.out.println("Puerto: " + PUERTO);

        // try-with-resources: asegura cierre automático del ServerSocket
        try (ServerSocket server = new ServerSocket(PUERTO)) {

            // Bucle infinito: servidor siempre activo
            while (true) {

                // accept() BLOQUEA hasta que un cliente se conecta
                Socket cliente = server.accept();

                System.out.println("Cliente conectado desde "
                        + cliente.getInetAddress().getHostAddress());

                // Se crea un hilo para este cliente
                new Thread(() -> manejarCliente(cliente)).start();
            }

        } catch (IOException e) {
            System.err.println("Error servidor: " + e.getMessage());
        }
    }

    // ============================
    // MÉTODO QUE ATIENDE UN CLIENTE
    // ============================
    private static void manejarCliente(Socket socket) {

        // try-with-resources: cierra streams automáticamente
        try (
            // Para leer texto enviado por el cliente
            BufferedReader entrada = new BufferedReader(
                new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)
            );

            // Para enviar respuestas al cliente
            PrintWriter salida = new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8),
                true // autoflush
            );

            // FileWriter en modo append (true)
            // NO borra el archivo existente
            BufferedWriter bw = new BufferedWriter(
                new FileWriter(ARCHIVO, true)
            )
        ) {

            // Mensaje inicial al cliente
            salida.println("OK Envia texto (fin para terminar)");

            String linea;

            // Leer líneas hasta recibir "fin"
            while ((linea = entrada.readLine()) != null) {

                // Si el cliente escribe "fin" se termina
                if (linea.equalsIgnoreCase("fin")) {
                    salida.println("OK Texto guardado. Cerrando conexion.");
                    break;
                }

                // Guardar línea en el archivo
                bw.write(linea);
                bw.newLine();
                bw.flush(); // Forzar escritura en disco

                // Confirmar al cliente
                salida.println("OK guardado");
            }

        } catch (IOException e) {
            System.err.println("Error cliente: " + e.getMessage());
        } finally {
            // Cerrar socket cliente
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }
}
