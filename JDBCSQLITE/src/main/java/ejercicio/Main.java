package ejercicio;

import java.sql.SQLException;

public class Main {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		
		Maquina cortadora = new Maquina("c-01","cortadora de pan",2);
		
		//Maquina.anyadirMaquina(cortadora.getCodigo_maquina(),cortadora.getDescripcion(),cortadora.getUnidades());
		
		Maquina.verMaquinas();
		
		//Maquina.eliminarMaquina("c-01");
		
		//Maquina.verMaquinas();
		
		Maquina.actualizarMaquina(cortadora.getCodigo_maquina(), "unidades", "3");
		Maquina.verMaquinas();

		
	}

}
