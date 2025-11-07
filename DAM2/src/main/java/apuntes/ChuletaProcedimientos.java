package apuntes;

public class ChuletaProcedimientos {
	/*
	=====================================================
	📘 CHULETA JAVA + SQLITE — SIMULANDO PROCEDIMIENTOS
	=====================================================

	💡 Contexto:
	En MySQL se pueden crear procedimientos almacenados.
	En SQLite no. Por tanto, lo hacemos desde Java.

	TABLA BASE:
	-------------
	CREATE TABLE IF NOT EXISTS usuarios (
	    coduser INTEGER PRIMARY KEY AUTOINCREMENT,
	    nombrelogin TEXT UNIQUE,
	    contrasena TEXT,
	    nombrecompleto TEXT
	);

	=====================================================
	1️⃣ CONEXIÓN A LA BASE DE DATOS SQLITE
	=====================================================
	*/

	import java.sql.*;
	import java.security.MessageDigest;
	import java.security.NoSuchAlgorithmException;

	public class GestorUsuarios {
	    // Ruta del archivo SQLite (si el profe te lo da, usa su nombre)
	    private static final String URL = "jdbc:sqlite:banco.db";

	    // ==============================================
	    // 🔐 MÉTODO AUXILIAR: ENCRIPTAR CONTRASEÑA (SHA-256)
	    // ==============================================
	    /*
	     * SQLite no tiene funciones SHA2 como MySQL,
	     * así que el hash (encriptación) se hace en Java.
	     */
	    public static String encriptarSHA256(String texto) {
	        try {
	            MessageDigest md = MessageDigest.getInstance("SHA-256");
	            byte[] hash = md.digest(texto.getBytes());
	            StringBuilder hex = new StringBuilder();
	            for (byte b : hash) {
	                hex.append(String.format("%02x", b)); // convierte bytes a hexadecimales
	            }
	            return hex.toString(); // devuelve el hash completo
	        } catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException(e);
	        }
	    }

	    // ==============================================
	    // 🧩 PROCEDIMIENTO 1 (Simulado):
	    // INSERTAR USUARIO CON CONTRASEÑA CIFRADA
	    // ==============================================
	    /*
	     * Equivale al procedimiento MySQL con SHA2.
	     * Inserta un nuevo usuario y guarda la contraseña cifrada.
	     */
	    public static void insertarUsuario(String login, String contrasena, String nombre) throws SQLException {
	        String sql = "INSERT INTO usuarios (nombrelogin, contrasena, nombrecompleto) VALUES (?, ?, ?)";
	        try (Connection conn = DriverManager.getConnection(URL);
	             PreparedStatement stmt = conn.prepareStatement(sql)) {

	            String hash = encriptarSHA256(contrasena); // ciframos antes de guardar
	            stmt.setString(1, login);
	            stmt.setString(2, hash);
	            stmt.setString(3, nombre);

	            stmt.executeUpdate();
	            System.out.println("✅ Usuario insertado con contraseña cifrada.");
	        } catch (SQLException e) {
	            System.out.println("⚠️ Error al insertar: " + e.getMessage());
	        }
	    }

	    // ==============================================
	    // 🧮 PROCEDIMIENTO 2 (Simulado):
	    // CONTAR CUÁNTAS VECES EXISTE UN USUARIO
	    // ==============================================
	    /*
	     * Equivale al procedimiento que devolvía un parámetro OUT en MySQL.
	     * Devuelve el número de veces que aparece el nombrelogin.
	     */
	    public static int contarUsuarios(String login) throws SQLException {
	        String sql = "SELECT COUNT(*) FROM usuarios WHERE nombrelogin = ?";
	        try (Connection conn = DriverManager.getConnection(URL);
	             PreparedStatement stmt = conn.prepareStatement(sql)) {

	            stmt.setString(1, login);
	            ResultSet rs = stmt.executeQuery();
	            int total = rs.next() ? rs.getInt(1) : 0;
	            System.out.println("🔹 El usuario '" + login + "' aparece " + total + " vez/veces.");
	            return total;
	        }
	    }

