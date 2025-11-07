package examenRaquel1;

import java.util.Scanner;

public class Tests {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner teclado = new Scanner(System.in);
		
		
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
				System.out.println("hay "+ Viajes.plazasLibre(destino)+"plazas libres");
				break;
				
			case "b":
				System.out.println("dime el código del viaje");
				int codigo = teclado.nextInt();
				System.out.println("dime el número de plazas que desea reservar");
				int plazas = teclado.nextInt();
				teclado.nextLine();
				System.out.println("dime el código del cliente");
				int cliente=teclado.nextInt();
				
				Viajes.crearReserva(codigo, plazas, cliente);
				break;
				
			case "e":
				Viajes.mostrarReservas();
				break;
				
			case "d":
				Viajes.verViajes();
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
