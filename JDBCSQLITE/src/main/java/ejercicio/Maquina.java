package ejercicio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;


public class Maquina {
	
	String codigo_maquina;
	String descripcion;
	int unidades;
	public Maquina(String codigo_maquina, String descripcion, int unidades) {
		super();
		this.codigo_maquina = codigo_maquina;
		this.descripcion = descripcion;
		this.unidades = unidades;
	}
	public String getCodigo_maquina() {
		return codigo_maquina;
	}
	public void setCodigo_maquina(String codigo_maquina) {
		this.codigo_maquina = codigo_maquina;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getUnidades() {
		return unidades;
	}
	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}
	
	public static void anyadirMaquina(String codigo_maquina, String descripcion, int unidades ) {
		String sql = "INSERT INTO maquina (codigo_maquina, descripcion, unidades) VALUES (?, ?, ?) ";
		try (Connection conn = DriverManager.getConnection("jdbc:sqlite:maquinaria.db");
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setString(1,codigo_maquina);
			stmt.setString(2, descripcion);
			stmt.setInt(3, unidades);
			//Aquí ejecutamos el INSERT realmente:
	        int filas = stmt.executeUpdate();
	        System.out.println("Filas insertadas: " + filas);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		
	}
	
	public static void verMaquinas() {
		try (Connection con = DriverManager.getConnection("jdbc:sqlite:maquinaria.db");
				Statement stmt = con.createStatement()) {
			String sql = "SELECT * from maquina";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.printf("codigo de la máquina= %s, descripción = %s, unidades: %d \n", rs.getString(1), rs.getString(2),rs.getInt(3));
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}
	
	public static void eliminarMaquina(String codigo_maquina) throws SQLException{
		String sql = "DELETE FROM maquina WHERE codigo_maquina = ?";
		try (Connection conn = DriverManager.getConnection("jdbc:sqlite:maquinaria.db")) {
		    conn.createStatement().execute("PRAGMA foreign_keys = ON;");
		    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
		        stmt.setString(1, codigo_maquina);
		        int filas = stmt.executeUpdate();
		        System.out.println("Filas eliminadas: " + filas);
		    }
		}
	}
	
	public static void actualizarMaquina(String codigo_maquina, String campo, String cambio) {
	    // ⚠️ Solo concatenamos el nombre del campo (no el valor)
	    String sql = "UPDATE maquina SET " + campo + " = ? WHERE codigo_maquina = ?";

	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:maquinaria.db");
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        // Asignamos el valor según el tipo
	        if (campo.equalsIgnoreCase("unidades")) {
	            stmt.setInt(1, Integer.parseInt(cambio));  // primer ?
	        } else {
	            stmt.setString(1, cambio);                // primer ?
	        }

	        // Código de la máquina → segundo ?
	        stmt.setString(2, codigo_maquina);

	        // Ejecutamos el UPDATE
	        int filas = stmt.executeUpdate();

	        if (filas > 0) {
	            System.out.println("Máquina actualizada correctamente (" + filas + " fila/s).");
	        } else {
	            System.out.println("No se encontró ninguna máquina con ese código.");
	        }

	    } catch (Exception e) {
	        System.out.println("Error al actualizar: " + e.getMessage());
	    }
	}
	
	/* SI NO TIENE EL ON DELETE CASCADE PUESTO:
	 * public static void eliminarMaquina(String codigo_maquina) {
    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:maquinaria.db")) {
        conn.createStatement().execute("PRAGMA foreign_keys = ON;");

        // Primero intentamos borrar las piezas que dependen de esta máquina
        String borrarPiezas = "DELETE FROM pieza WHERE codigo_maquina = ?";
        try (PreparedStatement st1 = conn.prepareStatement(borrarPiezas)) {
            st1.setString(1, codigo_maquina);
            int piezasBorradas = st1.executeUpdate();
            System.out.println("🧩 Piezas eliminadas: " + piezasBorradas);
        }

        // Luego borramos la máquina
        String borrarMaquina = "DELETE FROM maquina WHERE codigo_maquina = ?";
        try (PreparedStatement st2 = conn.prepareStatement(borrarMaquina)) {
            st2.setString(1, codigo_maquina);
            int filas = st2.executeUpdate();
            System.out.println("✅ Máquina eliminada: " + filas);
        }

    } catch (SQLException e) {
        System.out.println("❌ Error al eliminar: " + e.getMessage());
    }
}
	 */
	
	/*SI NO TIENE EL ON UPDATE CASCADE PUESTO
	 * public static void actualizarCodigoMaquina(String antiguoCodigo, String nuevoCodigo) {
    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:maquinaria.db")) {
        conn.createStatement().execute("PRAGMA foreign_keys = ON;");

        // Primero actualizamos el código en las piezas (simula ON UPDATE CASCADE)
        String sqlPiezas = "UPDATE pieza SET codigo_maquina = ? WHERE codigo_maquina = ?";
        try (PreparedStatement st1 = conn.prepareStatement(sqlPiezas)) {
            st1.setString(1, nuevoCodigo);
            st1.setString(2, antiguoCodigo);
            int piezasAfectadas = st1.executeUpdate();
            System.out.println("🧩 Piezas actualizadas: " + piezasAfectadas);
        }

        // Luego actualizamos el código en la tabla maquina
        String sqlMaquina = "UPDATE maquina SET codigo_maquina = ? WHERE codigo_maquina = ?";
        try (PreparedStatement st2 = conn.prepareStatement(sqlMaquina)) {
            st2.setString(1, nuevoCodigo);
            st2.setString(2, antiguoCodigo);
            int filas = st2.executeUpdate();
            System.out.println("✅ Máquina actualizada: " + filas);
        }

    } catch (SQLException e) {
        System.out.println("❌ Error al actualizar: " + e.getMessage());
    }
}

	 */
	
	


}
