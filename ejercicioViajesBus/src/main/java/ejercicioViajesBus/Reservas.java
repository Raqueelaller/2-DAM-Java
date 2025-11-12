package ejercicioViajesBus;

public class Reservas {
	
	int codigo_cliente;
	int codigo_viaje;
	int numero_reserva;
	int plazas_reservadas;
	String estado;
	
	public Reservas(int codigo_cliente, int codigo_viaje, int numero_reserva, int plazas_reservadas, String estado) {
		super();
		this.codigo_cliente = codigo_cliente;
		this.codigo_viaje = codigo_viaje;
		this.numero_reserva = numero_reserva;
		this.plazas_reservadas = plazas_reservadas;
		this.estado = estado;
	}

	public int getCodigo_cliente() {
		return codigo_cliente;
	}

	public void setCodigo_cliente(int codigo_cliente) {
		this.codigo_cliente = codigo_cliente;
	}

	public int getCodigo_viaje() {
		return codigo_viaje;
	}

	public void setCodigo_viaje(int codigo_viaje) {
		this.codigo_viaje = codigo_viaje;
	}

	public int getNumero_reserva() {
		return numero_reserva;
	}

	public void setNumero_reserva(int numero_reserva) {
		this.numero_reserva = numero_reserva;
	}

	public int getPlazas_reservadas() {
		return plazas_reservadas;
	}

	public void setPlazas_reservadas(int plazas_reservadas) {
		this.plazas_reservadas = plazas_reservadas;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	
}
