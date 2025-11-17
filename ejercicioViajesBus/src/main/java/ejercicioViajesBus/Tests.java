package ejercicioViajesBus;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import dao.ReservasDao;
import dao.ViajesDao;



public class Tests {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		Scanner teclado = new Scanner(System.in);
		
		  String usuario,password,servidor,puerto,basedatos;
		  Properties configuracion = new Properties();
		  
		  	configuracion.load(new FileInputStream("viajesBus.cnf"));

		    usuario = configuracion.getProperty("usuario");

		    password = configuracion.getProperty("password");

		    servidor = configuracion.getProperty("IP");

		    puerto = configuracion.getProperty("Port");
		    
		    basedatos= configuracion.getProperty("basedatos");
		    String url = String.format("jdbc:mysql://%s:%s/%s", servidor, puerto,basedatos ) ;
		
		
		String eleccion;
		do {
			System.out.println("Bienvenido al menú");
			System.out.println("opción a) mostrar plazas libres");
			System.out.println("opción b) crear reserva");
			System.out.println("opción c) cancelar reserva");
			System.out.println("opción d) mostrar todos los viajes");
			System.out.println("opción e) mostrar todas las reservas");
			System.out.println("opción f) salir");
			System.out.println("dime qué opción quieres elegir: ");
			eleccion=teclado.nextLine();
			eleccion=eleccion.toLowerCase();
		
			switch (eleccion) {
			
			case "a": 
				System.out.println("dime el destino");
				String destino = teclado.nextLine();
				System.out.println("hay "+ ViajesDao.plazasLibre(destino,url,usuario,password)+"plazas libres");
				break;
				
			case "b":
				System.out.println("dime el nombre del destino"); //Malaga
				String destino1 = teclado.nextLine();
				System.out.println("dime el código del viaje");
				int codigo = teclado.nextInt();
				System.out.println("dime el número de plazas que desea reservar");
				int plazas = teclado.nextInt();
				teclado.nextLine();
				System.out.println("dime el código del cliente");
				int cliente=teclado.nextInt();
				
				ReservasDao.crearReserva(codigo,destino1, plazas, cliente,url,usuario,password);
				break;
				
			case "c":
				
				System.out.println("dime el código del cliente");
				int cliente1=teclado.nextInt();
				System.out.println("dime el código del viaje");
				int codigo1 = teclado.nextInt();
				
				
				ReservasDao.cancelarReserva(cliente1,codigo1, url, usuario, password);
				
			case "e":
				ReservasDao.mostrarReservas(url,usuario,password);
				break;
				
			case "d":
				ViajesDao.verViajes(url,usuario,password);
				break;
			case "f":
				System.out.println("hasta pronto!!");
				
				break;
			default:
				System.out.println("mete otra aopción");
				
			}
			
		}while(!eleccion.equalsIgnoreCase("f"));
		
		
	}

}
