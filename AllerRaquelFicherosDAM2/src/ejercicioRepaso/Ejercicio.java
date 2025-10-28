package ejercicioRepaso;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Ejercicio {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner teclado = new Scanner(System.in);
		File f = new File ("nombres.txt");
		if(f.exists()) {
			f.delete();
		}
		f.createNewFile();
		FileWriter p = new FileWriter(f);
		String nombre;
		
		do {
			System.out.println("escribe un nombre");
			nombre = teclado.nextLine();
			if (!nombre.equalsIgnoreCase("fin")) {
			p.write(String.format("%s%n", nombre));
			}
		}while(!nombre.equalsIgnoreCase("fin"));
		teclado.close();
		p.close();
	}

}
