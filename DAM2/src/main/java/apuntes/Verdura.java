package apuntes;

import java.io.*;      // Para leer ficheros
import java.sql.*;     // Para conectar con SQLite
import java.util.*;    // Para usar List y ArrayList

public class Verdura {

    // ================================================================
    // 🔹 ATRIBUTOS DE LA CLASE
    // ================================================================
    private String nombre;
    private double precio;

    // ================================================================
    // 🔹 CONSTRUCTOR
    // ================================================================
    public Verdura(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    // ================================================================
    // 🔹 GETTERS Y SETTERS
    // ================================================================
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    // ================================================================
    // 🔹 CREAR TABLA SI NO EXISTE
    // ================================================================
    public static void crearTabla() {
        // SQL para crear tabla con dos columnas: nombre y precio
        String sql = """
            CREATE TABLE IF NOT EXISTS verdura (
                nombre TEXT PRIMARY KEY,
                precio REAL
            );
            """;

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:verduras.db");
             Statement stmt = conn.createStatement()) {

            // Ejecutamos la sentencia
            stmt.execute(sql);
            System.out.println("✅ Tabla 'verdura' creada o ya existente.");

        } catch (SQLException e) {
            System.out.println("❌ Error creando tabla: " + e.getMessage());
        }
    }

    // ================================================================
    // 🔹 LEER UN FICHERO DE TEXTO (nombre:precio)
    // ================================================================
    public static List<Verdura> leerFichero(String ruta) {
        List<Verdura> lista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;

            // Leer línea a línea
            while ((linea = br.readLine()) != null) {
                // Dividimos por el carácter ':'
                String[] partes = linea.split(":");

                // Solo procesamos si tiene dos partes
                if (partes.length == 2) {
                    String nombre = partes[0].trim();
                    double precio = Double.parseDouble(partes[1].trim());
                    lista.add(new Verdura(nombre, precio));
                }
            }
            System.out.println("✅ Fichero leído correctamente. " + lista.size() + " verduras encontradas.");

        } catch (IOException e) {
            System.out.println("❌ Error leyendo fichero: " + e.getMessage());
        }

        return lista;
    }

    // ================================================================
    // 🔹 INSERTAR UNA VERDURA EN LA BASE DE DATOS
    // ================================================================
    public static void insertarVerdura(Verdura v) {
        String sql = "INSERT OR REPLACE INTO verdura (nombre, precio) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:verduras.db");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Asignamos los valores de la verdura
            stmt.setString(1, v.getNombre());
            stmt.setDouble(2, v.getPrecio());

            int filas = stmt.executeUpdate();
            System.out.println("🥦 Verdura '" + v.getNombre() + "' insertada (" + filas + " fila/s).");

        } catch (SQLException e) {
            System.out.println("❌ Error al insertar verdura: " + e.getMessage());
        }
    }

    // ================================================================
    // 🔹 ACTUALIZAR PRECIO DE UNA VERDURA
    // ================================================================
    public static void actualizarPrecio(String nombre, double nuevoPrecio) {
        String sql = "UPDATE verdura SET precio = ? WHERE nombre = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:verduras.db");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Asignamos parámetros
            stmt.setDouble(1, nuevoPrecio);
            stmt.setString(2, nombre);

            int filas = stmt.executeUpdate();
            if (filas > 0)
                System.out.println("💰 Precio de " + nombre + " actualizado a " + nuevoPrecio + " €");
            else
                System.out.println("⚠️ No existe esa verdura en la base de datos.");

        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar precio: " + e.getMessage());
        }
    }

    // ================================================================
    // 🔹 MOSTRAR TODAS LAS VERDURAS (SELECT *)
    // ================================================================
    public static void mostrarVerduras() {
        String sql = "SELECT * FROM verdura";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:verduras.db");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n📋 LISTA DE VERDURAS EN BASE DE DATOS:");
            while (rs.next()) {
                System.out.printf(" - %s → %.2f €%n", rs.getString("nombre"), rs.getDouble("precio"));
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al mostrar verduras: " + e.getMessage());
        }
    }

    // ================================================================
    // 🔹 MOSTRAR VERDURAS MÁS BARATAS QUE UN PRECIO DADO
    // ================================================================
    public static void mostrarVerdurasBaratas(double precioMaximo) {
        // Usamos un WHERE en la consulta
        String sql = "SELECT * FROM verdura WHERE precio < ? ORDER BY precio ASC";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:verduras.db");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, precioMaximo);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n🥕 VERDURAS CON PRECIO MENOR A " + precioMaximo + " €:");
            boolean hayResultados = false;

            while (rs.next()) {
                System.out.printf(" - %s → %.2f €%n", rs.getString("nombre"), rs.getDouble("precio"));
                hayResultados = true;
            }

            if (!hayResultados) {
                System.out.println("⚠️ No hay verduras por debajo de ese precio.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al filtrar verduras: " + e.getMessage());
        }
    }

    // ================================================================
    // 🔹 MAIN DE PRUEBA (SIMULA EL EXAMEN)
    // ================================================================
    public static void main(String[] args) {
        // 1️⃣ Crear la tabla si no existe
        crearTabla();

        // 2️⃣ Leer el fichero de texto "verduras.txt"
        List<Verdura> lista = leerFichero("verduras.txt");

        // 3️⃣ Insertar todas las verduras en la BD
        for (Verdura v : lista) {
            insertarVerdura(v);
        }

        // 4️⃣ Mostrar todas las verduras
        mostrarVerduras();

        // 5️⃣ Actualizar el precio de una verdura concreta
        actualizarPrecio("tomate", 2.3);

        // 6️⃣ Mostrar solo las verduras que valen menos de 2 €
        mostrarVerdurasBaratas(2.0);

        // 7️⃣ Mostrar todo otra vez para comprobar el cambio
        mostrarVerduras();
    }
}
