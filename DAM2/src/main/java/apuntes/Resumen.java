package apuntes;

public class Resumen {
	
	/*
	=====================================================
	📘 CHULETA JAVA + SQLITE — EXAMEN DE ACCESO A DATOS
	=====================================================

	Autor: tú mismo 😎
	Base de datos: SQLite (no necesita servidor)
	Driver: JDBC incluido con SQLite (org.sqlite.JDBC)
	Fichero BD: banco.db (se crea solo)

	-----------------------------------------------------
	🧩 DIFERENCIAS ENTRE MYSQL Y SQLITE
	-----------------------------------------------------
	MySQL ------------------ SQLite ---------------------
	VARCHAR(50)             TEXT
	INT AUTO_INCREMENT      INTEGER PRIMARY KEY AUTOINCREMENT
	DOUBLE / DECIMAL        REAL
	BOOLEAN                 INTEGER (0 = false, 1 = true)
	DATE / DATETIME         TEXT (formato "YYYY-MM-DD")
	FOREIGN KEY (...)       Igual, pero usar PRAGMA foreign_keys = ON

	-----------------------------------------------------
	🔌 CONEXIÓN A SQLITE
	-----------------------------------------------------
	Connection conn = DriverManager.getConnection("jdbc:sqlite:banco.db");

	(No necesita usuario ni contraseña)
	El archivo banco.db se crea automáticamente si no existe.
	Siempre cerrar las conexiones con try-with-resources.
	-----------------------------------------------------
	*/

	/* =============================================
	   🏗️ CREAR TABLAS CON FOREIGN KEY
	   ============================================= */
	try (Connection conn = DriverManager.getConnection("jdbc:sqlite:banco.db");
	     Statement st = conn.createStatement()) {

	    // Activar claves foráneas
	    st.execute("PRAGMA foreign_keys = ON;");

	    // Crear tabla clientes
	    st.execute("""
	        CREATE TABLE IF NOT EXISTS clientes (
	            id INTEGER PRIMARY KEY AUTOINCREMENT,
	            nombre TEXT NOT NULL,
	            apellido TEXT NOT NULL,
	            codigo TEXT UNIQUE
	        );
	    """);

	    // Crear tabla cuentas con foreign key
	    st.execute("""
	        CREATE TABLE IF NOT EXISTS cuentas (
	            id_cuenta INTEGER PRIMARY KEY AUTOINCREMENT,
	            id_cliente INTEGER NOT NULL,
	            saldo REAL DEFAULT 0,
	            FOREIGN KEY (id_cliente) REFERENCES clientes(id)
	        );
	    """);
	}

	/* =============================================
	   ➕ INSERTAR DATOS
	   ============================================= */

	// Ejemplo simple
	String sql = "INSERT INTO clientes (nombre, apellido) VALUES (?, ?)";
	try (Connection conn = DriverManager.getConnection("jdbc:sqlite:banco.db");
	     PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

	    stmt.setString(1, "Raquel");
	    stmt.setString(2, "Aller");
	    stmt.executeUpdate();

	    // Obtener ID generado automáticamente
	    ResultSet rs = stmt.getGeneratedKeys();
	    if (rs.next()) {
	        int id = rs.getInt(1);
	        String codigo = "R" + "A" + id;

	        // Actualizar el código calculado
	        String sql2 = "UPDATE clientes SET codigo = ? WHERE id = ?";
	        try (PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
	            stmt2.setString(1, codigo);
	            stmt2.setInt(2, id);
	            stmt2.executeUpdate();
	        }
	    }
	}

	/* =============================================
	   🔍 CONSULTAR DATOS (SELECT)
	   ============================================= */
	String sql = "SELECT * FROM clientes";
	try (Connection conn = DriverManager.getConnection("jdbc:sqlite:banco.db");
	     PreparedStatement stmt = conn.prepareStatement(sql);
	     ResultSet rs = stmt.executeQuery()) {

	    while (rs.next()) {
	        System.out.println("ID: " + rs.getInt("id"));
	        System.out.println("Nombre: " + rs.getString("nombre"));
	        System.out.println("Apellido: " + rs.getString("apellido"));
	        System.out.println("Código: " + rs.getString("codigo"));
	        System.out.println("------------------------");
	    }
	}

	/* =============================================
	   ✏️ ACTUALIZAR DATOS (UPDATE)
	   ============================================= */
	// Siempre usar PreparedStatement para evitar errores y SQL injection
	String sql = "UPDATE cuentas SET saldo = saldo + ? WHERE id_cuenta = ?";
	try (Connection conn = DriverManager.getConnection("jdbc:sqlite:banco.db");
	     PreparedStatement stmt = conn.prepareStatement(sql)) {
	    stmt.setDouble(1, 100.50);
	    stmt.setInt(2, 1);
	    stmt.executeUpdate();
	}

