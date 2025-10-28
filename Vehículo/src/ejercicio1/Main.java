/*
 * Fecha 21/09/2025
 * Autora: Raquel Aller Cerón
 * Objetivo: Crear un cuestionario para añadir vehículos, buscarlos por matrícula y listarlos.
 * 
 */
package ejercicio1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner teclado = new Scanner(System.in);
		
		Vehiculo v ;
		String fin,buscar;
		File f = new File("vehiculos.txt"); // creamos el fichero
		f.delete(); //lo eliminamos por si hubiese alguno creado de anterioridad
		f= new File ("vehiculos.txt"); // lo volvemos a crear pero limpio
		System.out.println("Cuestionario para añadir tu vehículo, cuando termines, di `salir'");
		System.out.println("-------------------------------------");
		do{ 												// realizamos un do while para que se siga haciendo hasta que digas "salir"
			System.out.println("di que quieres hacer"); //desplegamos en la consola las distintas opciones
			System.out.println("Grabar");
			System.out.println("Buscar");
			System.out.println("Listar");
			System.out.println("salir");
			fin=teclado.nextLine();
			if (!fin.equalsIgnoreCase("salir")) { 
				switch (fin) { // he optado por usar un switch case porque solo hay 3 opciones que puede hacer el programa(Aparte de salir que si se escribe que para el programa)
				case "Grabar":
					grabar(f); //se usa el método grabar
						break;
				case "Buscar":
					System.out.println("dime la matrícula (debe ser [4 números][3 letras Mayúsculas]");
					buscar=teclado.nextLine(); // se guarda la matrícula
					System.out.println(buscar(buscar, f));
					break;
				case "Listar":
					ArrayList<Vehiculo> informe = new ArrayList<>();
					informe.addAll(listar(f)); // copiamos el archivo en una lista para posteriormente ver la lista con un for-each
					for (Vehiculo vehiculo : informe) {
						System.out.println(vehiculo.toString());
					}
					break;
				
				 }
					
			
			}
			
		} while(!fin.equalsIgnoreCase("salir"));
		
		
		
		
	} public static void grabar(File f) { //con este método grabamos los vehículos en el fichero que le pasemos por parámetro
		Scanner teclado = new Scanner(System.in);

		String tipo, marca, modelo, matricula;
		Vehiculo v = null;
		System.out.println("Qué tipo de vehículo es? ");
		tipo=teclado.nextLine();
		System.out.println("Qué marca es? ");
		marca=teclado.nextLine();
		System.out.println("Dime su modelo");
		modelo=teclado.nextLine();
		System.out.println("Cual es su matrícula?(debe ser [4 números][3 letras en mayúscula])");
		matricula=teclado.nextLine();
		try{
			v= new Vehiculo(tipo,marca,modelo,matricula);
		} catch (IllegalArgumentException x) {
			System.out.println("su vehículo no se ha podido añadir");
			System.out.println(x.getMessage());
		}
		if (v != null) {
		try(FileWriter p = new FileWriter(f,true)){
			p.write(v.toString()+"\n");
			System.out.println("Se ha guardado su vehículo correctamente");
			}catch(IOException x) {
				System.out.println(x.getMessage());
			}
		
		}
	}
	public static String buscar(String matricula, File f) { // con este método se busca la matrícula que le pasamos por parámetro recorriendo el fichero que le pasamos por parámetro también
		String mensaje = String.format("no se ha encontrado");
		try (Scanner sc = new Scanner(f)) {
			while (sc.hasNext()) {
				String linea = sc.nextLine();
				String[] parametros = linea.split(",");
				if (parametros[3].equalsIgnoreCase(matricula)) {
					mensaje= String.format("Tipo de vehículo: %s, Marca: %s, Modelo: %s",parametros[0].toString(),
							parametros[1].toString(), parametros[2].toString());
				}
			}
		
	}catch (FileNotFoundException e) {
		mensaje=String.format("Error. No se ha encontrado el archivo vehiculos.txt");
		System.out.println("Error. No se ha encontrado el archivo vehiculos.txt");
		
	}
		return mensaje;

} public static ArrayList<Vehiculo> listar(File f) { // con este método creamos una lista con el fichero que le pasamos por parámetro
	ArrayList<Vehiculo> cuestionario = new ArrayList <>();
	
	try (Scanner sc = new Scanner(f)) {
		while (sc.hasNext()) {
			String linea = sc.nextLine();
			String[] parametros = linea.split(",");
			cuestionario.add(new Vehiculo(parametros[0],parametros[1],parametros[2],parametros[3]));
		
		}} catch (FileNotFoundException e) {
			System.out.println("Error. No se ha encontrado el archivo vehiculos.txt");
		}
	
	return cuestionario;
}
	

}
