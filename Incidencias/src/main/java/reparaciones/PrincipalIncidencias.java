package reparaciones;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Properties;
import java.util.Scanner;

public class PrincipalIncidencias {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		Scanner teclado = new Scanner(System.in);

		String usuario, password, servidor, puerto, basedatos;
		Properties configuracion = new Properties();

		configuracion.load(new FileInputStream("mysqlReparaciones.txt"));

		usuario = configuracion.getProperty("usuario");

		password = configuracion.getProperty("password");

		servidor = configuracion.getProperty("IP");

		puerto = configuracion.getProperty("Port");

		basedatos = configuracion.getProperty("basedatos");
		String url = String.format("jdbc:mysql://%s:%s/%s", servidor, puerto, basedatos);

		String pregunta = null;

		do {

			System.out.println("Bienvenid@ al menú de incidencias, ¿qué desea hacer?");
			System.out.println("a) crear una nueva incidencia");
			System.out.println("b) actualizar incidencia");
			System.out.println("c) terminar incidencia");
			System.out.println("d) mostrar incidencias");
			System.out.println("e) salir");
			 pregunta = teclado.nextLine().toLowerCase();

			switch (pregunta) {

			case "a":
				 System.out.println("dime el código de cliente");
				 int codigoCliente = teclado.nextInt();
				 teclado.nextLine();
				existeCliente(codigoCliente,url, usuario, password);
				if(existeCliente(codigoCliente,url, usuario, password)==true) {
					System.out.println("dime el código de incidencia");
					String codigoIncidencia= teclado.nextLine();
					System.out.println("pon el asunto de esta incidencia");
					String asunto = teclado.nextLine();
					System.out.println("dame una descripción");
					String descripcion = teclado.nextLine();
					System.out.println("dime el importe de esta incidencia");
					double importe = teclado.nextDouble();
					try{
						crearIncidencia(codigoIncidencia,codigoCliente,asunto,descripcion,importe,url,usuario,password);
					}catch (IOException x) {
						System.out.println(x.getMessage());
					}
				}
				teclado.nextLine();
				System.out.println("----------------------- Creando nueva incidencia -------------------------");
				break;
			case "b":
				
				System.out.println("dime el codigo de incidencia ");
				String codigoIncidencia = teclado.nextLine();
				existeIncidencia(codigoIncidencia,url,usuario,password);
				if(existeIncidencia(codigoIncidencia,url,usuario,password)==true) {
					System.out.println("dime el campo que quieres cambiar");
					String campo = teclado.nextLine();
					System.out.println("escribe la nueva información");
					String nuevo = teclado.nextLine();
					actualizarIncidencia(codigoIncidencia,campo,nuevo,url,usuario,password);
					
				}

				teclado.nextLine();

				// anyadirPersona(nombre, telefono, saldo, url, usuario, password);

				System.out.println("----------------------------- AÑADIENDO PERSONA -----------------------");

				break;

			case "c":
				System.out.println("dime el codigo de incidencia: ");
				String codigoIncidenci = teclado.nextLine();
				existeIncidencia(codigoIncidenci,url,usuario,password);
				if(existeIncidencia(codigoIncidenci,url,usuario,password)==true) {
					terminarIncidencia(codigoIncidenci,url,usuario,password);
					
				}

				
				System.out.println("----------------------------- ELIMINANDO PERSONA -----------------------");

				break;
			case "d":
				mostrarIncidencias(url,usuario,password);
				break;

			case "e":
				System.out.println("Hasta pronto, gracias :)");
				

			default:
				System.out.println("elige una opción válida");
			}

		} while (!pregunta.equalsIgnoreCase("e"));

	}

	public static boolean existeCliente(int codigo,String url, String usuario, String password) {
		boolean existe = true;
		try {
			Connection conn = DriverManager.getConnection(url, usuario, password);
			String sql = "select * from clientes where codigo = ?";
			PreparedStatement stmt1 = conn.prepareStatement(sql);
			stmt1.setInt(1, codigo);
			stmt1.executeQuery();

			ResultSet filas = stmt1.executeQuery();
			while(filas.next()) {
				existe=true;
			}
			if(!existe) {
				System.out.println("No existe");
			}else {
				try {
					Connection conn1 = DriverManager.getConnection(url, usuario, password);
					String sql1 = "select * from clientes where codigo = ?";
					PreparedStatement stmt2 = conn.prepareStatement(sql1);
					stmt2.setInt(1, codigo);
					stmt2.executeQuery();

					ResultSet rs = stmt2.executeQuery();
					while (rs.next()) {
						System.out.println("codigo: " + rs.getInt("codigo"));
						System.out.println("nombre: " + rs.getString("nombre"));
						System.out.println("telefono: " + rs.getString("telefono"));
						System.out.println("saldo: " + rs.getFloat("saldo"));
						System.out.println(" ");
					}
					}catch (Exception x) {
						System.out.println(x.getMessage());
					}
			}
			
			stmt1.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("Error SQL: " + e.getMessage());
		}
		return existe;
	
	}
	public static void crearIncidencia (String codigoIncidencia, int codigoCliente, String asunto,String descripcion, double importe,String url, String usuario, String password) throws IOException {
		if (codigoIncidencia.length()>4) {
			throw new IOException("el código no puede tener mas de 4 carácteres");
		}
		LocalDate hoy = LocalDate.now();
		
		
		  try {
			  Connection conn = DriverManager.getConnection(url,usuario,password);
		  String sql = "INSERT INTO ordenes (codigo_incidencia, codigo_clientes, asunto, descripcion, fecha_apertura, importe) VALUES (?, ?, ?, ?,?,?)";
		  PreparedStatement stmt1 = conn.prepareStatement(sql);
		  stmt1.setString(1, codigoIncidencia);
		  stmt1.setInt(2, codigoCliente); 
		  stmt1.setDate(5, java.sql.Date.valueOf(hoy));
		  stmt1.setString(3, asunto);
		  stmt1.setString(4, descripcion);
		  stmt1.setDouble(6, importe);

		  
		  int filas = stmt1.executeUpdate();
		  if(filas == 1) {
			 System.out.println("Se ha añadadido la incidencia "+codigoIncidencia+ " Correctamente");
		  }else {
			  System.out.println("no se ha podido añadir");
		  }
		  stmt1.close();
		  conn.close();
		  }  catch (SQLException e) {
			  System.out.println("Error SQL: " + e.getMessage());
		  }
	  }
	public static boolean existeIncidencia(String codigoIncidencia,String url, String usuario, String password) {
		boolean existe = true;
		try {
			Connection conn = DriverManager.getConnection(url, usuario, password);
			String sql = "select * from ordenes where codigo_incidencia = ?";
			PreparedStatement stmt1 = conn.prepareStatement(sql);
			stmt1.setString(1, codigoIncidencia);
			stmt1.executeQuery();

			ResultSet filas = stmt1.executeQuery();
			while(filas.next()) {
				existe=true;
			}
			if(!existe) {
				System.out.println("No existe");
			}else {
				try {
					Connection conn1 = DriverManager.getConnection(url, usuario, password);
					String sql1 = "select * from ordenes where codigo_incidencia = ?";
					PreparedStatement stmt2 = conn.prepareStatement(sql1);
					stmt2.setString(1, codigoIncidencia);
					stmt2.executeQuery();

					ResultSet rs = stmt2.executeQuery();
					while (rs.next()) {
						System.out.println("codigo de incidencia: " + rs.getString("codigo_incidencia"));
						System.out.println("Asunto: " + rs.getString("asunto"));
						System.out.println("descripcion: " + rs.getString("descripcion"));
						System.out.println("fecha de apertura: " + rs.getDate("fecha_apertura"));
						System.out.println("fecha de cierre: " + rs.getDate("fecha_cierre"));
						System.out.println("importe: " + rs.getDouble("importe"));
						System.out.println(" ");
					}
					}catch (Exception x) {
						System.out.println(x.getMessage());
					}
			}
			
			stmt1.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("Error SQL: " + e.getMessage());
		}
		return existe;
	
	}
	public static void actualizarIncidencia(String codigoIncidencia,String campo, String nuevo , String url, String usuario, String password) {
 		 if (campo.equalsIgnoreCase("fecha de apertura")) {
 			campo="fecha_apertura";
 		}else if(campo.equalsIgnoreCase("fecha de cierre")) {
 			campo="fecha_cierre";
 		}
 		 
 		try {
 			  Connection conn = DriverManager.getConnection(url,usuario,password);
 		  String sql = String.format("UPDATE ordenes set %s = (?)  WHERE (codigo) = (?)",campo.toLowerCase());
 		  PreparedStatement stmt1 = conn.prepareStatement(sql);  		  
 		  stmt1.setString(1, nuevo);
 		  stmt1.setString(2, codigoIncidencia);

 		  
 		  
 		  int filas = stmt1.executeUpdate();
 		if(filas == 1) {
 			 System.out.println("Se ha actualizado la incidencia "+codigoIncidencia+ " Correctamente");
 		  }else {
 			  System.out.println("no se ha podido actualizar");
 		  }
 		  stmt1.close();
 		  conn.close();
 		  }  catch (SQLException e) {
 			  System.out.println("Error SQL: " + e.getMessage());
 		  }
 		
 	}
	public static void terminarIncidencia(String codigoIncidencia, String url, String usuario, String password) {
		LocalDate hoy = LocalDate.now();
		try {
			  Connection conn = DriverManager.getConnection(url,usuario,password);
		  String sql = String.format("UPDATE ordenes set fecha_cierre = (?), importe = 0  WHERE (codigo) = (?)");
		  PreparedStatement stmt1 = conn.prepareStatement(sql);  		  
		  stmt1.setDate(1, java.sql.Date.valueOf(hoy));
		  stmt1.setString(2, codigoIncidencia);

		  
		  
		  int filas = stmt1.executeUpdate();
		if(filas == 1) {
			 System.out.println("Se ha actualizado la incidencia "+codigoIncidencia+ " Correctamente");
		  }else {
			  System.out.println("no se ha podido actualizar");
		  }
		  stmt1.close();
		  conn.close();
		  }  catch (SQLException e) {
			  System.out.println("Error SQL: " + e.getMessage());
		  }
  	}
	public static void mostrarIncidencias(String url, String usuario, String password) {
		try {
			Connection conn = DriverManager.getConnection(url, usuario, password);
			String sql = "select * from ordenes";
			PreparedStatement stmt1 = conn.prepareStatement(sql);
			stmt1.executeQuery();

			

					ResultSet rs = stmt1.executeQuery();
					while (rs.next()) {
						System.out.println("codigo de incidencia: " + rs.getString("codigo_incidencia"));
						System.out.println("Asunto: " + rs.getString("asunto"));
						System.out.println("descripcion: " + rs.getString("descripcion"));
						System.out.println("fecha de apertura: " + rs.getDate("fecha_apertura"));
						System.out.println("fecha de cierre: " + rs.getDate("fecha_cierre"));
						System.out.println("importe: " + rs.getDouble("importe"));
						System.out.println(" ");
						
					}
					stmt1.close();
					conn.close();
					}catch (Exception x) {
						System.out.println(x.getMessage());
					}
			}
			
			
		
	
	}
	
	
	
		
	

