package net.javaguides.hibernate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Refrescos")
public class Refrescos {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
	
	@Column(name = "nombre")
    private String nombre;
	
	public int getId() {
		return id;
	}

	@Column(name = "existencia")
	private int existencia;

	public Refrescos(int id, String nombre, int existencia) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.existencia = existencia;
	}

	public Refrescos() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getExistencia() {
		return existencia;
	}

	public void setExistencia(int existencia) {
		this.existencia = existencia;
	}
	
	public String toString() {
		return String.format("Refrescos: %s,existencias: %d",this.nombre,this.existencia);
	}
	

}
