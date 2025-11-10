package ejercicioViajesBus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Viajes {
	
	int codigo;
	String destino;
	int plazasDisponibles;
	
	
	
	
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
	
	
		 
	 }
	
	
	
	

