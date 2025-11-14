package contadorConScynchronized;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		// Creamos un único objeto Contador compartido entre los dos hilos.
        Contador contador = new Contador();

        // Creamos dos hilos que incrementarán el contador 10 veces cada uno.
        Thread t1 = new Thread(new HiloIncrementador("Hilo 1", contador, 10));
        Thread t2 = new Thread(new HiloIncrementador("Hilo 2", contador, 10));

        // Arrancamos ambos hilos.
        t1.start();
        t2.start();

        // Esperamos a que ambos hilos terminen su ejecución.
        t1.join();
        t2.join();

        // Mostramos el resultado final del contador.
        // Debería ser 20 gracias a la sincronización.
        System.out.println("Valor final del contador = " + contador.getContador());

	}

}
