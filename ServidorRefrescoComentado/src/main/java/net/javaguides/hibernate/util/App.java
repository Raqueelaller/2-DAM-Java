package net.javaguides.hibernate.util;

// === IMPORTS de IO y red (comunicaciones) ===
import java.io.BufferedReader;             // Para leer texto línea a línea desde el socket
import java.io.IOException;                // Excepciones de entrada/salida
import java.io.InputStream;                // Flujo de entrada (bytes) desde el socket
import java.io.InputStreamReader;          // Convierte bytes -> caracteres (con charset)
import java.io.PrintWriter;                // Para enviar texto al cliente por el socket
import java.net.InetSocketAddress;         // Para bind con IP/puerto
import java.net.ServerSocket;              // Socket servidor (escucha conexiones)
import java.net.Socket;                    // Socket cliente (conexión individual)
import java.nio.charset.StandardCharsets;  // UTF-8 seguro
import java.util.List;                     // Listas de resultados Hibernate

// === IMPORTS de Hibernate (BBDD) ===
import org.hibernate.Session;              // Sesión Hibernate (unidad de trabajo)
import org.hibernate.Transaction;          // Transacciones (commit/rollback)
import org.hibernate.query.Query;          // Consultas HQL (aquí no se usa realmente)

// === ENTIDADES (tablas) ===
import net.javaguides.hibernate.entity.Pedidos;
import net.javaguides.hibernate.entity.Refrescos;

public class App {

    // Puerto TCP en el que escuchará el servidor
    private static final int PUERTO = 5009;

    // Variable no usada aquí (podría servir para rotar elementos, etc.)
    private static int indiceActual = 0;

    public static void main(String[] args) {

        // try-with-resources: el ServerSocket se cerrará automáticamente al salir del try
        // Importante en servidores para liberar el puerto correctamente
        try (ServerSocket serverSocket = new ServerSocket()) {

            // Permite reutilizar el puerto rápidamente si reinicias el servidor
            // (evita "Address already in use" en algunos casos)
            serverSocket.setReuseAddress(true);

            // Asocia el servidor a PUERTO (bind)
            serverSocket.bind(new InetSocketAddress(PUERTO));

            System.out.println(" Servidor en puerto: " + PUERTO);
            System.out.println(" MODO: Cada cliente en HILO SEPARADO");
            System.out.println(" Esperando clientes...\n");

            int contadorClientes = 0;

            // Bucle infinito: servidor siempre aceptando nuevas conexiones
            while (true) {

                // accept() bloquea hasta que entra un cliente
                Socket socketCliente = serverSocket.accept();
                contadorClientes++;

                // Obtener IP del cliente (útil para log)
                String ipCliente = socketCliente.getInetAddress().getHostAddress();
                System.out.println(" CLIENTE #" + contadorClientes + " CONECTADO desde " + ipCliente);

                // Cada cliente se atiende en un hilo separado -> servidor concurrente
                Thread hiloCliente = new Thread(new ManejadorCliente(socketCliente, contadorClientes));
                hiloCliente.setName("Cliente-" + contadorClientes);
                hiloCliente.start();

                System.out.println("    Hilo creado: " + hiloCliente.getName());
                System.out.println("    Hilos activos: " + Thread.activeCount());
                System.out.println("     Volviendo a esperar más clientes...\n");
            }

        } catch (IOException e) {
            // Si falla el bind o el accept, se captura aquí
            System.err.println("Error: " + e.getMessage());
        }
    }

    // Clase interna que maneja la conversación con 1 cliente (Runnable para Thread)
    static class ManejadorCliente implements Runnable {

        private Socket socketCliente; // Socket de ese cliente
        private int idCliente;        // ID lógico del cliente (contador)

        public ManejadorCliente(Socket socket, int id) {
            this.socketCliente = socket;
            this.idCliente = id;
        }

