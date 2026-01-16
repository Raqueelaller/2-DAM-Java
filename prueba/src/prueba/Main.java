package prueba;


import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner teclado = new Scanner(System.in);
		String num1 = "Hola         y Adios";
		
		num1=num1.toLowerCase().trim().replaceAll("\\s+", "");
		String num2 = num1;
		
		System.out.println(num2);
		System.out.println(num1);
	}

}