	/* =============================================
	   🗑️ BORRAR DATOS (DELETE)
	   ============================================= */
	// Ojo con claves foráneas: activar PRAGMA foreign_keys = ON
	String sql = "DELETE FROM clientes WHERE id = ?";
	try (Connection conn = DriverManager.getConnection("jdbc:sqlite:banco.db")) {
	    conn.createStatement().execute("PRAGMA foreign_keys = ON;");
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, 3);
	        int filas = stmt.executeUpdate();
	        System.out.println("Filas eliminadas: " + filas);
	    }
	}

	/* =============================================
	   💸 TRANSACCIONES (BEGIN, COMMIT, ROLLBACK)
	   ============================================= */
	// Ejemplo de traspaso de saldo entre cuentas
	try (Connection conn = DriverManager.getConnection("jdbc:sqlite:banco.db")) {
	    conn.setAutoCommit(false); // Desactivar autocommit para controlar transacción
	    try {
	        PreparedStatement restar = conn.prepareStatement(
	            "UPDATE cuentas SET saldo = saldo - ? WHERE id_cuenta = ?");
	        PreparedStatement sumar = conn.prepareStatement(
	            "UPDATE cuentas SET saldo = saldo + ? WHERE id_cuenta = ?");

	        double cantidad = 50;
	        restar.setDouble(1, cantidad);
	        restar.setInt(2, 1); // cuenta origen
	        restar.executeUpdate();

	        sumar.setDouble(1, cantidad);
	        sumar.setInt(2, 2); // cuenta destino
	        sumar.executeUpdate();

	        conn.commit();
	        System.out.println("✅ Transacción completada con éxito");
	    } catch (SQLException e) {
	        conn.rollback(); // Si algo falla, revierte los cambios
	        System.out.println("⚠️ Error, transacción revertida");
	    } finally {
	        conn.setAutoCommit(true);
	    }
	}

	/* =============================================
	   ⚙️ MÉTODOS GENÉRICOS (para reusar)
	   ============================================= */

	// Método genérico para UPDATE
	public static void actualizarCampo(String tabla, String campo, Object valor, String condicion) throws SQLException {
	    String sql = "UPDATE " + tabla + " SET " + campo + " = ? WHERE " + condicion;
	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:banco.db");
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setObject(1, valor);
	        stmt.executeUpdate();
	    }
	}

	// Método genérico para DELETE
	public static void borrarRegistro(String tabla, String condicion) throws SQLException {
	    String sql = "DELETE FROM " + tabla + " WHERE " + condicion;
	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:banco.db")) {
	        conn.createStatement().execute("PRAGMA foreign_keys = ON;");
	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.executeUpdate();
	        }
	    }
	}

	// Método genérico para mostrar cualquier tabla
	public static void mostrarTabla(String tabla) throws SQLException {
	    String sql = "SELECT * FROM " + tabla;
	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:banco.db");
	         Statement st = conn.createStatement();
	         ResultSet rs = st.executeQuery(sql)) {
	        ResultSetMetaData meta = rs.getMetaData();
	        int columnas = meta.getColumnCount();
	        while (rs.next()) {
	            for (int i = 1; i <= columnas; i++) {
	                System.out.print(meta.getColumnName(i) + ": " + rs.getString(i) + " | ");
	            }
	            System.out.println();
	        }
	    }
	}

	/* =============================================
	   🧠 RESUMEN FINAL DE COMANDOS CLAVE
	   =============================================

	-- Crear base de datos (automático)
	jdbc:sqlite:nombre.db

	-- Activar claves foráneas
	PRAGMA foreign_keys = ON;

	-- Crear tabla
	CREATE TABLE IF NOT EXISTS tabla (
	    id INTEGER PRIMARY KEY AUTOINCREMENT,
	    campo1 TEXT,
	    campo2 REAL,
	    id_otratabla INTEGER,
	    FOREIGN KEY (id_otratabla) REFERENCES otratabla(id)
	);

	-- Insertar
	INSERT INTO tabla (campo1, campo2) VALUES (?, ?);

	-- Update
	UPDATE tabla SET campo1 = ? WHERE id = ?;

	-- Delete
	DELETE FROM tabla WHERE id = ?;

	-- Select
	SELECT * FROM tabla;

	-- Transacciones
	conn.setAutoCommit(false);
	conn.commit();
	conn.rollback();
	conn.setAutoCommit(true);

	-----------------------------------------------------
	✅ CONSEJOS DE EXAMEN
	-----------------------------------------------------
	1. Usa siempre try-with-resources (cierra solo).
	2. Usa PreparedStatement en TODO.
	3. Activa foreign_keys si hay relaciones.
	4. Usa INTEGER PRIMARY KEY AUTOINCREMENT para IDs.
	5. Los TEXT no necesitan longitud.
	6. Si algo falla → usa conn.rollback().
	7. Para ver errores: e.printStackTrace() temporalmente.
	=====================================================
	*/


}
