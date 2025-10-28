package hilos1;

import java.util.Scanner;

public class TestHilos {

	public static void main(String[] args) {
		// TODO Auto-generated method stub7
		Scanner teclado = new Scanner(System.in);
		//1 crear instancias de la tarea
		int numero= numAleatorio(6000,2500);
		System.out.println("dime el nombre del primer hilo");
		String nombre1 = teclado.nextLine();
		System.out.println("dime el nombre del segundo hilo");
		String nombre2 = teclado.nextLine();
		System.out.println("dime el nombre del tercer hilo");
		String nombre3 = teclado.nextLine();
		TareaHilo tarea1 = new TareaHilo(nombre1,numero);
		TareaHilo tarea2 = new TareaHilo(nombre2,numero);
		TareaHilo tarea3 = new TareaHilo(nombre3,numero);
		
		//2. crear los objetos Thread, pasando la tarea (Runnable) y el nombre
		Thread t1 = new Thread(tarea1);
		Thread t2 = new Thread(tarea2);
		Thread t3 = new Thread(tarea3);
		
		// 3. Capturar el tiempo de inicio
        long tiempoInicio = System.currentTimeMillis();
		
		//4.iniciar la ejecución de los hilos
		//LLAMAR A start() ES FUNDAMENTAL, NO a run()
		t1.start();
		t2.start();
		t3.start();
		
		
		System.out.println("Hilos iniciados, el hilo principal (main) continúa su ejecución");
		
		  // 5. Esperar a que terminen todos los hilos
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.out.println("El hilo principal fue interrumpido.");
        }
        
     // 6. Capturar el tiempo de finalización
        long tiempoFin = System.currentTimeMillis();

        // 7. Calcular duración total
        long duracion = tiempoFin - tiempoInicio;
        double durar = ((double) duracion)/1000;
        System.out.println("Todos los hilos han terminado.");
        System.out.println("Tiempo total de ejecución: " + durar + " segundos");
    }
	public static int numAleatorio(int maximo, int minimo) { //creamos un método para generar números aleatorios
		int numAleatorio=(int) ((Math.random()*(((maximo+minimo)/2)+1))+minimo);
		return numAleatorio;
	}		

	}

