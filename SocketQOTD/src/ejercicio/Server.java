// Declaración del paquete - organiza las clases relacionadas
package ejercicio;

// IMPORTACIONES - Todas las bibliotecas necesarias
import java.net.*;          // ServerSocket, Socket, InetSocketAddress para redes
import java.nio.file.Files; // Para operaciones avanzadas con archivos
import java.nio.file.Path;  // Representación moderna de rutas de archivos
import java.nio.file.Paths; // Conversión de strings a Path
import java.util.ArrayList; // Listas dinámicas para almacenar frases
import java.util.Arrays;    // Métodos de utilidad para arrays
import java.util.List;      // Interfaz List para declarar el tipo de lista
import java.io.*;           // Para entrada/salida: PrintWriter, IOException, etc.

// Clase principal del servidor que soporta múltiples hilos
public class Server {
    // CONSTANTE: Puerto fijo donde el servidor escuchará
    // El puerto 5000 es común para aplicaciones de desarrollo
    private static final int PUERTO = 5004;
    
    // VARIABLE COMPARTIDA: Lista de frases leídas del archivo
    // 'static' porque todas las instancias de ManejadorCliente comparten esta lista
    private static List<String> frases;
    
    // VARIABLE COMPARTIDA: Índice para controlar qué frase enviar siguiente
    // 'static' porque debe mantenerse coherente entre todos los hilos/clientes
    private static int indiceActual = 0;
    
