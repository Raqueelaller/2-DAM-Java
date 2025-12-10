package ejercicio;

import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.*;

public class Server {
    private static final int PUERTO = 5004;
    private static List<String> frases;
    private static int indiceActual = 0;
    
    public static void main(String[] args) {
        cargarFrasesDesdeArchivo("frases.txt");
        
        if (frases.isEmpty()) {
            System.out.println("ERROR: No hay frases");
            return;
        }
        
        System.out.println( frases.size() + " frases listas");
        
        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.setReuseAddress(true);
            serverSocket.bind(new InetSocketAddress(PUERTO));
            
            System.out.println(" Servidor en puerto: " + PUERTO);
            System.out.println(" MODO: Cada cliente en HILO SEPARADO");
            System.out.println(" Esperando clientes...\n");
            
            int contadorClientes = 0;
            
            while (true) {
                Socket socketCliente = serverSocket.accept();
                contadorClientes++;
                
                String ipCliente = socketCliente.getInetAddress().getHostAddress();
                System.out.println(" CLIENTE #" + contadorClientes + " CONECTADO desde " + ipCliente);
                
                Thread hiloCliente = new Thread(new ManejadorCliente(socketCliente, contadorClientes));
                hiloCliente.setName("Cliente-" + contadorClientes);
                hiloCliente.start();
                
                System.out.println("    Hilo creado: " + hiloCliente.getName());
                System.out.println("    Hilos activos: " + Thread.activeCount());
                System.out.println("     Volviendo a esperar más clientes...\n");
            }
            
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    static class ManejadorCliente implements Runnable {
        private Socket socketCliente;
        private int idCliente;
        
        public ManejadorCliente(Socket socket, int id) {
            this.socketCliente = socket;
            this.idCliente = id;
        }
        
        @Override
        public void run() {
            System.out.println("[HILO " + idCliente + "] INICIADO");
            
            try (PrintWriter salida = new PrintWriter(
                    socketCliente.getOutputStream(), true)) {
                
                String frase = obtenerSiguienteFrase();
                System.out.println("[HILO " + idCliente + "] Enviando: \"" + frase + "\"");
                salida.println(frase);
                Thread.sleep(2000);
                System.out.println("[HILO " + idCliente + "]  Frase enviada");
                System.out.println("[HILO " + idCliente + "]  Terminando hilo\n");
                
            } catch (IOException | InterruptedException e) {
                System.err.println("[HILO " + idCliente + "] Error: " + e.getMessage());
                
            } finally {
                try {
                    if (socketCliente != null && !socketCliente.isClosed()) {
                        socketCliente.close();
                    }
                } catch (IOException e) {
                }
            }
        }
    }
    
    private static void cargarFrasesDesdeArchivo(String nombreArchivo) {
        frases = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            frases = new ArrayList<>();
            
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    frases.add(linea);
                }
            }
            
            if (!frases.isEmpty()) {
                System.out.println(" Frases cargadas desde: " + nombreArchivo);
            } else {
                frases = Arrays.asList("Frase 1", "Frase 2", "Frase 3");
            }
            
        } catch (FileNotFoundException e) {
            // Si el archivo no existe, usar frases por defecto
            frases = Arrays.asList("Frase 1", "Frase 2", "Frase 3");
        } catch (IOException e) {
            frases = Arrays.asList("Error cargando frases");
        }
    }
    
    private static synchronized String obtenerSiguienteFrase() {
        if (frases.isEmpty()) return "Sin frases";
        String frase = frases.get(indiceActual);
        indiceActual = (indiceActual + 1) % frases.size();
        return frase;
    }
}