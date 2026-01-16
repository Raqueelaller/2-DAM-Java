package net.javaguides.hibernate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Pedidos")
public class Pedidos {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
	
	@Column(name = "cliente")
    private String cliente;
	
	
	 @ManyToOne()
	 @JoinColumn(name = "id_refresco")
	 private Refrescos refresco;


	 public Pedidos(int id, String cliente, Refrescos refresco) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.refresco = refresco;
	 }


	 public Pedidos() {
		super();
	 }


	 public String getCliente() {
		 return cliente;
	 }


	 public void setCliente(String cliente) {
		 this.cliente = cliente;
	 }


	 public Refrescos getRefresco() {
		 return refresco;
	 }


	 public void setRefresco(Refrescos refresco) {
		 this.refresco = refresco;
	 }
	 
	 
	 
	
}
