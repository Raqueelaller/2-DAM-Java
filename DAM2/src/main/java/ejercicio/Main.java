package ejercicio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
	public static void main(String[] args) {
		try (Connection con = DriverManager.getConnection("jdbc:sqlite:test.db");
				Statement stmt = con.createStatement()) {
			String sql = "SELECT * from usuario";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.printf("ID= %d, Nombre = %s \n", rs.getInt(1), rs.getString(2));
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}
}
