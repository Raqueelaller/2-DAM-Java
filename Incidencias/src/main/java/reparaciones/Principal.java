package reparaciones;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class Principal {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		
		  String usuario,password,servidor,puerto,basedatos;
		  Properties configuracion = new Properties();
		  Scanner teclado = new Scanner(System.in);
		  
		  	configuracion.load(new FileInputStream("mysqlReparaciones.txt"));

		    usuario = configuracion.getProperty("usuario");

		    password = configuracion.getProperty("password");

		    servidor = configuracion.getProperty("IP");

		    puerto = configuracion.getProperty("Port");
		    
		    basedatos= configuracion.getProperty("basedatos");
		    String url = String.format("jdbc:mysql://%s:%s/%s", servidor, puerto,basedatos ) ;
		    	
		   
		    String eleccion = null;
		
			  do {
				  System.out.println("Elige la opción del menú lo que quieras hacer");
				  System.out.println("a) consulta");
				  System.out.println("b) alta");
				  System.out.println("c) eliminar persona");
				  System.out.println("d) acutalizar");
				  System.out.println("e) salir");
				   eleccion = teclado.nextLine().toLowerCase();
			  switch (eleccion) {
			 
			  case "a":
				  System.out.println("----------------------- MOSTRANDO LISTADO -------------------------");
				 mostrarListado(url,usuario,password);
				 
				   
					  break;
			  case "b":
				  System.out.println("dime el nombre: ");
				  String nombre = teclado.nextLine();
				  System.out.println("dime el teléfono: ");
				  String telefono = teclado.nextLine();
				  System.out.println("dime el saldo: ");
				  double saldo = teclado.nextDouble();
				  teclado.nextLine();
				  
				  anyadirPersona(nombre, telefono, saldo, url, usuario, password);
				  
				  System.out.println("----------------------------- AÑADIENDO PERSONA -----------------------");
				  
				  
				  		break;
				
			  case "c":
				  System.out.println("dime el nombre de la persona que quieras eliminar: ");
				  String nombre1 = teclado.nextLine();
				 
				  eliminarPersona(nombre1, url, usuario, password);
				  System.out.println("----------------------------- ELIMINANDO PERSONA -----------------------");

				  
				  break;
			
			  case "d":
				  System.out.println("dime el código de la persona que quieres actualizar");
				  String codigo = teclado.nextLine();
				  System.out.println("dime el campo que desea actualizar");
				  String campo = teclado.nextLine();
				  System.out.println("dame la nueva información");
				  String nuevo = teclado.nextLine();
				  
				  actualizarUsuario(codigo,campo, nuevo, url, usuario, password);
				  
				  default :
					  System.out.println("elige una opción válida");
			 } 
		
			  } while (!eleccion.equalsIgnoreCase("e"));
			
	  
	  }public static void anyadirPersona (String nombre, String telefono, double saldo, String url, String usuario, String password) {   //Este método añade una persona.
		  
		  try {
			  Connection conn = DriverManager.getConnection(url,usuario,password);
		  String sql = "INSERT INTO clientes (nombre, telefono, saldo) VALUES (?, ?, ?)";
		  PreparedStatement stmt1 = conn.prepareStatement(sql);
		  stmt1.setString(1, nombre);
		  stmt1.setString(2, telefono);
		  stmt1.setDouble(3, saldo);
		  
		  int filas = stmt1.executeUpdate();
		  if(filas == 1) {
			 System.out.println("Se ha añadadido a "+nombre+ " Correctamente");
		  }else {
			  System.out.println("no se ha podido añadir");
		  }
		  stmt1.close();
		  conn.close();
		  }  catch (SQLException e) {
			  System.out.println("Error SQL: " + e.getMessage());
		  }
	  }
	  
	  public static void eliminarPersona (String persona, String url, String usuario, String password) { 		// este método elimina un usuario
		  try {
			  Connection conn = DriverManager.getConnection(url,usuario,password);
		  String sql = "DELETE FROM clientes WHERE (nombre) = (?)";
		  PreparedStatement stmt1 = conn.prepareStatement(sql);
		  stmt1.setString(1, persona);
		  
		  
		  int filas = stmt1.executeUpdate();
		  if(filas == 1) {
				 System.out.println("Se ha eliminado a "+persona+ " Correctamente");
			  }else {
				  System.out.println("no se ha podido eliminar");
			  }
		  stmt1.close();
		  conn.close();
		  }  catch (SQLException e) {
			  System.out.println("Error SQL: " + e.getMessage());
		  }
	  }
	  	
	  	public static void 	mostrarListado (String url, String usuario, String password) {  // este método muestra el listado completo
	  		 try(Connection conn = DriverManager.getConnection(url,usuario,password);
					  
						Statement stmt = conn.createStatement() ;
						ResultSet rs = stmt.executeQuery("SELECT * FROM clientes")){
							
				  while (rs.next()) {
					  System.out.println("codigo: "+ rs.getString("codigo"));
					  System.out.println("nombre: "+ rs.getString("nombre"));
					  System.out.println("telefono: "+ rs.getString("telefono"));
					  System.out.println("saldo: "+ rs.getFloat("saldo"));
					  System.out.println(" ");
				  						}
				  
				  System.out.println("-----------------------------------------------------------------------------");
				  stmt.close();
				  conn.close();
			  } catch (Exception x ) { 
				    System.out.println(x.getMessage() );

			  } 
	  	}
	  	
	  	public static void actualizarUsuario(String codigo,String campo, String nuevo , String url, String usuario, String password) {
	  		 if (campo.equalsIgnoreCase("fecha de alta")) {
	  			campo="fecha_alta";
	  		}
	  		try {
	  			  Connection conn = DriverManager.getConnection(url,usuario,password);
	  		  String sql = String.format("UPDATE clientes set %s = (?)  WHERE (codigo) = (?)",campo.toLowerCase());
	  		  PreparedStatement stmt1 = conn.prepareStatement(sql);  		  
	  		  stmt1.setString(1, nuevo);
	  		  stmt1.setString(2, codigo);

	  		  
	  		  
	  		  int filas = stmt1.executeUpdate();
	  		if(filas == 1) {
	  			 System.out.println("Se ha actualizado a "+codigo+ " Correctamente");
	  		  }else {
	  			  System.out.println("no se ha podido actualizar");
	  		  }
	  		  stmt1.close();
	  		  conn.close();
	  		  }  catch (SQLException e) {
	  			  System.out.println("Error SQL: " + e.getMessage());
	  		  }
	  		
	  	
	}

}