        @Override
        public void run() {
            System.out.println("[HILO " + idCliente + "] INICIADO");

            // try-with-resources: cerrará inputStream, salida y reader automáticamente
            // cuando termine el hilo o haya excepción.
            try (InputStream inputStream = socketCliente.getInputStream();

                 // PrintWriter con autoFlush=true: cada println hace flush automático
                 PrintWriter salida = new PrintWriter(socketCliente.getOutputStream(), true);

                 // Reader para leer texto (UTF-8) línea a línea (protocolo basado en líneas)
                 BufferedReader reader = new BufferedReader(
                     new InputStreamReader(inputStream, StandardCharsets.UTF_8)
                 )
            ) {

                boolean bandera = true;

                // Creas UNA Session para todo el ciclo del cliente (idea correcta)
                // PERO OJO: luego la cierras en métodos auxiliares (bug).
                Session session = HibernateUtil.getSessionFactory().openSession();

                String mensajeCliente;

                // Enviar lista inicial al conectarse
                listarRefrescos(salida, session);

                // Menú/protocolo: instrucciones al cliente
                salida.println("Si quieres pedir, el formato es: Pide (el refresco que quieras) (el nombre del cliente)");
                salida.println("Si quieres recargar, el formato es: Regcarga (id del refresco) (cantidad) (codigo_admin)");
                salida.println("Si quieres terminar, pon 'fin'");

                // Bucle principal: lee líneas del cliente hasta que ponga fin o se desconecte
                while (bandera && (mensajeCliente = reader.readLine()) != null) {

                    mensajeCliente = mensajeCliente.trim();

                    // Si escribe "fin" o línea vacía -> terminar sesión
                    if (mensajeCliente.equalsIgnoreCase("fin") || mensajeCliente.equalsIgnoreCase("")) {
                        bandera = false;

                    } else {
                        // Procesar comando: pide / recarga
                        actualizarMaquina(mensajeCliente, salida, session);

                        // Volver a mostrar refrescos e instrucciones
                        listarRefrescos(salida, session);

                        salida.println("Si quieres pedir, el formato es: Pide (el refresco que quieras) (el nombre del cliente)");
                        salida.println("Si quieres recargar, el formato es: Regcarga (id del refresco) (cantidad) (codigo_admin)");
                        salida.println("Si quieres terminar, pon 'fin'");
                    }
                }

                // Cerrar socket si sigue abierto (aunque try-with-resources cerrará streams)
                if (!socketCliente.isClosed()) {
                    socketCliente.close();
                }

                // Recomendación: aquí sería buen sitio para cerrar session
                // session.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Procesa la orden recibida por el cliente y actualiza la BBDD (Hibernate)
    private static void actualizarMaquina(String respuesta, PrintWriter salida, Session session) {

        Transaction transaction = null;

        // Normalizar entrada (evitar problemas con mayúsculas / espacios)
        respuesta = respuesta.trim().toLowerCase();

        // “Clave admin” hardcodeada (en examen: mala práctica, pero sirve)
        final String codigo_admin = "123";

        try {
            // Cargar lista de refrescos (consulta HQL)
            // "FROM Refrescos" = select * de la entidad Refrescos
            List<Refrescos> refrescos =
                session.createQuery("FROM Refrescos ORDER BY id", Refrescos.class).list();

            // Separar comando por espacios:
            // ej: "pide cocacola raquel" => ["pide", "cocacola", "raquel"]
            String[] respuestita = respuesta.split(" ");

            if (respuesta.isEmpty()) {
                salida.println("no has enviado nada");

            } else if (respuestita[0].equalsIgnoreCase("pide")) {

                boolean bandera = false;

                // respuestita[1] = nombre refresco, respuestita[2] = nombre cliente
                String refresco1 = respuestita[1].toLowerCase();
                String nombreCliente = respuestita[2];

                // Buscar el refresco por nombre
                for (Refrescos refresco : refrescos) {
                    if (refresco.getNombre().equalsIgnoreCase(refresco1)) {

                        // Abrir transacción (IMPORTANTE para modificar/persistir)
                        transaction = session.beginTransaction();
                        bandera = true;

                        // Disminuir existencia en 1 (venta/pedido)
                        refresco.setExistencia(refresco.getExistencia() - 1);

                        // merge: sincroniza el objeto con el contexto de Hibernate
                        // (si el objeto estuviera detached)
                        Refrescos refrescoActualizado = (Refrescos) session.merge(refresco);

                        // Crear un pedido y persistirlo
                        Pedidos pedido = new Pedidos(0, nombreCliente, refresco);
                        session.persist(pedido);

                        // Confirmar cambios
                        transaction.commit();
                    }
                }

                // Si no encontró el refresco por nombre
                if (bandera == false) {
                    salida.println("No existe ese refresco");
                }

            } else if (respuestita[0].equalsIgnoreCase("recarga")) {

                // respuestita[3] debe ser el admin code (según tu formato)
                if (respuestita[3].equalsIgnoreCase(codigo_admin)) {

                    int idRefresco = Integer.parseInt(respuestita[1]);
                    int cantidad = Integer.parseInt(respuestita[2]);
                    boolean bandera1 = false;

                    // Validación: no permitir recargar negativo
                    if (cantidad < 0) {
                        salida.println("no se puede recargar esa cantidad");
                        cantidad = 0;
                    }

                    // Buscar por ID
                    for (Refrescos refresco : refrescos) {
                        if (refresco.getId() == idRefresco) {

                            transaction = session.beginTransaction();
                            bandera1 = true;

                            // Aumentar stock
                            int nuevaExistencia = refresco.getExistencia() + cantidad;
                            refresco.setExistencia(nuevaExistencia);

                            // Guardar cambios
                            session.merge(refresco);

                            transaction.commit();
                        }
                    }

                    if (bandera1 == false) {
                        salida.println("No se encuentra ese refresco en la máquina");
                    }

                } else {
                    salida.println("Código de admin equivocado");
                }
            }

        } finally {
            // ⚠️ PROBLEMA:
            // Estás cerrando la Session aquí, PERO esa session se creó en run()
            // para reutilizarla durante toda la conversación con el cliente.
            // Esto hace que la siguiente operación falle ("Session is closed").
            //
            // LO CORRECTO: NO cerrar aquí. Cerrar en el run() al terminar el cliente.
            session.close();
        }
    }

    // Envía al cliente la lista de refrescos (estado actual de máquina)
    public static void listarRefrescos(PrintWriter salida, Session session) {

        Transaction transaction = null;

        try {
            // session.clear(): limpia el primer nivel de caché de Hibernate
            // (en teoría para forzar que vuelva a leer de BD)
            session.clear();

            // Leer refrescos ordenados por id
            List<Refrescos> refrescos =
                session.createQuery("FROM Refrescos ORDER BY id", Refrescos.class).list();

            System.out.println(" ");

            // Enviar cada refresco por el socket al cliente
            for (Refrescos refresco : refrescos) {
                salida.println(refresco.toString());
            }

        } finally {
            // ⚠️ PROBLEMA IGUAL QUE ANTES:
            // Aquí también cierras la session, lo que rompe el flujo en el hilo.
            // Deberías cerrar la session una vez, al final del run().
            session.close();
        }
    }
}
