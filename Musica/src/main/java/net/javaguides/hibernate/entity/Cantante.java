package net.javaguides.hibernate.entity;






import java.util.ArrayList;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "Cantante")
public class Cantante{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
	
	@Column(name = "nombre")
    private String nombre;
	
	@Column(name = "pais")
	private String pais;	

	@OneToMany(mappedBy = "cantante", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Cancion> canciones = new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Cantante(int id, String nombre, String pais) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.pais = pais;
	}

	public String getPais() {
		return pais;
	}

	public void setCanciones(List<Cancion> canciones) {
		this.canciones = canciones;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public List<Cancion> getCanciones() {
		return canciones;
	}

	@Override
	public String toString() {
		return "Cantante id=" + id + ", nombre=" + nombre + ", pais=" + pais ;
	}

	public Cantante() {
		super();
	}

	

	
	


	
	
	
	
	
	
}