    // MÉTODO PRINCIPAL - Punto de entrada de la aplicación
    public static void main(String[] args) {
        // Mensajes de inicio para identificar el modo del servidor
        
        // 1. CARGAR FRASES desde el archivo "frases.txt"
        cargarFrasesDesdeArchivo("frases.txt");
        
        // 2. VERIFICAR que se cargaron frases
        if (frases.isEmpty()) {
            System.out.println("ERROR: No hay frases");
            return; // Termina el programa si no hay frases para enviar
        }
        
        // 3. MOSTRAR INFORMACIÓN DE INICIO
        System.out.println( frases.size() + " frases listas");
        
        // 4. CREAR EL SERVER SOCKET PRINCIPAL
        // try-with-resources asegura que el ServerSocket se cierre automáticamente
        try (ServerSocket serverSocket = new ServerSocket()) {
            // CONFIGURACIÓN IMPORTANTE: Permitir reusar el puerto inmediatamente
            // Evita el error "Address already in use" al reiniciar rápido
            serverSocket.setReuseAddress(true);
            
            // VINCULAR el ServerSocket al puerto específico
            // InetSocketAddress crea una dirección con el puerto 5000
            serverSocket.bind(new InetSocketAddress(PUERTO));
            
            // 5. MOSTRAR QUE EL SERVIDOR ESTÁ LISTO
            System.out.println(" Servidor en puerto: " + PUERTO);
            System.out.println(" MODO: Cada cliente en HILO SEPARADO");
            System.out.println(" Esperando clientes...\n");
            
            // 6. CONTADOR para identificar clientes secuencialmente
            // Se incrementa con cada nueva conexión
            int contadorClientes = 0;
            
            // 7. BUCLE INFINITO PRINCIPAL - Acepta conexiones continuamente
            while (true) {
                // 7.1. ESPERAR UNA NUEVA CONEXIÓN
                // accept() es BLOQUEANTE: el programa se pausa aquí hasta que llegue un cliente
                // Cuando un cliente se conecta, retorna un nuevo objeto Socket
                Socket socketCliente = serverSocket.accept();
                
                // 7.2. INCREMENTAR CONTADOR de clientes
                contadorClientes++;
                
                // 7.3. OBTENER INFORMACIÓN DEL CLIENTE
                // getInetAddress() obtiene la dirección IP del cliente
                String ipCliente = socketCliente.getInetAddress().getHostAddress();
                System.out.println(" CLIENTE #" + contadorClientes + " CONECTADO desde " + ipCliente);
                
                // 8. ¡PUNTO CLAVE! Crear HILO para manejar este cliente
                // ManejadorCliente es una clase interna que implementa Runnable
                // Se le pasa el socket del cliente y su ID único
                Thread hiloCliente = new Thread(new ManejadorCliente(socketCliente, contadorClientes));
                
                // 9. Configurar nombre del hilo (opcional, útil para debugging)
                hiloCliente.setName("Cliente-" + contadorClientes);
                
                // 10. INICIAR EL HILO - ¡ESTA ES LA CLAVE DE LA CONCURRENCIA!
                // start() inicia la ejecución del método run() en un NUEVO hilo
                // NO se usa run() porque ejecutaría en el hilo actual (sin concurrencia)
                hiloCliente.start();
                
                // 11. CONTINUAR INMEDIATAMENTE - El hilo principal NO espera
                // Esto permite que el servidor vuelva a accept() para atender más clientes
                // Mientras el hilo recién creado procesa al cliente actual
                System.out.println("    Hilo creado: " + hiloCliente.getName());
                System.out.println("    Hilos activos: " + Thread.activeCount());
                System.out.println("     Volviendo a esperar más clientes...\n");
                
                // 12. EL BUCLE VUELVE A accept() para esperar el siguiente cliente
                // Esto sucede INMEDIATAMENTE, sin esperar a que termine el hilo creado
            }
            
        } catch (IOException e) {
            // 13. MANEJO DE ERRORES del servidor principal
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    // ============================================================
    // CLASE INTERNA: MANEJADOR DE CLIENTE (Implementa Runnable)
    // ============================================================
    // Cada instancia de esta clase maneja UN cliente específico
    // Se ejecuta en un HILO SEPARADO creado por el método main
    static class ManejadorCliente implements Runnable {
        // Socket específico para ESTE cliente
        // No es static: cada instancia tiene su propio socket
        private Socket socketCliente;
        
        // ID único para identificar ESTE cliente
        // No es static: cada instancia tiene su propio ID
        private int idCliente;
        
        // CONSTRUCTOR: recibe el socket y el ID asignado
        // Se llama desde main() cuando se crea el hilo
        public ManejadorCliente(Socket socket, int id) {
            this.socketCliente = socket;  // Guarda referencia al socket
            this.idCliente = id;          // Guarda el ID del cliente
        }
        
        // MÉTODO run(): ¡SE EJECUTA EN UN HILO SEPARADO!
        // Este es el código que ejecuta CADA hilo cliente
        @Override
        public void run() {
            // 1. Mostrar que el hilo se ha iniciado
            System.out.println("[HILO " + idCliente + "] INICIADO");
            
            // 2. try-with-resources para el PrintWriter
            // Se cierra automáticamente al final del bloque try
            try (PrintWriter salida = new PrintWriter(
                    socketCliente.getOutputStream(), true)) {
                
                // 3. OBTENER UNA FRASE para este cliente
                // Llama al método sincronizado que garantiza orden secuencial
                String frase = obtenerSiguienteFrase();
                
                // 4. Mostrar qué frase se enviará (solo para logging)
                System.out.println("[HILO " + idCliente + "] Enviando: \"" + frase + "\"");
                
                // 5. ENVIAR LA FRASE al cliente
                // println() envía la frase seguida de salto de línea
                salida.println(frase);
                
                // 6. PEQUEÑA PAUSA para simular procesamiento
                // Thread.sleep() hace que el hilo espere 2 segundos
                // Mientras este hilo duerme, OTROS HILOS pueden ejecutarse
                Thread.sleep(2000); // 2000 milisegundos = 2 segundos
                
                // 7. Mostrar confirmación de envío
                System.out.println("[HILO " + idCliente + "]  Frase enviada");
                System.out.println("[HILO " + idCliente + "]  Terminando hilo\n");
                
            } catch (IOException | InterruptedException e) {
                // 8. MANEJO DE ERRORES específicos de este hilo
                // IOException: error de red o de socket
                // InterruptedException: si el hilo es interrumpido durante sleep()
                System.err.println("[HILO " + idCliente + "] Error: " + e.getMessage());
                
            } finally {
                // 9. BLOQUE FINALLY: SIEMPRE se ejecuta, haya error o no
                try {
                    // 10. CERRAR EL SOCKET del cliente
                    // Importante para liberar recursos del sistema
                    if (socketCliente != null && !socketCliente.isClosed()) {
                        socketCliente.close();
                        // Nota: No se imprime mensaje aquí para no saturar la salida
                    }
                } catch (IOException e) {
                    // 11. Error al cerrar el socket (poco común, pero posible)
                    // Se ignora porque ya estamos en manejo de errores
                }
            }
            // 12. FIN DEL MÉTODO run() - El hilo termina aquí
            // Los recursos del hilo son liberados por el sistema
        }
    }
    
    // ============================================================
    // MÉTODOS AUXILIARES (compartidos por todos los hilos)
    // ============================================================
    
    // MÉTODO: Cargar frases desde archivo
    private static void cargarFrasesDesdeArchivo(String nombreArchivo) {
        frases = new ArrayList<>();
        try {
            // Convertir nombre de archivo a Path
            Path archivoPath = Paths.get(nombreArchivo);
            
            // Verificar si el archivo existe
            if (Files.exists(archivoPath)) {
                // Leer todas las líneas del archivo
                frases = Files.readAllLines(archivoPath);
                
                // Eliminar líneas vacías o con solo espacios
                frases.removeIf(linea -> linea.trim().isEmpty());
                
                System.out.println(" Frases cargadas desde: " + nombreArchivo);
            } else {
                // Si el archivo no existe, usar frases por defecto
                frases = Arrays.asList("Frase 1", "Frase 2", "Frase 3");
            }
        } catch (IOException e) {
            // Error al leer el archivo
            frases = Arrays.asList("Error cargando frases");
        }
    }
    
    // MÉTODO SINCRONIZADO: Obtener siguiente frase
    // 'synchronized' es CRÍTICO aquí porque múltiples hilos lo llaman
    private static synchronized String obtenerSiguienteFrase() {
        // Verificar que hay frases disponibles
        if (frases.isEmpty()) return "Sin frases";
        
        // Obtener frase en la posición actual
        String frase = frases.get(indiceActual);
        
        // Avanzar índice de forma circular
        // Ejemplo: 5 frases (0-4), índice 4 → (4+1)%5 = 0
        indiceActual = (indiceActual + 1) % frases.size();
        
        return frase;
        // El 'synchronized' garantiza que solo UN hilo ejecute esto a la vez
        // Evita que dos hilos obtengan la misma frase
    }
}