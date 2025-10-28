package ejercicio1;

import java.util.regex.PatternSyntaxException;

public class Vehiculo {
	public static final String MATRICULA_DEFFAULT="0028LYB";
	public static final String MARCA_DEFFAULT= "seat";
	public static final String MODELO_DEFFAULT= "ibiza";
	public static final String TIPO_DEFFAULT= "coche";
	
	private static final String patron="[0-9]{4}[A-Z]{3}";
	
	private final String matricula; //son todos los atributos final por que una vez que se le da un valor, no puede cambiar.
	private final String marca;
	private final String modelo;
	private final String tipo;

	public Vehiculo (String tipo, String modelo, String marca, String matricula)throws IllegalArgumentException {
		try{ if (!matricula.matches(patron)) {
			throw new IllegalArgumentException("La matrícula deben ser 4 números y tres letras '0000XXX'");
		}
		}catch (PatternSyntaxException x) {
			System.out.println(x.getMessage());
		}
			
		this.matricula=matricula;
		this.marca=marca;
		this.modelo=modelo;
		this.tipo=tipo;
	}
	
	public Vehiculo() {
		this(TIPO_DEFFAULT,MODELO_DEFFAULT,MARCA_DEFFAULT,MATRICULA_DEFFAULT);
	}
	
	public String toString() {
		return String.format("%s,%s,%s,%s",tipo,modelo,marca,matricula);
	}
	
	public String getMatricula() {
		return matricula;
	}
	
	public String getMarca() {
		return marca;
	}
	
	public String getModelo() {
		return modelo;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	
	
	
	


}
