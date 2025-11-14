package contadorConScynchronized;

public class Contador {
	
	 // Variable que guarda el valor del contador.
    // Es privada para obligar a usar los métodos controlados.
    private int contador = 0;

    // Método que devuelve el valor del contador.
    public int getContador() {
        return contador;
    }

    // Método que modifica el valor del contador.
    // Usamos THIS para referirnos al atributo de la clase.
    public void setContador(int contador) {
        this.contador = contador;
    }

    // Método sincronizado: solo un hilo puede ejecutarlo a la vez.
    // Esto evita que dos hilos incrementen al mismo tiempo y se pierda un incremento.
    public synchronized void incrementarContador() {
        contador = contador + 1;  // operación atómica gracias a synchronized
    }


}