	    // ==============================================
	    // 🔑 PROCEDIMIENTO 3 (Simulado):
	    // COMPROBAR LOGIN DEL USUARIO
	    // ==============================================
	    /*
	     * Equivale al procedimiento que en MySQL comparaba SHA2(password)
	     * con la contraseña encriptada guardada en la base de datos.
	     */
	    public static boolean loginUsuario(String login, String contrasena) throws SQLException {
	        String sql = "SELECT contrasena FROM usuarios WHERE nombrelogin = ?";
	        try (Connection conn = DriverManager.getConnection(URL);
	             PreparedStatement stmt = conn.prepareStatement(sql)) {

	            stmt.setString(1, login);
	            ResultSet rs = stmt.executeQuery();

	            if (rs.next()) {
	                String hashGuardado = rs.getString("contrasena");
	                String hashTecleado = encriptarSHA256(contrasena);

	                if (hashGuardado.equals(hashTecleado)) {
	                    System.out.println("✅ Login correcto: hash coincide.");
	                    return true;
	                } else {
	                    System.out.println("❌ Contraseña incorrecta.");
	                    return false;
	                }
	            } else {
	                System.out.println("⚠️ Usuario no encontrado.");
	                return false;
	            }
	        }
	    }

	    // ==============================================
	    // 🧪 MÉTODO MAIN PARA PROBAR TODO (SIMULACIÓN DE EXAMEN)
	    // ==============================================
	    public static void main(String[] args) throws SQLException {
	        // 1️⃣ Insertar un usuario nuevo
	        insertarUsuario("raquel", "clave123", "Raquel Aller");

	        // 2️⃣ Contar cuántas veces aparece el usuario
	        contarUsuarios("raquel");

	        // 3️⃣ Intentar iniciar sesión
	        loginUsuario("raquel", "clave123");   // correcto
	        loginUsuario("raquel", "otraClave"); // incorrecto
	    }
	}

	/*
	=====================================================
	📘 RESUMEN RÁPIDO (PARA EXAMEN)
	=====================================================

	🟩 TABLA usuarios:
	CREATE TABLE usuarios (
	    coduser INTEGER PRIMARY KEY AUTOINCREMENT,
	    nombrelogin TEXT UNIQUE,
	    contrasena TEXT,
	    nombrecompleto TEXT
	);

	🟦 MÉTODOS EQUIVALENTES A PROCEDIMIENTOS MYSQL:

	1️⃣ insertarUsuario()
	   - Inserta un nuevo usuario.
	   - Encripta la contraseña con SHA-256 desde Java.
	   - Usa PreparedStatement para evitar inyecciones SQL.

	2️⃣ contarUsuarios()
	   - Ejecuta SELECT COUNT(*) WHERE nombrelogin = ?.
	   - Devuelve el número de registros que coinciden.

	3️⃣ loginUsuario()
	   - Busca la contraseña cifrada guardada.
	   - Cifra la que escribe el usuario.
	   - Compara los dos hashes (si coinciden → login correcto).

	=====================================================
	⚙️ CONSEJOS DE EXAMEN
	=====================================================

	✔ Usa try-with-resources para cerrar conexión automáticamente.
	✔ Usa siempre PreparedStatement.
	✔ Recuerda que SQLite no tiene SHA2() → hay que hacerlo en Java.
	✔ Los TEXT no tienen límite (no uses VARCHAR).
	✔ INTEGER PRIMARY KEY AUTOINCREMENT = ID autoincremental.
	✔ Si hay claves foráneas → activa PRAGMA foreign_keys = ON;
	✔ Para depurar → usa e.printStackTrace() temporalmente.

	=====================================================
	✅ PATRÓN MENTAL
	=====================================================
	MySQL: CREATE PROCEDURE → se ejecuta dentro del motor SQL.
	SQLite: No hay procedimientos → se simulan con métodos Java.

	Cada método Java = procedimiento almacenado manual.

	=====================================================
	*/


}
