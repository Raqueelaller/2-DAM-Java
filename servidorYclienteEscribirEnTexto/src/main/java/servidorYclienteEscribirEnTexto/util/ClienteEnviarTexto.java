package servidorYclienteEscribirEnTexto.util;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/*
 CLIENTE TCP:
 - Se conecta al servidor
 - Envía texto por teclado
 - Recibe confirmación
*/

public class ClienteEnviarTexto {

    public static void main(String[] args) throws Exception {

        String host = "127.0.0.1";
        int puerto = 6000;

        try (
            Socket socket = new Socket(host, puerto);

            BufferedReader teclado = new BufferedReader(
                new InputStreamReader(System.in)
            );

            BufferedReader entrada = new BufferedReader(
                new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)
            );

            PrintWriter salida = new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8),
                true
            )
        ) {

            // Mensaje inicial del servidor
            System.out.println(entrada.readLine());

            String texto;

            // Leer del teclado y enviar
            while (true) {

                System.out.print("Escribe: ");
                texto = teclado.readLine();

                salida.println(texto);

                // Leer respuesta servidor
                String respuesta = entrada.readLine();
                System.out.println("Servidor: " + respuesta);

                if (texto.equalsIgnoreCase("fin")) {
                    break;
                }
            }
        }
    }
}

