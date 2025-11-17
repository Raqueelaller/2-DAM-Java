package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViajesDao {
	
	public static void verViajes(String url, String usuario, String password) {
		String sql = "SELECT * from viajes";
		try (Connection conn = DriverManager.getConnection(url,usuario,password);
				Statement stmt = conn.createStatement())  {
			
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.printf("codigo del viaje= %d, destino = %s, plazas disponibles: %d \n", rs.getInt(1), rs.getString(2),rs.getInt(3));
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}
	
	public static int plazasLibre (String destino,String url, String usuario, String password) {
		int plazas = 0;
		
		
		String sql = String.format("SELECT plazas_disponibles from viajes where destino = '%s'",destino);
		try (Connection conn = DriverManager.getConnection(url,usuario,password);
	
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
}
