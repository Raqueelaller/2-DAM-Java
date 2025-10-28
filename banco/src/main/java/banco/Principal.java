package banco;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class Principal {

	public static void main(String[] args) throws FileNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		Scanner teclado = new Scanner(System.in);

		String usuario, password, servidor, puerto, basedatos;
		Properties configuracion = new Properties();

		configuracion.load(new FileInputStream("mysqlBanco.txt"));

		usuario = configuracion.getProperty("usuario");

		password = configuracion.getProperty("password");

		servidor = configuracion.getProperty("IP");

		puerto = configuracion.getProperty("Port");

		basedatos = configuracion.getProperty("basedatos");
		String url = String.format("jdbc:mysql://%s:%s/%s", servidor, puerto, basedatos);
		
		Connection conexion = null;
		String pregunta = null;
		
		do {
			System.out.println("¿qué quieres hacer?");
			System.out.println("a)traspasar dinero de una cuenta a otra");
			System.out.println("b)mostrar cuentas");
			System.out.println("c)salir");
			pregunta=teclado.nextLine();
			pregunta=pregunta.toLowerCase();
			switch(pregunta) {
			case "a":
				System.out.println("dime el id de la cuenta origen");
				String origen = teclado.nextLine();
				System.out.println("dime el id de la cuenta destino");
				String destino = teclado.nextLine();
				System.out.println("¿cual es la cantidad que deseas traspasar?");
				double cantidad = teclado.nextDouble();
				teclado.nextLine();
				if(tieneSaldo(origen,cantidad,url,usuario,password)==true) {
				if (existeUsuario(origen,url,usuario,password)==true && existeUsuario(destino,url,usuario,password)==true){
					
				traspasar_cantidad(origen,destino,cantidad,url,usuario,password);
				} else {
					System.out.println("no existe la cuenta que has introducido");
				}} else {
					System.out.println("no hay suficiente saldo");
				}
				break;
			case "b":
				mostrarCuentas(url,usuario,password);
				break;
			case "c":
				System.out.println("hasta pronto!");
				
			
				
			}
			
		}while(!pregunta.equalsIgnoreCase("c"));
		
		
			
		
	}

	public static void traspasar_cantidad(String origen, String destino, double cantidad, String url, String usuario, String password)throws FileNotFoundException, IOException,SQLException{
		Connection conexion = null;
		if(cantidad>=0) {
			
		try {
			// Establecer conexión
			conexion = DriverManager.getConnection(url, usuario, password);
			// Desactivar autocommit para manejar transacciones manualmente
			conexion.setAutoCommit(false);
			String sql1 = "UPDATE cuentas SET saldo = saldo - ? WHERE id_cuenta = ?";
			PreparedStatement stmt1 = conexion.prepareStatement(sql1);
			stmt1.setDouble(1, cantidad); // Cantidad a restar
			stmt1.setString(2, origen); // ID del artículo
			stmt1.executeUpdate();
			
			String sql2 = "UPDATE cuentas SET saldo = saldo + ? WHERE id_cuenta = ?";
			PreparedStatement stmt2 = conexion.prepareStatement(sql2);
			stmt2.setDouble(1, cantidad); // Cantidad a sumar
			stmt2.setString(2, destino); // ID del artículo
			stmt2.executeUpdate();
			
			conexion.commit();
			System.out.println("Transacción completada con éxito");
			
			
		} catch (SQLException e) {
			// En caso de error, revertir la transacción
			try {
			if (conexion != null) {
			conexion.rollback();
			System.out.println("Transacción revertida debido a un error.");
			}
			} catch (SQLException rollbackEx) {
			rollbackEx.printStackTrace();
			}
			e.printStackTrace();
			} finally {
			// Restaurar autocommit y cerrar conexión
			try {
			if (conexion != null) {
			conexion.setAutoCommit(true);
			conexion.close();
			}
			} catch (SQLException closeEx) {
			closeEx.printStackTrace();
			}
	} 
		} else {
			System.out.println("introduce una cantidad válida");
		}
	}
	public static void mostrarCuentas(String url, String usuario, String password) {
		try {
			Connection conn = DriverManager.getConnection(url, usuario, password);
			String sql = "select * from cuentas";
			PreparedStatement stmt1 = conn.prepareStatement(sql);
			stmt1.executeQuery();

			

					ResultSet rs = stmt1.executeQuery();
					while (rs.next()) {
						System.out.println("codigo de cuenta: " + rs.getString("id_cuenta"));
						System.out.println("nombre del cliente: " + rs.getString("nombre_cliente"));
						System.out.println("saldo: " + rs.getDouble("saldo"));
						System.out.println(" ");
						
					}
					stmt1.close();
					conn.close();
					}catch (Exception x) {
						System.out.println(x.getMessage());
					}
			}
			
	public static boolean existeUsuario(String codigoUsuario,String url, String usuario, String password) {
		boolean existe = true;
		try {
			Connection conn = DriverManager.getConnection(url, usuario, password);
			String sql = "select * from cuentas where id_cuenta = ?";
			PreparedStatement stmt1 = conn.prepareStatement(sql);
			stmt1.setString(1, codigoUsuario);
			stmt1.executeQuery();

			ResultSet filas = stmt1.executeQuery();
			if(filas.next()) {
				existe=true;
			}
			else  {
				existe=false;
			}
			stmt1.close();
			conn.close();
		}catch (Exception x) {
			System.out.println(x.getMessage());
		}
		
		return existe;
	}
	
	public static boolean tieneSaldo(String codigoUsuario, double cantidad, String url, String usuario, String password) throws SQLException {
		
		Connection conexion = null;
		boolean existe = true;
		try {
			Connection conn = DriverManager.getConnection(url, usuario, password);
			String sql = "select * from cuentas where id_cuenta = ?";
			PreparedStatement stmt1 = conn.prepareStatement(sql);
			stmt1.setString(1, codigoUsuario);
			stmt1.executeQuery();

			ResultSet filas = stmt1.executeQuery();
			try {
				Connection conn1 = DriverManager.getConnection(url, usuario, password);
				String sql1 = "select * from cuentas where id_cuenta = ?";
				PreparedStatement stmt2 = conn.prepareStatement(sql1);
				stmt2.setString(1, codigoUsuario);
				stmt2.executeQuery();

				ResultSet rs = stmt2.executeQuery();
				while (rs.next()) {
					if(rs.getDouble("saldo")<cantidad) {
						existe=false;
					}
					
				}
			stmt1.close();
			conn.close();
		}catch (Exception x) {
			System.out.println(x.getMessage());
		}
		
		return existe;
	} finally {
		// Restaurar autocommit y cerrar conexión
		try {
		if (conexion != null) {
		conexion.setAutoCommit(true);
		conexion.close();
		}
		} catch (SQLException closeEx) {
		closeEx.printStackTrace();
		}

	}
	}
}
