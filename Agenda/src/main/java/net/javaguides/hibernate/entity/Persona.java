package net.javaguides.hibernate.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "persona")
public class Persona{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private int codigo;
	
	@Column(name = "first_name")
    private String name;
	
	@Column(name = "direccion")
	private String direccion;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "telefono")
	private int telefono;
	
	@Column(name = "alutra")
	private int altura;
	
	
	

	public Persona() {
		
	}
	
	
	
	public Persona(int codigo, String name, String direccion, String email, int telefono, int altura) {
		super();
		this.codigo = codigo;
		this.name = name;
		this.direccion = direccion;
		this.email = email;
		this.telefono = telefono;
		this.altura = altura;
	}




	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}



	@Override
	public String toString() {
		return "Persona [codigo=" + codigo + ", nombre=" + name + ", direccion=" + direccion + ", email=" + email
				+ ", telefono=" + telefono + ", altura=" + altura + "]";
	}
	
	
	
	
	
	
}