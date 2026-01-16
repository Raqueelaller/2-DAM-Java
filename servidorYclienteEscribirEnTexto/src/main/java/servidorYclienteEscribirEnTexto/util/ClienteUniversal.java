package servidorYclienteEscribirEnTexto.util;


//IMPORTS DE RED
import java.net.Socket;

//IMPORTS DE ENTRADA/SALIDA
import java.io.*;

//IMPORTS PARA UTF-8
import java.nio.charset.StandardCharsets;

/*
CLIENTE UNIVERSAL (EXAMEN):

MODO 1: ENVIAR TEXTO (para ServidorGuardarTxt)
- Se conecta, escribe líneas desde teclado y las manda al servidor
- Termina cuando escribes "fin"

MODO 2: RECIBIR FRASE (para ServidorFrases)
- Se conecta, lee 1 línea (frase) y termina

USO:
1) Enviar texto:
 java ClienteUniversal send 127.0.0.1 6000

2) Recibir una frase:
 java ClienteUniversal get 127.0.0.1 6001
*/

public class ClienteUniversal {

 public static void main(String[] args) {

     // Comprobación de argumentos (típico en examen)
     if (args.length < 4) {
         System.out.println("USO:");
         System.out.println("  send <host> <puerto>   -> enviar texto (fin para terminar)");
         System.out.println("  get  <host> <puerto>   -> recibir una frase y salir");
         System.out.println("");
         System.out.println("Ejemplos:");
         System.out.println("  java ClienteUniversal send 127.0.0.1 6000");
         System.out.println("  java ClienteUniversal get  127.0.0.1 6001");
         return;
     }

     // Modo: "send" o "get"
     String modo = args[0].toLowerCase();

     // Host y puerto
     String host = args[1];
     int puerto = Integer.parseInt(args[2]);

     // NOTA: args[3] existe por el check, pero en realidad con 3 sería suficiente.
     // Si quieres, puedes borrar el check y dejarlo en args.length < 3.
     // Para no liarte ahora, lo dejamos así y usamos args[2] como puerto.

     // Según el modo, ejecutamos una función u otra
     if (modo.equals("send")) {
         enviarTexto(host, puerto);
     } else if (modo.equals("get")) {
         recibirFrase(host, puerto);
     } else {
         System.out.println("Modo no reconocido: " + modo);
         System.out.println("Usa: send o get");
     }
 }

 // =========================================
 // MODO SEND: Enviar texto (hasta "fin")
 // =========================================
 private static void enviarTexto(String host, int puerto) {

     // Socket: crea conexión TCP con el servidor
     try (Socket socket = new Socket(host, puerto);

          // Para leer del teclado (lo que escribe el usuario)
          BufferedReader teclado = new BufferedReader(
              new InputStreamReader(System.in, StandardCharsets.UTF_8)
          );

          // Para leer respuestas del servidor
          BufferedReader in = new BufferedReader(
              new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)
          );

          // Para enviar texto al servidor (por líneas)
          PrintWriter out = new PrintWriter(
              new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8),
              true // autoFlush
          )
     ) {
         // Leer mensaje inicial del servidor (si lo envía)
         String saludo = in.readLine();
         if (saludo != null) {
             System.out.println("Servidor: " + saludo);
         }

         // Bucle: enviar líneas hasta "fin"
         while (true) {
             System.out.print("Escribe (fin para terminar): ");
             String linea = teclado.readLine();

             // Si el usuario cierra input (Ctrl+Z en Windows) -> salir
             if (linea == null) break;

             // Enviar línea al servidor
             out.println(linea);

             // Leer confirmación del servidor (si existe)
             String respuesta = in.readLine();
             if (respuesta != null) {
                 System.out.println("Servidor: " + respuesta);
             }

             // Terminar si escribimos fin
             if (linea.equalsIgnoreCase("fin")) {
                 break;
             }
         }

     } catch (IOException e) {
         System.err.println("Error cliente SEND: " + e.getMessage());
     }
 }

 // =========================================
 // MODO GET: Recibir una frase (1 línea)
 // =========================================
 private static void recibirFrase(String host, int puerto) {

     try (Socket socket = new Socket(host, puerto);

          BufferedReader in = new BufferedReader(
              new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)
          )
     ) {
         // El servidor de frases manda 1 línea y cierra
         String frase = in.readLine();
         System.out.println("Frase recibida: " + frase);

     } catch (IOException e) {
         System.err.println("Error cliente GET: " + e.getMessage());
     }
 }
}
