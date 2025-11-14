package productorConsumidor;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		 // Creamos buffer compartido
        BufferLinea buffer = new BufferLinea();

        // Creamos los consumidores
        Thread v = new Thread(new ContadorVocales(buffer));
        Thread c = new Thread(new ContadorConsonantes(buffer));

        // Arrancamos los hilos consumidores
        v.start();
        c.start();

        // Simulamos un productor que genera líneas (podría ser un archivo)
        String[] lineas = {
            "Hola mundo",
            "Java es divertido",
            "Examen de hilos",
            "__FIN__"     // marca el final
        };

        // El productor introduce las líneas en el buffer
        for (String linea : lineas) {
            buffer.ponerLinea(linea);
        }

        // Esperamos a que los consumidores terminen
        v.join();
        c.join();

        System.out.println(" todos los consumidores han terminado.");
	}

}
