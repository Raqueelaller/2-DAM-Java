package ejercicio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Pieza {
	String codigo_pieza;
	String descripcion;
	int unidades;
	String codigo_maquina;
	public Pieza(String codigo_pieza, String descripcion, int unidades, String codigo_maquina) {
		super();
		this.codigo_pieza = codigo_pieza;
		this.descripcion = descripcion;
		this.unidades = unidades;
		this.codigo_maquina = codigo_maquina;
	}
	public String getCodigo_pieza() {
		return codigo_pieza;
	}
	public void setCodigo_pieza(String codigo_pieza) {
		this.codigo_pieza = codigo_pieza;
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
	public String getCodigo_maquina() {
		return codigo_maquina;
	}
	public void setCodigo_maquina(String codigo_maquina) {
		this.codigo_maquina = codigo_maquina;
	}
	
	
	
	public static void anyadirPieza(String codigo_pieza, String descripcion, int unidades, String codigo_maquina ) throws SQLException {
		
		if (existeMaquina(codigo_maquina)) {
			String sql = "INSERT INTO pieza (codigo_pieza, descripcion, unidades, codigo_maquina) VALUES (?, ?, ?, ?) ";
			try (Connection conn = DriverManager.getConnection("jdbc:sqlite:maquinaria.db");
					PreparedStatement stmt = conn.prepareStatement(sql)) {
				
				stmt.setString(1,codigo_pieza);
				stmt.setString(2, descripcion);
				stmt.setInt(3, unidades);
				stmt.setString(4, codigo_maquina);
				//Aquí ejecutamos el INSERT realmente:
		        int filas = stmt.executeUpdate();
		        System.out.println("Filas insertadas: " + filas);
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		    
		} else {
		    System.out.println("⚠️ No se puede insertar la pieza: la máquina no existe.");
		}
		
	}
	
	public static void verPieza() {
		try (Connection con = DriverManager.getConnection("jdbc:sqlite:maquinaria.db");
				Statement stmt = con.createStatement()) {
			String sql = "SELECT * from pieza";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.printf("codigo de la pieza= %s, descripción = %s, unidades: %d, codigo de la máquina: %s \n", rs.getString(1), rs.getString(2),rs.getInt(3), rs.getString(4));
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}
	
	public static void eliminarPieza(String codigo_pieza) throws SQLException{
		String sql = "DELETE FROM pieza WHERE codigo_pieza = ?";
		try (Connection conn = DriverManager.getConnection("jdbc:sqlite:maquinaria.db")) {
		    conn.createStatement().execute("PRAGMA foreign_keys = ON;");
		    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
		        stmt.setString(1, codigo_pieza);
		        int filas = stmt.executeUpdate();
		        System.out.println("Filas eliminadas: " + filas);
		    }
		}
	}
	
	public static void actualizarPieza(String codigo_pieza, String campo, String cambio) {
		
	    // ⚠️ Solo concatenamos el nombre del campo (no el valor)
	    String sql = "UPDATE pieza SET " + campo + " = ? WHERE codigo_pieza = ?";

	    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:maquinaria.db");
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        // Asignamos el valor según el tipo
	        if (campo.equalsIgnoreCase("unidades")) {
	            stmt.setInt(1, Integer.parseInt(cambio));  // primer ?
	        } else {
	            stmt.setString(1, cambio);                // primer ?
	        }

	        // Código de la máquina → segundo ?
	        stmt.setString(2, codigo_pieza);

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
	
	  // ANTES DE INSERTAR UNA PIEZA
	  public static boolean existeMaquina(String codigo_maquina) throws SQLException {
    String sql = "SELECT COUNT(*) FROM maquina WHERE codigo_maquina = ?";
    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:maquinaria.db");
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, codigo_maquina);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt(1) > 0;
    }
	  }


	
	


}
