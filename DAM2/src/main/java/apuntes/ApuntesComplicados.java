package apuntes;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/*
=====================================================
📘 CHULETA COMPLETA — SQLITE + JAVA (EXAMEN)
=====================================================
Autor: Tú 😎
Objetivo: tener en una sola clase todas las operaciones
más comunes que pueden caer en un examen de acceso a datos:
 - conexión con SQLite
 - insert/update/delete/select
 - validaciones (dni, teléfono)
 - control de errores
 - inserciones seguras (sin duplicados)
 - uso de fechas
=====================================================
*/

public class ApuntesComplicados {

    // =====================================================
    // 🔹 1️⃣ INSERTAR UNA MÁQUINA SOLO SI NO EXISTE
    // =====================================================
    public static void insertarMaquinaSiNoExiste(String codigo_maquina, String descripcion, int unidades) {
        // 1️⃣ Comprobamos si ya existe ese código en la tabla
        String comprobar = "SELECT COUNT(*) FROM maquina WHERE codigo_maquina = ?";
        // 2️⃣ Si no existe, la insertamos
        String insertar = "INSERT INTO maquina (codigo_maquina, descripcion, unidades) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:maquinaria.db")) {
            PreparedStatement check = conn.prepareStatement(comprobar);
            check.setString(1, codigo_maquina);
            ResultSet rs = check.executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) { // No existe → insertamos
                PreparedStatement stmt = conn.prepareStatement(insertar);
                stmt.setString(1, codigo_maquina);
                stmt.setString(2, descripcion);
                stmt.setInt(3, unidades);
                stmt.executeUpdate();
                System.out.println("✅ Máquina insertada correctamente");
            } else {
                // Ya existía → no insertamos
                System.out.println("⚠️ La máquina ya existe, no se insertó.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    // =====================================================
    // 🔹 2️⃣ INSERTAR CLIENTE CON ID AUTOINCREMENTAL
    //     CÓDIGO = primera letra del nombre + del apellido + id
    // =====================================================
    public static void insertarCliente(String nombre, String apellido) {
        String sql = "INSERT INTO clientes (nombre, apellido) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:maquinaria.db");
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Insertamos nombre y apellido
            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.executeUpdate();

            // Obtenemos el id autoincremental generado automáticamente
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                // Creamos el código a partir de las iniciales + id (ej: JM3)
                String codigo = nombre.substring(0, 1).toUpperCase() + apellido.substring(0, 1).toUpperCase() + id;

                // Actualizamos la tabla para añadir el código al cliente
                PreparedStatement update = conn.prepareStatement("UPDATE clientes SET codigo = ? WHERE id = ?");
                update.setString(1, codigo);
                update.setInt(2, id);
                update.executeUpdate();
                System.out.println("✅ Cliente creado con código: " + codigo);
            }
        } catch (Exception e) {
            System.out.println("❌ Error al insertar cliente: " + e.getMessage());
        }
    }

    // =====================================================
    // 🔹 3️⃣ INSERTAR SOLO SI NO EXISTE (FORMA SQL ELEGANTE)
    // =====================================================
    public static void insertarSiNoExiste(String codigo_maquina, String descripcion, int unidades) {
        // Usamos "INSERT ... SELECT ... WHERE NOT EXISTS"
        String sql = "INSERT INTO maquina (codigo_maquina, descripcion, unidades) " +
                     "SELECT ?, ?, ? WHERE NOT EXISTS " +
                     "(SELECT 1 FROM maquina WHERE codigo_maquina = ?)";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:maquinaria.db");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Asignamos valores a los parámetros (los ?)
            stmt.setString(1, codigo_maquina);
            stmt.setString(2, descripcion);
            stmt.setInt(3, unidades);
            stmt.setString(4, codigo_maquina);

            int filas = stmt.executeUpdate(); // Ejecutamos el INSERT
            if (filas > 0)
                System.out.println("✅ Máquina insertada (no existía antes).");
            else
                System.out.println("⚠️ Ya existía esa máquina, no se insertó.");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    // =====================================================
    // 🔹 4️⃣ INSERTAR O ACTUALIZAR FECHAS EN SQLITE
    // =====================================================
    public static void insertarFecha(String codigo_maquina, LocalDate fecha) {
        // En SQLite las fechas se guardan como texto "YYYY-MM-DD"
        String sql = "UPDATE maquina SET fecha_registro = ? WHERE codigo_maquina = ?";
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:maquinaria.db");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Convertimos la fecha al formato correcto
            stmt.setString(1, fecha.format(formato));
            stmt.setString(2, codigo_maquina);
            stmt.executeUpdate();
            System.out.println("✅ Fecha actualizada correctamente");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    // =====================================================
    // 🔹 5️⃣ VALIDAR DNI (8 dígitos + letra correcta)
    // =====================================================
    public static boolean validarDNI(String dni) {
        // 1️⃣ Validar formato con expresión regular
        if (!dni.matches("\\d{8}[A-Za-z]")) {
            System.out.println("⚠️ DNI con formato inválido");
            return false;
        }

        // 2️⃣ Calcular la letra correcta
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int numero = Integer.parseInt(dni.substring(0, 8));
        char letraCorrecta = letras.charAt(numero % 23);

        // 3️⃣ Comparar la letra introducida con la correcta
        boolean valido = Character.toUpperCase(dni.charAt(8)) == letraCorrecta;
        System.out.println(valido ? "✅ DNI válido" : "❌ DNI incorrecto");
        return valido;
    }

    // =====================================================
    // 🔹 6️⃣ VALIDAR TELÉFONO (exactamente 9 dígitos)
    // =====================================================
    public static boolean validarTelefono(String movil) {
        if (movil.matches("\\d{9}")) {
            System.out.println("✅ Teléfono correcto");
            return true;
        } else {
            System.out.println("⚠️ Teléfono inválido (debe tener 9 dígitos)");
            return false;
        }
    }

    // =====================================================
    // 🔹 7️⃣ LEER ENTERO DEL TECLADO DE FORMA SEGURA
    // =====================================================
    public static int leerEnteroSeguro(String mensaje) {
        Scanner sc = new Scanner(System.in);
        int valor = 0;
        boolean valido = false;
        // Repetimos hasta que el usuario meta un número correcto
        while (!valido) {
            try {
                System.out.print(mensaje);
                valor = Integer.parseInt(sc.nextLine());
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Error: introduce un número entero válido.");
            }
        }
        return valor;
    }

    // =====================================================
    // 🔹 8️⃣ MAIN DE PRUEBA — EJEMPLOS DE USO
    // =====================================================
    public static void main(String[] args) {

        // Insertar máquina solo si no existe
        insertarMaquinaSiNoExiste("M001", "Taladro", 5);
        insertarSiNoExiste("M002", "Sierra eléctrica", 3);

        // Insertar cliente con código autogenerado
        insertarCliente("Juan", "Martinez");

        // Insertar una fecha en formato "YYYY-MM-DD"
        insertarFecha("M001", LocalDate.now());

        // Validar DNI y teléfono
        validarDNI("12345678Z");
        validarTelefono("612345678");

        // Leer número entero de forma segura
        int unidades = leerEnteroSeguro("Introduce unidades nuevas: ");
        System.out.println("Has introducido: " + unidades);

        System.out.println("✅ Fin del programa de prueba");
    }
}

/*
=====================================================
📋 CHULETA SQL RÁPIDA — FUNCIONES MÁS USADAS
=====================================================

-- Crear tabla de máquinas
CREATE TABLE IF NOT EXISTS maquina (
    codigo_maquina TEXT PRIMARY KEY,
    descripcion TEXT,
    unidades INTEGER,
    fecha_registro TEXT
);

-- Crear tabla de clientes
CREATE TABLE IF NOT EXISTS clientes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    codigo TEXT,
    nombre TEXT,
    apellido TEXT
);

-- Sentencias típicas:
SELECT * FROM maquina;
UPDATE maquina SET unidades = 10 WHERE codigo_maquina = 'M001';
DELETE FROM maquina WHERE codigo_maquina = 'M002';
SELECT COUNT(*) FROM maquina;
SELECT SUM(unidades) FROM maquina;

-- Fechas en SQLite:
DATE('now')                 → fecha actual
DATETIME('now')             → fecha y hora actual
DATE('now', '+1 day')       → mañana
STRFTIME('%d/%m/%Y', 'now') → formato español

-- Recordatorio JDBC:
executeUpdate() → INSERT, UPDATE, DELETE
executeQuery()  → SELECT
TEXT  → para cadenas
INTEGER → para números
REAL → para decimales
PRAGMA foreign_keys = ON; → activa claves foráneas
=====================================================
*/
