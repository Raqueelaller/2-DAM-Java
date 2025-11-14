package productorConsumidor;

public class BufferLinea {
	  // Variable donde guardamos la línea pendiente de ser consumida.
    private String linea = null;

    // Indica si el buffer tiene una línea disponible.
    private boolean disponible = false;

    // Método sincronizado para que un productor introduzca una línea.
    public synchronized void ponerLinea(String nuevaLinea) throws InterruptedException {

        // Mientras haya una línea pendiente de consumir, el productor debe esperar.
        while (disponible) {
            wait(); // el hilo se bloquea y libera el monitor
        }

        // Guardamos la nueva línea en el buffer.
        linea = nuevaLinea;
        disponible = true;

        // Avisamos a los hilos consumidores de que ya hay algo.
        notifyAll();
    }

    // Método sincronizado para que un consumidor obtenga la línea.
    public synchronized String obtenerLinea() throws InterruptedException {

        // Si no hay línea, el consumidor debe esperar.
        while (!disponible) {
            wait();
        }

        // Guardamos la línea para devolverla.
        String resultado = linea;

        // Vaciamos el buffer.
        disponible = false;

        // Avisamos al productor de que ya puede poner otra línea.
        notifyAll();

        return resultado;
    }

}
