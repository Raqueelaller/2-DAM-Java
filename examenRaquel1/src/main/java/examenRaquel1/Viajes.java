package examenRaquel1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Viajes {
	
	int codigo;
	String destino;
	int plazasDisponibles;
	
	int numero_reserva;
	int plazas_reservadas;
	int codigo_cliente;
	String estado ;
	
	public Viajes(int codigo, String destino, int plazasDisponibles) {
		super();
		this.codigo = codigo;
		this.destino = destino;
		this.plazasDisponibles = plazasDisponibles;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public int getPlazasDisponibles() {
		return plazasDisponibles;
	}
	public void setPlazasDisponibles(int plazasDisponibles) {
		this.plazasDisponibles = plazasDisponibles;
	}
	
	public static void verViajes() {
		String sql = "SELECT * from viajes";
		try (Connection con = DriverManager.getConnection("jdbc:sqlite:viajesbus.db");
				Statement stmt = con.createStatement())  {
			
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.printf("codigo del viaje= %d, destino = %s, plazas disponibles: %d \n", rs.getInt(1), rs.getString(2),rs.getInt(3));
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}
	
	public static int plazasLibre (String destino) {
		int codigo=0;
		int plazas = 0;
		if(destino.equals("Sevilla")) {
			codigo=2;
		} if(destino.equals("Cuenca")) {
			codigo=1;
		}
		if (destino.equals("Madrid")) {
			codigo=3;
		}
			
		
		String sql = String.format("SELECT plazas_disponibles from viajes where codigo = %d",codigo);
		try (Connection conn = DriverManager.getConnection("jdbc:sqlite:viajesbus.db");
	
						Statement stmt = conn.createStatement()	) {
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				plazas=rs.getInt(1);
				
			}
			else {
				plazas=-1;
				
			}
			return plazas;
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return -1;
		}
	}
	 public static void mostrarReservas() {
		 String sql = "SELECT * from reservas";
			try (Connection con = DriverManager.getConnection("jdbc:sqlite:viajesbus.db");
					Statement stmt = con.createStatement())  {
				
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					System.out.printf("numero de la reserva= %d, codigo del viaje = %d, codigo del cliente: %d, plazas reservadas: %d, estado:%s \n", rs.getInt(1), rs.getInt(2),rs.getInt(3), rs.getInt(4),rs.getString(5));
				}
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
	 }
	
	 public static void crearReserva(int codigoViaje, int numero_plazas, int codigo_cliente) {
		 int plazas_libres=0;
		 if(codigoViaje==1) {
			 plazas_libres= plazasLibre("Cuenca");
		 }if(codigoViaje==2) {
			 plazas_libres=plazasLibre("Sevilla");
		 }if(codigoViaje==3) {
			 plazas_libres=plazasLibre("Madrid");
		 }
		 
		 if((plazas_libres-numero_plazas)>=0) {
			String sql = "UPDATE reservas SET estado = 'A' WHERE codigo_cliente = ?";
			String sql2= "UPDATE reservas SET plazas_reservadas = ? WHERE codigo_cliente = ?";

			    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:viajesbus.db");
			         PreparedStatement stmt = conn.prepareStatement(sql);
			        		 PreparedStatement stmt1 = conn.prepareStatement(sql2)) {

			        stmt.setInt(1, codigo_cliente);
			        stmt1.setInt(1,numero_plazas);
			        stmt1.setInt(2, codigo_cliente);
			        // Ejecutamos el UPDATE
			        int filas = stmt.executeUpdate();
			        int filas2=stmt1.executeUpdate();
			        if (filas > 0 && filas2 >0) {
			            System.out.println("reserva realizada correctamente (" + filas + " fila/s).");
			        } else {
			            System.out.println("No se encontró ninguna reserva con ese código.");
			        }

			    } catch (Exception e) {
			        System.out.println("Error al actualizar: " + e.getMessage());
			    }
			
			}else {
				String sql = "UPDATE reservas SET estado = 'E' WHERE codigo_cliente = ?";
				
			    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:viajesbus.db");
			         PreparedStatement stmt = conn.prepareStatement(sql);
			    		PreparedStatement stmt1 = conn.prepareStatement(sql)) {
			    	
			        stmt.setInt(1, codigo_cliente);
			        stmt1.setInt(1,codigo_cliente);
			        // Ejecutamos el UPDATE
			        int filas = stmt.executeUpdate();
			        int filas2=stmt1.executeUpdate();

			        if (filas > 0 && filas2 >0) {
			            System.out.println("reserva realizada correctamente (" + filas + " fila/s).");
			        } else {
			            System.out.println("No se encontró ninguna reserva con ese código.");
			        }

			    } catch (Exception e) {
			        System.out.println("Error al actualizar: " + e.getMessage());
			    }
			}
			 
		 }
	/* 
	 public static void cancelarReserva(int codigo_cliente) {
		 int plazas=0;
		 String sql = "UPDATE reservas SET estado = 'C' WHERE codigo_cliente = ?";
		 String sql3 = String.format("SELECT plazas_reservadas from reservas where codigo = %d",codigo_cliente);
			String sql2= "UPDATE reservas SET plazas_reservadas = 0 WHERE codigo_cliente = ?";
			String sql4 = String.format("UPDATE viajes SET plazas_disponibles = plazas_disponibles + %d WHERE codigo_cliente = ?",plazas);
			    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:viajesbus.db");
			         PreparedStatement stmt = conn.prepareStatement(sql);
			        		 PreparedStatement stmt1 = conn.prepareStatement(sql2);
			    		Statement stmt2 = conn.createStatement();
			    				PreparedStatement stmt3 = conn.prepareStatement(sql4)) {

			        stmt.setInt(1, codigo_cliente);
			        stmt1.setInt(1, codigo_cliente);
			        
			        // Ejecutamos el UPDATE
			        int filas = stmt.executeUpdate();
			        int filas2=stmt1.executeUpdate();
			        ResultSet rs = stmt2.executeQuery(sql3);
			        int flias
					
					if(rs.next()) {
						plazas=rs.getInt(1);
						
					}
			        if (filas > 0 && filas2 >0) {
			            System.out.println("reserva realizada correctamente (" + filas + " fila/s).");
			        } else {
			            System.out.println("No se encontró ninguna reserva con ese código.");
			        }

			    } catch (Exception e) {
			        System.out.println("Error al actualizar: " + e.getMessage());
			    }
	 }*/ // Me ha faltado en este, actualizar las plazas disponibles de la tabla viajes.
		 
	 }
	
	
	
	

