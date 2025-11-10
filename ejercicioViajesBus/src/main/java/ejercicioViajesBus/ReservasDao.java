package ejercicioViajesBus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ReservasDao {
	

	 public static void mostrarReservas(String url, String usuario, String password) {
		 String sql = "SELECT * from reservas";
			try (Connection con = DriverManager.getConnection(url,usuario,password);
					Statement stmt = con.createStatement())  {
				
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					System.out.printf("numero de la reserva= %d, codigo del viaje = %d, codigo del cliente: %d, plazas reservadas: %d, estado:%s \n", rs.getInt(1), rs.getInt(2),rs.getInt(3), rs.getInt(4),rs.getString(5));
				}
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
	 }
	
	 public static void crearReserva(int codigoViaje, int numero_plazas, int codigo_cliente,String url, String usuario, String password) {
		 int plazas_libres=0;
		 int plazas_disponibles=0;
		int plazas_reservadas=0;
		 
		 if((plazas_libres-numero_plazas)>=0) {
			String sql = "UPDATE reservas SET estado = 'A' WHERE codigo_cliente = ?";
			String sql2= "UPDATE reservas SET plazas_reservadas = ? WHERE codigo_cliente = ?";
			String sql4 = "UPDATE viajes set plazas_disponibles = plazas_disponibles - ? where codigo = ?";
			    try (Connection conn = DriverManager.getConnection(url,usuario,password);
			         PreparedStatement stmt = conn.prepareStatement(sql);
			        		 PreparedStatement stmt1 = conn.prepareStatement(sql2);
			    					 PreparedStatement stmt4 = conn.prepareStatement(sql4)) {
			    	
			    	
			        stmt.setInt(1, codigo_cliente);
			        stmt1.setInt(1,numero_plazas);
			        stmt1.setInt(2, codigo_cliente);
			        stmt4.setInt(1, numero_plazas);
			        stmt4.setInt(2, codigoViaje);
			        // Ejecutamos el UPDATE
			        int filas = stmt.executeUpdate();
			        int filas2=stmt1.executeUpdate();
			        int filas3 = stmt4.executeUpdate();
			        if (filas > 0 && filas2 >0 && filas3>0) {
			            System.out.println("reserva realizada correctamente (" + filas + " fila/s).");
			        } else {
			            System.out.println("No se encontró ninguna reserva con ese código.");
			        }

			    } catch (Exception e) {
			        System.out.println("Error al actualizar: " + e.getMessage());
			    }
			
			}else {
				String sql = "UPDATE reservas SET estado = 'E' WHERE codigo_cliente = ?";
				String sql1 = "SELECT plazas_reservadas from reservas where codigo_ciente = ?";
				String sql2 =  "UPDATE viajes set plazas_disponibles = plazas_disponibles + ? where codigo = ?";
				String sql3= "UPDATE reservas SET plazas_reservadas = ? where codigo_cliente= ?";
			    try (Connection conn = DriverManager.getConnection(url,usuario,password);
			         
			    		PreparedStatement stmt1 = conn.prepareStatement(sql);
			    		Statement stmt2 = conn.createStatement();
			    		PreparedStatement stmt3 = conn.prepareStatement(sql2);
			    		PreparedStatement stmt4 = conn.prepareStatement(sql3)) {
			    	
			        
			        stmt1.setInt(1,codigo_cliente);
			        ResultSet rs = stmt2.executeQuery(sql1);
			        while (rs.next()) {
						plazas_reservadas=rs.getInt(1);
					}
			        
			        stmt3.setInt(1, plazas_reservadas);
			        stmt3.setInt(2, codigoViaje);
			        stmt4.setInt(1, numero_plazas);
			        stmt4.setInt(2, codigo_cliente);
			        // Ejecutamos el UPDATE
			        int filas = stmt1.executeUpdate();
			        int filas2=stmt3.executeUpdate();
			        int filas3=stmt4.executeUpdate();
			        
			        if (filas > 0 && filas2 >0 && filas3>0) {
			            System.out.println("reserva realizada correctamente (" + filas + " fila/s).");
			        } else {
			            System.out.println("No se encontró ninguna reserva con ese código.");
			        }

			    } catch (Exception e) {
			        System.out.println("Error al actualizar: " + e.getMessage());
			    }
			}
			 
		 }
	
	 public static void cancelarReserva(int codigo_cliente, int codigo_viaje,String url, String usuario, String password) {
		 int plazas=0;
		 String estado="";
		 String sql6 = String.format("SELECT estado FOM reservas where codigo_cliente = %d",codigo_cliente); 
		 try (Connection conn = DriverManager.getConnection(url,usuario,password);
				 Statement stmt6 = conn.createStatement(); ){
			 ResultSet rs = stmt6.executeQuery(sql6);
		        while (rs.next()) {
					estado=rs.getString(1);
				}
			 if(estado.equalsIgnoreCase("A")){
				 String sql = "UPDATE reservas SET estado = 'C' WHERE codigo_cliente = ?";
				 String sql3 = String.format("SELECT plazas_reservadas from reservas where codigo = %d",codigo_cliente);
				 String sql7= "UPDATE viajes SET plazas_disponibles= plazas_disponibles + ? where codigo = ?";
					String sql2= "UPDATE reservas SET plazas_reservadas = 0 WHERE codigo_cliente = ?";
					    try (Connection con = DriverManager.getConnection(url,usuario,password);
					         PreparedStatement stmt = con.prepareStatement(sql);
					        		 PreparedStatement stmt1 = con.prepareStatement(sql2);
					    		Statement stmt2 = con.createStatement();
					    				PreparedStatement stmt3 = con.prepareStatement(sql7)) {

					        stmt.setInt(1, codigo_cliente);
					        stmt1.setInt(1, codigo_cliente);
					        
					        ResultSet rs1 = stmt3.executeQuery(sql7);
					     		if(rs1.next()) {
								plazas=rs.getInt(1);
							}
					     	
					     	stmt3.setInt(1, plazas);
					     	stmt.setInt(2, codigo_viaje);
					     	
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
				 String sql = "UPDATE reservas SET estado = 'C' WHERE codigo_cliente = ?";
					String sql2= "UPDATE reservas SET plazas_reservadas = 0 WHERE codigo_cliente = ?";
					    try (Connection con = DriverManager.getConnection(url,usuario,password);
					         PreparedStatement stmt = con.prepareStatement(sql);
					        		 PreparedStatement stmt1 = con.prepareStatement(sql2)) {

					        stmt.setInt(1, codigo_cliente);
					        stmt1.setInt(1, codigo_cliente);
					        
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
		 } catch (Exception e) {
			 
		 }
		 
		
	 }
}
