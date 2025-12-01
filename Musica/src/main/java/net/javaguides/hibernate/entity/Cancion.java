package net.javaguides.hibernate.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Cancion")
public class Cancion {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
	
	@Column(name = "titulo")
    private String titulo;
	
	@Column(name = "anyo")
	private int anyo;
	
	 @ManyToOne()
	 @JoinColumn(name = "id_cantante")
	 private Cantante cantante;

	 public int getId() {
		 return id;
	 }

	 public void setId(int id) {
		 this.id = id;
	 }

	 public String getTitulo() {
		 return titulo;
	 }

	 public void setTitulo(String titulo) {
		 this.titulo = titulo;
	 }

	 public int getAnyo() {
		 return anyo;
	 }

	 public void setAnyo(int anyo) {
		 this.anyo = anyo;
	 }

	 public Cantante getCantante() {
		 return cantante;
	 }

	 public void setCantante(Cantante cantante) {
		 this.cantante = cantante;
	 }

	 public Cancion(int id, String titulo, int anyo, Cantante cantante)throws IllegalArgumentException {
		 if(cantante == null) {
			 throw new IllegalArgumentException("No existe el cantante insertado");
		 }
		this.id = id;
		this.titulo = titulo;
		this.anyo = anyo;
		this.cantante = cantante;
	 }

	 public Cancion() {
		super();
	 }
	 
	 
	
}
